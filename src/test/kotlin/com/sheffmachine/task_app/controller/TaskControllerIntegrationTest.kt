package com.sheffmachine.task_app.controller

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.sheffmachine.task_app.data.Priority
import com.sheffmachine.task_app.exception.TaskNotFoundException
import com.sheffmachine.task_app.model.TaskCreateDto
import com.sheffmachine.task_app.model.TaskDto
import com.sheffmachine.task_app.service.TaskService
import io.mockk.InternalPlatformDsl.toStr
import jakarta.persistence.EntityNotFoundException
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.awt.PageAttributes
import java.time.LocalDateTime
import java.util.*
import kotlin.test.Test

@ExtendWith(SpringExtension::class)
@WebMvcTest(controllers = [TaskController::class])
class TaskControllerIntegrationTest(@Autowired private val mockMvc: MockMvc) {

    @MockBean
    private lateinit var mockTaskService: TaskService

    private val taskId = UUID.randomUUID()
    private val createTaskDto = TaskDto(
        id = UUID.randomUUID(),
        simpleId = 1L,
        description = "hello-world",
        createdOn = LocalDateTime.now(),
        priority = Priority.MEDIUM.toStr(),
        isTaskOpen = true,
        isReminderSet = false,
    )
    private val taskMapper = jacksonObjectMapper().registerModule(JavaTimeModule())


    @BeforeEach
    fun setUp() {}

    @Test
    fun `given all tasks endpoint make sure they are all returned`() {
        // Arrange
        `when`(mockTaskService.getAllTasks()).thenReturn(listOf(createTaskDto))
        // Act
        val getAllTasksResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks"))
        // Assert
        getAllTasksResult.andExpect(MockMvcResultMatchers.status().isOk)
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(1))
    }

    @Test
    fun `when task id does not exist, then accept task it not found response`() {
        // Arrange
        `when`(mockTaskService.getTaskById(taskId)).thenThrow(TaskNotFoundException("Task with id $taskId does not exist")::class.java)
        // Act
        val result = mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks/$taskId"))
        // Assert
        result.andExpect(MockMvcResultMatchers.status().isNotFound)
    }

    @Test
    fun `when task id endpoint is called with a char instead of a uuid, expect not found message`() {
        // Arrange
        // Act
        val result = mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks/hello"))
        // Assert
        result.andExpect(MockMvcResultMatchers.status().isBadRequest)
    }
}