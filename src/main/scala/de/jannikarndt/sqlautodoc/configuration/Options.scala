package de.jannikarndt.sqlautodoc.configuration

case class Options(connection: Connection, dbType: SupportedDBs.SupportedDB, outputFile: String, outputFolder: String, timeout: Int, format: OutputFormat.OutputFormat)
