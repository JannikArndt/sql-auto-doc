package de.jannikarndt.sqlautodoc

import de.jannikarndt.sqlautodoc.configuration._
import de.jannikarndt.sqlautodoc.markdown._
import de.jannikarndt.sqlautodoc.model._

/**
  * SQL Auto Doc reads the url, user and password of a database from args, sqlautodoc.conf or environment vars,
  * queries the database's system tables and creates a Markdown-file from that structure.
  */
object SqlAutoDoc {

    /**
      * Create Markdown documentation
      *
      * Usage: java -jar SqlAutoDoc.jar -url=jdbc:sqlserver://localhost:1401 -user=SA -password=Bla12345 -output=README.md -timeout=20
      */
    def main(args: Array[String]): Unit = {

        val options = Configuration.FromArgsConfAndEnv(args)

        val tableInfos = TableInfo.For(options)

        Markdown.From(tableInfos, options)
    }
}
