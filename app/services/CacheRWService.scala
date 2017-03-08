package services

import javax.inject.Inject

import models.{LogInUser, RegisterUser}
import org.mindrot.jbcrypt.BCrypt
import play.api.cache.CacheApi

import scala.collection.mutable.ListBuffer

class CacheRWService @Inject() (cache : CacheApi) extends RWService{

  override val listBuffer: ListBuffer[String] = ListBuffer[String]()

  override def read(loginUser: LogInUser): String = {
    val user: Option[RegisterUser] = cache.get[RegisterUser](loginUser.username)
    user match {
      case Some(x) =>
        println(s"verifying  ${loginUser.password}==${x.password}")
        if(BCrypt.checkpw(loginUser.password, x.password)) {
          if(!x.isRevoked) x.username else throw new Exception("Authorisation revoked")
        } else throw new Exception("Password doesn't Match")
      case None => throw new Exception("No user")
    }
  }

  override def write(newUser: RegisterUser): String = {
    val user: Option[RegisterUser] = cache.get[RegisterUser](newUser.username)
    user match {
      case Some(_) => throw new Exception("User Already Exists")
      case None =>
        cache.set(newUser.username,newUser)
        listBuffer += newUser.username
        println(listBuffer)
        newUser.username
    }
  }

  override def getUser(username: String) = {
    val user: Option[RegisterUser] = cache.get[RegisterUser](username)
    user match {
      case Some(x) => x
      case None => throw new Exception("User Not Found")
    }
  }

}
