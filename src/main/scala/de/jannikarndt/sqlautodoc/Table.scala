package de.jannikarndt.sqlautodoc

case class TableInfo(schema: String, name: String, id: Int, description: String, columns: Seq[ColumnInfo]) {
    def toMarkdown: String = {
        val _id = math.max(columns.map(_.id.toString.length).max, 4)
        val _name = math.max(columns.map(_.name.length).max, 6)
        val _type = math.max(columns.map(col => col.colType.length + col.length.toString.length + 2).max, 14)
        val _nullable = math.max(columns.map(_.nullable.toString.length).max, 10)
        val _default = math.max(columns.map(_.default.length).max, 9)
        val _example = math.max(columns.map(_.example.length).max, 9)
        val _comment = math.max(columns.map(_.comment.length).max, 9)

        val tableHeader = Seq(
            "ID".padTo(_id, " ").mkString,
            "Name".padTo(_name, " ").mkString,
            "Type(Length)".padTo(_type, " ").mkString,
            "Nullable".padTo(_nullable, " ").mkString,
            "Default".padTo(_default, " ").mkString,
            "Example".padTo(_example, " ").mkString,
            "Comment".padTo(_comment, " ").mkString
        ).mkString("| ", " | ", " |")

        val tableBorder = Seq(
            "-".padTo(_id, "-").mkString,
            "-".padTo(_name, "-").mkString,
            "-".padTo(_type, "-").mkString,
            "-".padTo(_nullable, "-").mkString,
            "-".padTo(_default, "-").mkString,
            "-".padTo(_example, "-").mkString,
            "-".padTo(_comment, "-").mkString
        ).mkString("| ", " | ", " |")

        val columnStrings = columns.map(_.toMarkdown(_id, _name, _type, _nullable, _default, _example, _comment)).mkString(System.lineSeparator())

        Seq(s"# $schema.$name", "", description, "", tableHeader, tableBorder, columnStrings, System.lineSeparator()).mkString(System.lineSeparator())
    }
}

case class ColumnInfo(name: String, id: Int, colType: String, length: Int, nullable: Boolean, default: String, Properties: Seq[(String, String)]) {
    def toMarkdown(_id: Int, _name: Int, _type: Int, _nullable: Int, _default: Int, _example: Int, _comment: Int): String = Seq(
        id.toString.padTo(_id, " ").mkString,
        name.padTo(_name, " ").mkString,
        (colType + "(" + length + ")").padTo(_type, " ").mkString,
        nullable.toString.padTo(_nullable, " ").mkString,
        default.padTo(_default, " ").mkString,
        example.padTo(_example, " ").mkString,
        comment.padTo(_comment, " ").mkString
    ).mkString("| ", " | ", " |")

    def example: String = Properties.filter(_._1 contains "Example").map(_._2).headOption.getOrElse("_")

    def comment: String = Properties.filter(_._1 contains "MS_Description").map(_._2).headOption.getOrElse("_")
}

