package controllers

import models.Task

import javax.inject._
import play.api._
import play.api.data.Form
import play.api.data.Forms.{mapping, nonEmptyText, number}
import play.api.data.validation.Constraints
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class  HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController with play.api.i18n.I18nSupport {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */

    //methods that execute/return an action that handles the request (action returns a result that represents the HTTP response to send back to the web browser :

    //Here the action returns a 200 OK response filled with HTML content provided by a template defined in the index view:
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def sumOf2Num(num1: Int, num2: Int) = Action { implicit request: Request[_] =>
    val sum: Int = num1 + num2
    Ok(views.html.sum(sum))
  }

  //action renders a 200 OK result filled with the HTML content rendered by the tasks view (which calls the tasks list) and the task form:
  def listOfTasks()= Action { implicit request: Request[_] =>
    Ok(views.html.tasks(Task.getAllTasks(), taskForm))
  }

  def addTask = Action { implicit request: Request[_] =>
    taskForm.bindFromRequest.fold(
      errors => BadRequest(views.html.tasks(Task.getAllTasks(), errors)),
      taskName => {
        Task.addTask(taskName)
        Redirect(routes.HomeController.listOfTasks())
      }
    )
  }

  def deleteTask(id: Int) = Action { implicit request: Request[_] =>
    Task.deleteTask(id)
    Redirect(routes.HomeController.listOfTasks())
  }

  //Form object encapsulates an HTML form definition, including validation constraints
  //Here the Form will check that the label "taskName" provided by the user is not empty
  // taskForm: Form[String] since it is a form generating a simple String
  val taskForm = Form(
    "taskName" -> nonEmptyText
  )

}
