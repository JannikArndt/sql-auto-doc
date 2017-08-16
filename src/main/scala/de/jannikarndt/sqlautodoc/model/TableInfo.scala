package de.jannikarndt.sqlautodoc.model

import de.jannikarndt.sqlautodoc.MSSQL.SqlServerDoc
import de.jannikarndt.sqlautodoc.configuration.{Options, SupportedDBs}
import de.jannikarndt.sqlautodoc.exceptions.DatabaseNotSupportedException

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
            case _ => throw DatabaseNotSupportedException("Sorry, this database is not supported yet.")
        }
    }
}

case class TableInfo(schema: String, name: String, id: Int, description: String, columns: Seq[ColumnInfo])
