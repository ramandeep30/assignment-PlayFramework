package controllers

import models.{LogInUser, RegisterUser}
import play.api.data._
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}

class UserController extends Controller {
//
//  val passwordCheck: Mapping[String] = nonEmptyText()
//    .verifying(passwordCheckConstraint)
  val registerUser = Form(
    mapping(
      "firstName" -> nonEmptyText,
      "middleName" -> text,
      "lastName" -> nonEmptyText,
      "userName" -> nonEmptyText,
      "password" -> nonEmptyText,
      "confirmPassword" -> nonEmptyText,
      "contactNumber" -> nonEmptyText,
      "gender" -> text,
      "age" -> number(min = 18, max = 75),
      "hobbies" -> list(text)
    )(RegisterUser.apply)(RegisterUser.unapply)
  )

  val logInUser = Form(
    mapping(
      "username" -> nonEmptyText,
      "password" -> nonEmptyText)
    (LogInUser.apply)(LogInUser.unapply)
  )


  def login = Action { implicit request =>
    logInUser.bindFromRequest.fold(
      formWithErrors => {
        println(">>>>>>>>>>>>>>>>>>>>>>>>>."+formWithErrors.toString)
        BadRequest(views.html.signInPage())
      },
      userData => {
        println(">>>>>>>>>>>>>>>>>>>>>>>>>."+userData.toString)
        val data = RegisterUser("Ramandeep","", "kaur","ramandeep","mountaindew","mountaindew","9910091476","Female",
          23,List("Listening Music","Reading Novels"))

        val newUser = models.LogInUser
        Ok(views.html.profilePage(data))
      }
    )
  }

  def profile = Action {
    Ok(views.html.profilePage(RegisterUser("Ramandeep","", "kaur","ramandeep","mountaindew","mountaindew","9910091476","Female",
      23,List("Listening Music","Reading Novels")))).withSession("connected" -> "ramandeep")
  }

  def signUp = Action {
    Ok(views.html.registrationPage())
  }

  def signIn = Action {
    Ok(views.html.signInPage())
  }

  def register = Action {
    implicit request =>
      val userData = request.body
      registerUser.bindFromRequest.fold(
      formWithErrors => {
        BadRequest(views.html.registrationPage())
      },
      userData => {
        val newUser = models.RegisterUser("Ramandeep","", "kaur","ramandeep","mountaindew","mountaindew","9910091476","Female",
          23,List("Listening Music","Reading Novels"))
        Ok(views.html.profilePage(newUser))
      }
    )
  }

}
