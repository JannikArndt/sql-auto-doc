package de.jannikarndt.sqlautodoc.MSSQL

import com.typesafe.scalalogging.Logger
import de.jannikarndt.sqlautodoc.configuration.Options
import de.jannikarndt.sqlautodoc.model._
import slick.jdbc
import slick.jdbc.SQLServerProfile
import slick.jdbc.SQLServerProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.language.postfixOps

object SqlServerDoc {

    val logger = Logger(this.getClass)

    def QuerySystemTables(options: Options): Seq[TableInfo] = {

        logger.debug(s"Connecting to SQL Server ${options.connection.url} with user ${options.connection.user}")

        var db: jdbc.SQLServerProfile.backend.DatabaseDef = null

        try {
            db = Database.forURL(
                options.connection.url,
                driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                user = options.connection.user,
                password = options.connection.password,
                executor = AsyncExecutor("test1", numThreads = 10, queueSize = 1000))

            val doc = new SqlServerDoc(db)

            Await.result(doc.getTableInfo, options.timeout seconds)
        } finally {
            db.close
        }
    }
}

class SqlServerDoc(val db: SQLServerProfile.backend.DatabaseDef) {
    private lazy val schemas = TableQuery[Schemas]
    private lazy val sysobjects = TableQuery[SysObjects]
    private lazy val properties = TableQuery[ExtendedProperties]
    private lazy val sysColumns = TableQuery[SysColumns]
    private lazy val sysTypes = TableQuery[SysTypes]

    val logger = Logger(this.getClass)

    def getTableInfo: Future[Seq[TableInfo]] = {
        db.run(tablesQuery.result).map(x => x.map(TabInfo.tupled)).flatMap(getTablesInfo)
    }

    private def getTablesInfo(tables: Seq[TabInfo]): Future[Seq[TableInfo]] = {
        Future.sequence(tables.map(getTableInfo))
    }

    private def getTableInfo(table: TabInfo): Future[TableInfo] = {
        queryColumns(table.id)
            .map(getColumnsInfo)
            .map(col => TableInfo(table.schema, table.name, table.id, table.propVal, col))
    }

    private def getColumnsInfo(cols: Seq[ColInfo]): Seq[ColumnInfo] = {
        cols.groupBy(_.name).map((colTuple: (String, Seq[ColInfo])) => {
            println(colTuple)
            val propsForThisCol = colTuple._2.map(c => (c.propName, c.propVal))
            println(propsForThisCol)
            println("-")
            val col = colTuple._2.head
            MssqlColumnInfo(col.name, col.colid, col.typeName, col.length, col.nullable, col.default, propsForThisCol)
        }).toSeq.sortBy(_.id)
    }

    private def queryTables() = for {
        schema <- schemas.filterNot(_.name.inSet(Seq("dbo", "guest", "INFORMATION_SCHEMA", "sys"))).filterNot(_.name.startsWith("db_"))
        tables <- sysobjects.filter(_.xtype === "U") if tables.uid === schema.schema_id
        prop <- properties.filter(_.minor_id === 0).filter(_.theclass === 1) if tables.id === prop.major_id
    } yield (schema.name, tables.name, tables.id, prop.name, prop.value.asColumnOf[String])

    val tablesQuery = Compiled(queryTables())

    private def queryColumns(tableId: Int): Future[Seq[ColInfo]] = {
        logger.debug(s"Querying columns for table id $tableId")
        val columnsQuery = for {
            column <- sysColumns.filter(_.id === tableId)
            colType <- sysTypes.filterNot(_.name === "sysname") if colType.xtype === column.xtype
            prop <- properties.filter(_.minor_id === column.colid).filter(_.theclass === 1).map(p => (p.minor_id, p.name, p.value.asColumnOf[String]))
        } yield (column.colid, column.id, column.name, colType.name, column.length, column.isnullable, column.default, prop._2, prop._3)

        db.run(columnsQuery.result).map(x => x.map(ColInfo.tupled))
    }
}

case class TabInfo(schema: String, name: String, id: Int, propName: String, propVal: String)

case class ColInfo(colid: Int, id: Int, name: String, typeName: String, length: Int, nullable: Boolean, default: String, propName: String, propVal: String)
