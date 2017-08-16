package de.jannikarndt.sqlautodoc.model

abstract class ColumnInfo(val name: String,
                          val id: Int,
                          val colType: String,
                          val length: Int,
                          val nullable: Boolean,
                          val default: String,
                          val Properties: Seq[(String, String)]) {
    def example: String

    def comment: String
}

case class MssqlColumnInfo(override val name: String,
                           override val id: Int,
                           override val colType: String,
                           override val length: Int,
                           override val nullable: Boolean,
                           override val default: String,
                           override val Properties: Seq[(String, String)])
    extends ColumnInfo(name, id, colType, length, nullable, default, Properties) {

    override def example: String = Properties.filter(_._1 contains "Example").map(_._2).headOption.getOrElse("_")

    override def comment: String = Properties.filter(_._1 contains "MS_Description").map(_._2).headOption.getOrElse("_")

}
