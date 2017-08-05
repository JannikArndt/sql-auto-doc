package de.jannikarndt.sqlautodoc

import de.jannikarndt.sqlautodoc.MSSQL.SqlServerDoc

object TableInfo {

    /**
      * Querys the system tables of the database given in the options.
      *
      * @param options Options containing database type, url, user and password
      * @return
      */
    def For(options: Options): Seq[TableInfo] = {
        options.dbType match {
            case SupportedDBs.MSSQL => SqlServerDoc.QuerySystemTables(options)
            case _ => throw DatabaseNotSupported("Sorry, this database is not supported yet.")
        }
    }
}

case class TableInfo(schema: String, name: String, id: Int, description: String, columns: Seq[ColumnInfo])

case class ColumnInfo(name: String, id: Int, colType: String, length: Int, nullable: Boolean, default: String, Properties: Seq[(String, String)]) {
    def example: String = Properties.filter(_._1 contains "Example").map(_._2).headOption.getOrElse("_")

    def comment: String = Properties.filter(_._1 contains "MS_Description").map(_._2).headOption.getOrElse("_")
}

