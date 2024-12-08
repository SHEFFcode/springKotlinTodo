package com.sheffmachine.task_app.controller

import com.sheffmachine.task_app.model.TaskCreateDto
import com.sheffmachine.task_app.model.TaskDto
import com.sheffmachine.task_app.model.TaskUpdateDto
import com.sheffmachine.task_app.service.TaskService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping(value = ["/api"], produces = ["application/json"])
class TaskController(private val taskService: TaskService) {
    @GetMapping(value = ["/tasks"], produces = ["application/json"])
    fun getTasks(): ResponseEntity<List<TaskDto>> {
        val tasks = taskService.getAllTasks()
        return ResponseEntity.ok(tasks)
    }

    @GetMapping(value = ["tasks/open"], produces = ["application/json"])
    fun getOpenTasks(): ResponseEntity<List<TaskDto>> {
        val tasks = taskService.getAllOpenTasks()
        return ResponseEntity.ok(tasks)
    }

    @GetMapping(value = ["tasks/closed"], produces = ["application/json"])
    fun getClosedTasks(): ResponseEntity<List<TaskDto>> {
        val tasks = taskService.getAllClosedTasks()
        return ResponseEntity.ok(tasks)
    }

    @GetMapping(value = ["/tasks/{id}"], produces = ["application/json"])
    fun getTaskById(@PathVariable("id") id: UUID): ResponseEntity<TaskDto> {
        val task = taskService.getTaskById(id)
        return ResponseEntity.ok(task)
    }

    @GetMapping(value = ["/tasks/simple/{id}"], produces = ["application/json"])
    fun getTaskBySimpleId(@PathVariable("id") id: Long): ResponseEntity<TaskDto> {
        val task = taskService.getTaskBySimpleId(id)
        return ResponseEntity.ok(task)
    }

    @PostMapping(value = ["/tasks"], consumes = ["application/json"])
    fun createTask(@Valid @RequestBody task: TaskCreateDto): ResponseEntity<TaskDto> {
        val createdTaskDto = taskService.createTask(task)
        return ResponseEntity.ok(createdTaskDto)
    }

    @PatchMapping(value = ["/tasks/{id}"], consumes = ["application/json"])
    fun updateTask(@PathVariable id: UUID, @Valid @RequestBody task: TaskUpdateDto): ResponseEntity<TaskDto> {
        val updatedTask = taskService.updateTask(id, task)
        return ResponseEntity.ok(updatedTask)
    }

    @PatchMapping(value = ["tasks/simple/{id}"], consumes = ["application/json"])
    fun updateTaskBySimpleId(@PathVariable("id") simpleId: Long, @Valid @RequestBody task: TaskUpdateDto): ResponseEntity<TaskDto> {
        val updatedTask = taskService.updateTaskBySimpleId(simpleId, task)
        return ResponseEntity.ok(updatedTask)
    }

    @DeleteMapping(value = ["/tasks/{id}"], consumes = ["application/json"])
    fun deleteTask(@PathVariable("id") id: UUID): ResponseEntity<UUID> {
        val deletedId = taskService.deleteTask(id)
        return ResponseEntity.ok(deletedId)
    }

    @DeleteMapping(value = ["/tasks/simple/{id}"], consumes = ["application/json"])
    fun deleteTaskBySimpleId(@PathVariable("id") simpleId: Long): ResponseEntity<Long> {
        val deletedSimpleId = taskService.deleteTaskBySimpleId(simpleId)
        return ResponseEntity.ok(deletedSimpleId)
    }
}