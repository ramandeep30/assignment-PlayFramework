package controllers

import com.google.inject.Inject
import play.api.mvc.{Action, Controller}
import services.RWService


class AuthorityController @Inject() (rWService: RWService) extends Controller{

  def manageAuthority = Action{
    println(rWService.listBuffer)
    val list = (for(i<-rWService.listBuffer)yield rWService.getUser(i)).toList
    println(list)
    Ok(views.html.userList(list))
  }

}
