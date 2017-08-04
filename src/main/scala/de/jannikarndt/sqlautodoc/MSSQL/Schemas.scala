package de.jannikarndt.sqlautodoc.MSSQL

import shapeless.{Generic, HNil}
import slick.jdbc.SQLServerProfile.api._
import slickless._

case class Schema(name: String, schema_id: Int, principal_id: Int)

//noinspection TypeAnnotation
class Schemas(tag: Tag) extends Table[Schema](tag, Some("sys"), "schemas") {
    def name = column[String]("name", O.PrimaryKey)

    def schema_id = column[Int]("schema_id")

    def principal_id = column[Int]("principal_id")

    def * = (name :: schema_id :: principal_id :: HNil).mappedWith(Generic[Schema])
}
