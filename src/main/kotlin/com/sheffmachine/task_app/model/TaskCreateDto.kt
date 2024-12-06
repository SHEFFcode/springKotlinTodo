package com.sheffmachine.task_app.model

import com.sheffmachine.task_app.data.Priority
import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime
import java.util.*

data class TaskCreateDto (
    @NotBlank(message = "Task description cannot be blank")
    val description: String,
    val isReminderSet: Boolean,
    val isTaskOpen: Boolean,
    @NotBlank(message = "Task createdOn cannot be blank")
    val createdOn: LocalDateTime,
    val priority: String,
)