package models

import anorm.SqlParser._
import anorm._
import play.api.db.Database

case class Photo(id: Option[Long], user: String, description: String, url: Option[String])

object Photo {
  private val parser = {
    get[Option[Long]]("id") ~
      get[String]("user") ~
      get[String]("description") ~
      get[Option[String]]("url") map {
      case id ~ user ~ description ~ url => Photo(id, user, description, url)
    }
  }

  def insert(db: Database, photo: Photo) = {
    db.withConnection { implicit connection =>
      val id = SQL(
        """
            INSERT INTO photos(id, user, description, url)
            VALUES ((SELECT NEXT VALUE FOR photo_id_seq), {user}, {description}, {url})
        """).on(
        'user -> photo.user,
        'description -> photo.description,
        'url -> photo.url
      ).executeInsert(scalar[Long].single)
      Photo(Some(id), photo.user, photo.description, photo.url)
    }
  }

  def findById(db: Database, id: Long): Option[Photo] = {
    db.withConnection { implicit connection =>
      SQL("SELECT * from photos where id = {id}").on('id -> id).as(parser.singleOpt)
    }
  }
}
