package com.sheffmachine.task_app.repository

import com.sheffmachine.task_app.data.Task
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TaskRepository : JpaRepository<Task, UUID> {
    fun findTaskById(id: UUID): Task?
    fun findTaskBySimpleId(simpleId: Long): Task?


    @Query("SELECT * FROM task WHERE task_is_open = TRUE", nativeQuery = true)
    fun queryAllOpenTasks(): List<Task>

    @Query("SELECT * FROM task WHERE task_is_open = FALSE", nativeQuery = true)
    fun queryAllClosedTasks(): List<Task>

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN TRUE ELSE FALSE END FROM Task t WHERE t.description = :description")
    fun doesDescriptionExist(description: String): Boolean

}