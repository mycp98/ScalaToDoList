package models

import scala.util.Random.nextInt

case class Task(taskId: Int, name: String)

//companion object to manage Task operations:
object Task {

  var taskList: List[Task] = List[Task]()

  def getAllTasks(): List[Task] = taskList

  def addTask(newTaskName: String): Unit = {
    // task to save = new task (generated id, newTaskName)
    var newTask = Task(nextInt(), newTaskName)
    taskList = newTask :: taskList
  }

  def deleteTask(id: Int): Unit = {
   taskList = taskList.filter(task => task.taskId != id)
  }
}
