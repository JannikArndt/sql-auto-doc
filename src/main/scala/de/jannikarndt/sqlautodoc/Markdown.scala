package de.jannikarndt.sqlautodoc

object Markdown {

    /**
      * Creates a markdown-string from several TableInfo-objects
      *
      * @param tableInfos A list of TableInfo-objects
      * @return
      */
    def From(tableInfos: Seq[TableInfo]): String = {
        tableInfos.map(From).mkString(System.lineSeparator())
    }

    private def From(tableInfo: TableInfo): String = {
        val _id = math.max(tableInfo.columns.map(_.id.toString.length).max, 4)
        val _name = math.max(tableInfo.columns.map(_.name.length).max, 6)
        val _type = math.max(tableInfo.columns.map(col => col.colType.length + col.length.toString.length + 2).max, 14)
        val _nullable = math.max(tableInfo.columns.map(_.nullable.toString.length).max, 10)
        val _default = math.max(tableInfo.columns.map(_.default.length).max, 9)
        val _example = math.max(tableInfo.columns.map(_.example.length).max, 9)
        val _comment = math.max(tableInfo.columns.map(_.comment.length).max, 9)

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

        val columnStrings = tableInfo.columns.map(From(_, _id, _name, _type, _nullable, _default, _example, _comment)).mkString(System.lineSeparator())

        Seq(s"# ${tableInfo.schema}.${tableInfo.name}", "", tableInfo.description, "", tableHeader, tableBorder, columnStrings, System.lineSeparator()).mkString(System.lineSeparator())
    }

    private def From(columnInfo: ColumnInfo, _id: Int, _name: Int, _type: Int, _nullable: Int, _default: Int, _example: Int, _comment: Int): String = Seq(
        columnInfo.id.toString.padTo(_id, " ").mkString,
        columnInfo.name.padTo(_name, " ").mkString,
        (columnInfo.colType + "(" + columnInfo.length + ")").padTo(_type, " ").mkString,
        columnInfo.nullable.toString.padTo(_nullable, " ").mkString,
        columnInfo.default.padTo(_default, " ").mkString,
        columnInfo.example.padTo(_example, " ").mkString,
        columnInfo.comment.padTo(_comment, " ").mkString
    ).mkString("| ", " | ", " |")
}
