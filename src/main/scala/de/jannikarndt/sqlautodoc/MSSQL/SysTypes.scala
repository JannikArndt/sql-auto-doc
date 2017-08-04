package de.jannikarndt.sqlautodoc.MSSQL

import shapeless.{Generic, HNil}
import slick.jdbc.SQLServerProfile.api._
import slickless._

case class SysType(name: String, xtype: Int)

//noinspection TypeAnnotation
class SysTypes(tag: Tag) extends Table[SysType](tag, Some("sys"), "systypes") {

    def name = column[String]("name", O.PrimaryKey)

    def xtype = column[Int]("xtype")

    def * = (name :: xtype :: HNil).mappedWith(Generic[SysType])
}

