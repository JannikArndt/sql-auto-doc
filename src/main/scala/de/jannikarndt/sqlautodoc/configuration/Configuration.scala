package de.jannikarndt.sqlautodoc.configuration

import com.typesafe.config.{Config, ConfigFactory}
import com.typesafe.scalalogging.Logger
import de.jannikarndt.sqlautodoc.exceptions.{DatabaseNotSupportedException, MissingArgumentException, WrongFormatException}

object Configuration {
    val logger = Logger(this.getClass)
    val conf: Config = ConfigFactory.load("sqlautodoc.conf")

    /**
      * Load configuration from args, sqlautodoc.conf and environment vars
      *
      * @param args args given to main function
      * @return
      */
    def FromArgsConfAndEnv(args: Array[String]): Options = {
        val usage = "Usage: java -jar SqlAutoDoc.jar -url=jdbc:sqlserver://localhost:1401 -user=SA -password=Bla12345 -output=README.md -timeout=20"

        if (args.length == 0) {
            println(usage)
            sys.exit(0)
        }

        try {
            val inputArgs = args.toList.map(_.split("=", 2)).map(arg => arg(0).stripPrefix("-") -> arg(1)).toMap

            val connection = Connection(
                url = GetValue(inputArgs, "url"),
                user = GetValue(inputArgs, "user"),
                password = GetValue(inputArgs, "password")
            )

            val options = Options(
                connection = connection,
                dbType = GetDbTypeFromUrl(connection.url),
                outputFile = GetValue(inputArgs, "output", "tables.md"),
                outputFolder = GetValue(inputArgs, "outputFolder", ""),
                timeout = GetValue(inputArgs, "timeout", "20").toInt,
                format = GetValue(inputArgs, "format", "OneFile") match {
                    case "OneFile" => OutputFormat.OneFile
                    case "OneFilePerSchema" => OutputFormat.OneFilePerSchema
                    case "OneFilePerTable" => OutputFormat.OneFilePerTable
                    case wrong => throw WrongFormatException(s"The given format $wrong is not supported. Valid options are 'OneFile', 'OneFilePerSchema' and 'OneFilePerTable'.")
                }
            )

            logger.debug(s"Database Type in URL is ${options.dbType}.")
            logger.debug(s"output is set to ${options.outputFile}.")
            logger.debug(s"outputFolder is set to ${options.outputFolder}.")
            logger.debug(s"timeout is set to ${options.timeout} seconds.")
            logger.debug(s"format is set to ${options.format} (OneFile/OneFilePerSchema/OneFilePerTable).")

            options
        }
        catch {
            case throwable: Throwable => throw MissingArgumentException(s"Error parsing arguments: ${throwable.getMessage}\n" + usage)
        }
    }

    private def GetDbTypeFromUrl(url: String): SupportedDBs.SupportedDB = {
        url match {
            case str if str.contains("sqlserver") => SupportedDBs.MSSQL
            case _ => throw DatabaseNotSupportedException("Sorry, this database is not supported yet.")
        }
    }

    private def GetValue(args: Map[String, String], key: String, default: String = null): String = {

        if (args.contains(key))
            args(key)
        else if (conf.hasPath(key))
            conf.getString(key)
        else if (sys.env.contains(s"SQLAUTODOC_${key.toUpperCase}"))
            sys.env(key)
        else if (default != null)
            default
        else
            throw MissingArgumentException(s"Argument $key is missing in args, sqlautodoc.conf and environment (as SQLAUTODOC_${key.toUpperCase}). Please specify either.")
    }
}
