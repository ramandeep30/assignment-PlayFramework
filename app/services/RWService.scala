package services

import models.{LogInUser, RegisterUser}

import scala.collection.mutable.ListBuffer


trait RWService {

  val listBuffer:ListBuffer[String]

  def read(loginUser: LogInUser): String

  def write(newUser: RegisterUser): String

  def getUser(username: String): RegisterUser

}
