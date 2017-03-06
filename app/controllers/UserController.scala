package controllers

import models.{LogInUser, RegisterUser}
import play.api.data._
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}

class UserController extends Controller {

//   def validatePassword(password: String, confirmPassword: String): Boolean = {
//     if (password==confirmPassword)
//       true
//     else
//       false
//   }

  val registerUser = Form(
    mapping(
      "firstName" -> nonEmptyText,
      "middleName" -> text,
      "lastName" -> nonEmptyText,
      "username" -> nonEmptyText,
      "password" -> nonEmptyText,
      "confirmPassword" -> nonEmptyText,   // .verifying(validatePassword(password , confirmPassword )),
      "contactNumber" -> nonEmptyText,
      "gender" -> text,
      "age" -> number(min = 18, max = 75),
      "hobbies" -> nonEmptyText
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
        BadRequest(views.html.signInPage())
      },
      userData => {
        val data = RegisterUser("Ramandeep","", "kaur","ramandeep","mountaindew","mountaindew","9910091476","Female",
          23,"Reading Novels")

        val newUser = models.LogInUser
        Ok(views.html.profilePage(data))
      }
    )
  }

  def profile = Action {
    Ok(views.html.profilePage(RegisterUser("Ramandeep","", "kaur","ramandeep","mountaindew","mountaindew","9910091476","Female",
      23,"Reading Novels"))).withSession("connected" -> "ramandeep")
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
          23,"Reading Novels")
        Ok(views.html.profilePage(newUser)).flashing("registeredUser"-> "Registration Done Successsfully")
      }
    )
  }

}
