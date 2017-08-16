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
                keepAliveConnection = true,
                executor = AsyncExecutor("test1", numThreads=10, queueSize=1000))

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
        val tablesFuture: Future[Seq[(String, String, Int, String, String)]] = queryTables()

        tablesFuture.flatMap(getTablesInfo)
    }

    private def getTablesInfo(tables: Seq[(String, String, Int, String, String)]): Future[Seq[TableInfo]] = {
        Future.sequence(tables.map(getTableInfo))
    }

    private def getTableInfo(table: (String, String, Int, String, String)): Future[TableInfo] = {
        queryColumns(table._3)
            .flatMap(getColumnsInfo)
            .map(col => TableInfo(table._1, table._2, table._3, table._5, col))
    }

    private def getColumnsInfo(cols: Seq[(Int, Int, String, String, Int, Boolean, String)]): Future[Seq[ColumnInfo]] = {
        Future.sequence(cols.map(getColumnInfos))
    }

    private def getColumnInfos(col: (Int, Int, String, String, Int, Boolean, String)): Future[ColumnInfo] = {
        logger.debug(s"Querying property for major id ${col._2} and minor id ${col._1}")

        db.run(getPropertiesQueryCompiled(col._2, col._1).result)
            .map(props => MssqlColumnInfo(col._3, col._2, col._4, col._5, col._6, col._7, props))
    }

    private def queryTables(): Future[Seq[(String, String, Int, String, String)]] = {
        val tablesQuery = for {
            schema <- schemas.filterNot(_.name.inSet(Seq("dbo", "guest", "INFORMATION_SCHEMA", "sys"))).filterNot(_.name.startsWith("db_"))
            tables <- sysobjects.filter(_.xtype === "U") if tables.uid === schema.schema_id
            prop <- properties.filter(_.minor_id === 0).filter(_.theclass === 1) if tables.id === prop.major_id
        } yield (schema.name, tables.name, tables.id, prop.name, prop.value.asColumnOf[String])

        db.run(tablesQuery.result) // .sortBy(_._1)
    }



    private def queryColumns(tableId: Int): Future[Seq[(Int, Int, String, String, Int, Boolean, String)]] = {
        logger.debug(s"Querying columns for table id $tableId")
        val columnsQuery = for {
            column <- sysColumns.filter(_.id === tableId)
            colType <- sysTypes.filterNot(_.name === "sysname") if colType.xtype === column.xtype
        } yield (column.colid, column.id, column.name, colType.name, column.length, column.isnullable, column.default)

        db.run(columnsQuery.result)
    }

    private def getPropertiesQuery(majorId: Rep[Int], minorId: Rep[Int]) =
        properties
            .filter(_.major_id === majorId)
            .filter(_.minor_id === minorId)
            .filter(_.theclass === 1)
            .map(col => (col.name, col.value.asColumnOf[String]))

    private val getPropertiesQueryCompiled = Compiled(getPropertiesQuery _)
}
