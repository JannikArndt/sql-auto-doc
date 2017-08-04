package de.jannikarndt.sqlautodoc.MSSQL

import shapeless.{Generic, HNil}
import slick.jdbc.SQLServerProfile.api._
import slickless._

case class SysColumn(name: String, id: Int, xtype: Int, colid: Int, length: Int, isnullable: Boolean, default: String)

//noinspection TypeAnnotation
class SysColumns(tag: Tag) extends Table[SysColumn](tag, Some("sys"), "syscolumns") {

    def name = column[String]("name", O.PrimaryKey)

    def id = column[Int]("id")

    def xtype = column[Int]("xtype")

    def colid = column[Int]("colid")

    def length = column[Int]("length")

    def isnullable = column[Boolean]("isnullable")

    def default = column[String]("cdefault")

    def * = (name :: id :: xtype :: colid :: length :: isnullable :: default :: HNil).mappedWith(Generic[SysColumn])
}

