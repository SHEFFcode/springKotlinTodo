package com.sheffmachine.task_app.service

import com.sheffmachine.task_app.data.Priority
import com.sheffmachine.task_app.data.Task
import com.sheffmachine.task_app.exception.BadRequestException
import com.sheffmachine.task_app.exception.TaskNotFoundException
import com.sheffmachine.task_app.model.TaskCreateDto
import com.sheffmachine.task_app.model.TaskDto
import com.sheffmachine.task_app.model.TaskUpdateDto
import com.sheffmachine.task_app.repository.TaskRepository
import org.springframework.stereotype.Service
import java.util.UUID
import kotlin.reflect.full.memberProperties

@Service
class TaskService(private val taskRepository: TaskRepository) {
    private fun entityToDtoMapping(task: Task): TaskDto = task.let {
        TaskDto(
            it.id!!,
            it.simpleId,
            it.description,
            it.isReminderSet,
            it.isTaskOpen,
            it.createdOn,
            it.priority.toString()
        )
    }

    private fun requestToEntityMapping(task: Task, request: TaskCreateDto): Task = request.let {
        task.description = it.description
        task.isReminderSet = it.isReminderSet
        task.isTaskOpen = it.isTaskOpen
        task.priority = Priority.valueOf(it.priority)
        return task
    }

    private fun checkTaskForId(id: UUID) {
        if (taskRepository.findTaskById(id) == null) throw TaskNotFoundException("Task with id $id not found")
    }

    private fun checkTaskForSimpleId(simpleId: Long) {
        if (taskRepository.findTaskBySimpleId(simpleId) == null) throw TaskNotFoundException("Task with id $simpleId not found")
    }

    fun getTaskById(id: UUID): TaskDto {
        checkTaskForId(id)
        val task = taskRepository.findTaskById(id)
        return entityToDtoMapping(task!!)
    }

    fun getAllTasks(): List<TaskDto> {
        return taskRepository.findAll().map { entityToDtoMapping(it) }
    }

    fun getAllOpenTasks(): List<TaskDto> {
        return taskRepository.queryAllOpenTasks().map { entityToDtoMapping(it) }
    }

    fun getAllClosedTasks(): List<TaskDto> {
        return taskRepository.queryAllClosedTasks().map { entityToDtoMapping(it) }
    }

    fun createTask(taskCreateDto: TaskCreateDto): TaskDto {
        if (taskRepository.doesDescriptionExist(taskCreateDto.description)) {
            throw BadRequestException("Task with description ${taskCreateDto.description} already exists")
        }

        val task = requestToEntityMapping(Task(), taskCreateDto)
        val savedTask = taskRepository.save(task)
        return entityToDtoMapping(savedTask)
    }

    fun updateTask(id: UUID, taskUpdateDto: TaskUpdateDto): TaskDto {
        checkTaskForId(id)
        val task = taskRepository.findTaskById(id)

        val updatedTask = task!!.apply {
            this.description = taskUpdateDto.description ?: this.description
            this.isReminderSet = taskUpdateDto.isReminderSet ?: this.isReminderSet
            this.isTaskOpen = taskUpdateDto.isTaskOpen ?: this.isTaskOpen
        }

        taskRepository.save(updatedTask)

        return entityToDtoMapping(updatedTask)
    }

    fun updateTaskBySimpleId(simpleId: Long, taskUpdateDto: TaskUpdateDto): TaskDto {
        checkTaskForSimpleId(simpleId)
        val task = taskRepository.findTaskBySimpleId(simpleId)
        val updatedTask = task!!.apply {
            this.description = taskUpdateDto.description ?: this.description
            this.isReminderSet = taskUpdateDto.isReminderSet ?: this.isReminderSet
            this.isTaskOpen = taskUpdateDto.isTaskOpen ?: this.isTaskOpen
        }

        taskRepository.save(updatedTask)

        return entityToDtoMapping(updatedTask)
    }

    fun deleteTask(id: UUID): UUID {
        checkTaskForId(id)
        taskRepository.deleteById(id)
        return id
    }

    fun deleteTaskBySimpleId(simpleId: Long): Long {
        checkTaskForSimpleId(simpleId)
        taskRepository.findTaskBySimpleId(simpleId)
        return simpleId
    }
}