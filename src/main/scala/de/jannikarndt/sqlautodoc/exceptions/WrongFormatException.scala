package de.jannikarndt.sqlautodoc.exceptions

case class WrongFormatException(private val message: String = "",
                                private val cause: Throwable = None.orNull)
    extends Exception(message, cause)
