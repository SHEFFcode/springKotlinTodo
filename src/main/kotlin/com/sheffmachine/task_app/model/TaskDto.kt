package com.sheffmachine.task_app.model

import java.time.LocalDateTime
import java.util.UUID

data class TaskDto(
    val id: UUID,
    val simpleId: Long,
    val description: String,
    val isReminderSet: Boolean,
    val isTaskOpen: Boolean,
    val createdOn: LocalDateTime,
    val priority: String,
)