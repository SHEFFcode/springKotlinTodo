package com.sheffmachine.task_app.service

import com.sheffmachine.task_app.data.Priority
import com.sheffmachine.task_app.data.Task
import com.sheffmachine.task_app.model.TaskCreateDto
import com.sheffmachine.task_app.model.TaskDto
import com.sheffmachine.task_app.model.TaskUpdateDto
import com.sheffmachine.task_app.repository.TaskRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.util.*

@ExtendWith(MockKExtension::class)
@ActiveProfiles("test")
class TaskServiceTest {

    @RelaxedMockK
    lateinit var mockRepository: TaskRepository

    @InjectMockKs
    lateinit var taskService: TaskService

    private val task = Task()
    private lateinit var createRequest: TaskCreateDto

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        createRequest = TaskCreateDto(
            description = "hello-world",
            isReminderSet = true,
            isTaskOpen = false,
            createdOn = LocalDateTime.now(),
            priority = Priority.MEDIUM.toString()
        )
    }

    @Test
    fun getTaskById() {

    }

    @Test
    fun getTaskBySimpleId() {}

    @Test
    fun `make sure getAllTasks works`() {
        // Arrange
        val firstTask = TaskDto(
            id = UUID.randomUUID(),
            description = "hello-world",
            createdOn = LocalDateTime.now(),
            priority = Priority.MEDIUM.toString(),
            simpleId = 1L,
            isTaskOpen = true,
            isReminderSet = false,
        )
        val secondTask = TaskDto(
            id = UUID.randomUUID(),
            description = "hello-world",
            createdOn = LocalDateTime.now(),
            priority = Priority.MEDIUM.toString(),
            simpleId = 2L,
            isTaskOpen = false,
            isReminderSet = false,
        )
        val expectedTaskDTOs = listOf(firstTask, secondTask)
        val expectedTasks = listOf(
            Task(
                id = firstTask.id,
                description = "hello-world",
                createdOn = firstTask.createdOn,
                priority = Priority.MEDIUM,
                simpleId = 1L,
                isTaskOpen = true,
                isReminderSet = false,
            ),
            Task(
                id = secondTask.id,
                description = "hello-world",
                createdOn = secondTask.createdOn,
                priority = Priority.MEDIUM,
                simpleId = 2L,
                isTaskOpen = false,
                isReminderSet = false,
            )
        )
        every { mockRepository.findAll() } returns expectedTasks.toMutableList()
        // Act
        val tasks = taskService.getAllTasks()
        // Assert
        assertThat(tasks).containsExactlyInAnyOrder(*expectedTaskDTOs.toTypedArray())
    }

    @Test
    fun `make sure update task works`() {
        // Arrange
        val existingTaskDto = TaskCreateDto(
            description = "hello-world",
            createdOn = LocalDateTime.now(),
            priority = Priority.MEDIUM.toString(),
            isTaskOpen = true,
            isReminderSet = false,
        )

        val existingTask = Task(
            id = UUID.randomUUID(),
            description = "hello-world",
            createdOn = existingTaskDto.createdOn,
            priority = Priority.MEDIUM,
            isTaskOpen = true,
            isReminderSet = false,
        )

        val taskWithUpdatedDescription = existingTask.copy(description = "goodbye-friends")

        every { mockRepository.save(any()) } returns existingTask
        every { mockRepository.findTaskById(any()) } returns taskWithUpdatedDescription

        val savedTask = taskService.createTask(existingTaskDto)
        // Act

        val updatedTask = taskService.updateTask(savedTask.id, TaskUpdateDto(description = "goodbye-friends"))

        // Assert
        val foundTask = taskService.getTaskById(updatedTask.id)
        assertThat(updatedTask.description).isEqualTo(foundTask.description)

    }
}