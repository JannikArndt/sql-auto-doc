package de.jannikarndt.sqlautodoc

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Path, Paths, StandardOpenOption}

import com.typesafe.scalalogging.Logger

object File {
    val logger = Logger(this.getClass)

    /**
      * Creates a file from the given string
      * @param options Options containing the output-filename
      * @param markdown The content of the file
      */
    def Create(options: Options, markdown: String): Unit = {
        if (!options.outputFile.isEmpty)
        {
            logger.debug(s"Writing to file ${options.outputFile}")
            Files.write(Paths.get(options.outputFile), markdown.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE)
            logger.debug(s"Done.")
        }
        else{
            logger.debug("No output file given, writing to console instead.")
            println(markdown)
        }
    }
}
