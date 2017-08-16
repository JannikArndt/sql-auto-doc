package de.jannikarndt.sqlautodoc.exceptions

case class DatabaseNotSupportedException(private val message: String = "",
                                         private val cause: Throwable = None.orNull)
    extends Exception(message, cause)
