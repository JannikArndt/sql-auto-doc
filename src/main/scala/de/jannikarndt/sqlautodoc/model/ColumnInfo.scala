package de.jannikarndt.sqlautodoc.model

case class ColumnInfo(name: String, id: Int, colType: String, length: Int, nullable: Boolean, default: String, Properties: Seq[(String, String)]) {
    def example: String = Properties.filter(_._1 contains "Example").map(_._2).headOption.getOrElse("_")

    def comment: String = Properties.filter(_._1 contains "MS_Description").map(_._2).headOption.getOrElse("_")
}
