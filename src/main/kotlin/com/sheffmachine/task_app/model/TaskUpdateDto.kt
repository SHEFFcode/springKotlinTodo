package com.sheffmachine.task_app.model

import com.sheffmachine.task_app.data.Priority
import java.time.LocalDateTime
import java.util.*

data class TaskUpdateDto(
    val description: String? = null,
    val isReminderSet: Boolean? = null,
    val isTaskOpen: Boolean? = null,
    val priority: Priority? = null,
)
