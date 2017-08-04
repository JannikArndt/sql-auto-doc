package de.jannikarndt.sqlautodoc.MSSQL

import shapeless.{Generic, HNil}
import slick.jdbc.SQLServerProfile.api._
import slickless._

case class SysObject(name: String, id: Int, uid: Int, xtype: String)

//noinspection TypeAnnotation
class SysObjects(tag: Tag) extends Table[SysObject](tag, Some("sys"), "sysobjects") {
    def name = column[String]("name", O.PrimaryKey)

    def id = column[Int]("id")

    def uid = column[Int]("uid")

    def xtype = column[String]("xtype")

    def * = (name :: id :: uid :: xtype :: HNil).mappedWith(Generic[SysObject])
}
