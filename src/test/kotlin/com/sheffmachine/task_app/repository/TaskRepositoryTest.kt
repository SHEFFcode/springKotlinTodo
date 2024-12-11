package com.sheffmachine.task_app.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import java.util.UUID

@DataJpaTest(properties = ["spring.jpa.properties.javax.persistence.validation.mode=none"])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Sql("classpath:test-data.sql")
class TaskRepositoryTest {
    @Autowired
    private lateinit var repository: TaskRepository

    private val recordsInDb = 3
    private val openRecords = 1
    private val closeRecords = 2

    @Test
    fun `make sure task is committed`() {
        // Arrange
        val taskIdOfSavedTask = UUID.fromString("123e4567-e89b-12d3-a456-426655440000")
        // Act
        val taskInRepository = repository.findTaskById(taskIdOfSavedTask)
        // Assert
        assertNotNull(taskInRepository)
    }

    @Test
    fun `check that number of records matches`() {
        // Arrange
        // Act
        val allTasks = repository.findAll()
        // Assert
        assertThat(allTasks).hasSize(recordsInDb)
    }

    @Test
    fun `when a task is deleted, the list size is decremented by 1`() {
        // Arrange
        val taskToDelete = UUID.fromString("123e4567-e89b-12d3-a456-426655440000")
        // Act
        repository.deleteById(taskToDelete)
        // Assert
        val allTasks = repository.findAll()
        assertThat(allTasks).hasSize(recordsInDb - 1) // We deleted one task
    }

    @Test
    fun `confirm open and closed tasks are correct`() {
        // Arrange
        val allTasks = repository.findAll()
        // Act
        val (openTasks, closedTasks) = allTasks.partition { it.isTaskOpen }
        // Assert
        assertThat(openTasks).hasSize(openTasks.size)
        assertThat(closedTasks).hasSize(closedTasks.size)
    }

    @Test
    fun `another way to confirm task counts`() {
        // Arrange
        val openTasks = repository.queryAllOpenTasks()
        val closedTasks = repository.queryAllClosedTasks()
        // Act
        // Assert
        assertThat(openTasks).hasSize(openTasks.size)
        assertThat(closedTasks).hasSize(closedTasks.size)
    }
}