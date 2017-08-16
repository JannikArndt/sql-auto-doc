package de.jannikarndt.sqlautodoc.markdown

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths, StandardOpenOption}

import com.typesafe.scalalogging.Logger

object File {
    val logger = Logger(this.getClass)

    /**
      * Creates a file from the given string
      *
      * @param path     Filepath
      * @param markdown The content of the file
      */
    def Create(path: String, markdown: String): Unit = {
        logger.debug(s"Writing to file $path")
        val filepath = Paths.get(path)
        Files.createDirectories(filepath.getParent)
        Files.write(filepath, markdown.getBytes(StandardCharsets.UTF_8), StandardOpenOption.TRUNCATE_EXISTING)
        logger.debug(s"Done.")
    }
}
