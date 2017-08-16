package de.jannikarndt.sqlautodoc.configuration

object OutputFormat {

    sealed trait OutputFormat

    case object OneFile extends OutputFormat

    case object OneFilePerSchema extends OutputFormat

    case object OneFilePerTable extends OutputFormat

    val OutputFormat = Seq(OneFile, OneFilePerSchema, OneFilePerTable)
}
