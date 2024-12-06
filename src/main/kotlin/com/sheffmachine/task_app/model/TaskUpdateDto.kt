package com.sheffmachine.task_app.model

import com.sheffmachine.task_app.data.Priority
import java.time.LocalDateTime
import java.util.*

data class TaskUpdateDto(
    val description: String?,
    val isReminderSet: Boolean?,
    val isTaskOpen: Boolean?,
    val priority: Priority?,
)
