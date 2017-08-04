package de.jannikarndt.sqlautodoc

import slick.jdbc.SQLServerProfile.api._

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps

object SqlAutoDoc {
    def main(args: Array[String]): Unit = {

        val db = Database.forURL("jdbc:sqlserver://localhost:1401",
            driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver", user = "SA", password = "Bla12345")

        try {

            val doc = new SqlServerDoc(db)

            val tableInfos: Seq[TableInfo] = Await.result(doc.getTableInfo, 20 seconds)

            print(tableInfos.map(_.toMarkdown).mkString(System.lineSeparator()))

        } finally db.close()
    }
}


