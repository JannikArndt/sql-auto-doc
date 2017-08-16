package de.jannikarndt.sqlautodoc.configuration

object SupportedDBs {

    sealed trait SupportedDB

    case object MSSQL extends SupportedDB

    case object Oracle extends SupportedDB

    case object PostgreSQL extends SupportedDB

    case object MySQL extends SupportedDB

    val supportedDBs = Seq(MSSQL)
}
