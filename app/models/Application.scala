package models

case class RegisterUser(firstName: String, middleName: String, lastName: String, userName: String, password: String,
                        confirmPassword: String, contactNumber: String, gender: String, age: Int, hobbies: List[String])

case class LogInUser(username: String, password: String)