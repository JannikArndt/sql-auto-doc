package de.jannikarndt.sqlautodoc.MSSQL

import shapeless.{Generic, HNil}
import slick.jdbc.SQLServerProfile.api._
import slickless._

case class ExtendedProperty(
                               theclass: Int,
                               class_desc: String,
                               major_id: Int,
                               minor_id: Int,
                               name: String,
                               value: String)

//noinspection TypeAnnotation
class ExtendedProperties(tag: Tag) extends Table[ExtendedProperty](tag, Some("sys"), "extended_properties") {
    def theclass = column[Int]("class")

    def class_desc = column[String]("class_desc")

    def major_id = column[Int]("major_id")

    def minor_id = column[Int]("minor_id")

    def name = column[String]("name")

    def value = column[String]("value", O.SqlType("VARCHAR(4000)"))

    def * = (theclass :: class_desc :: major_id :: minor_id :: name :: value :: HNil).mappedWith(Generic[ExtendedProperty])
}

