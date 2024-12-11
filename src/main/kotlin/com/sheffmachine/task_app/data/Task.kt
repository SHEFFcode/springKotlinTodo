package com.sheffmachine.task_app.data

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime
import java.util.*


@Entity
@Table(name = "task", uniqueConstraints = [UniqueConstraint(name="uq_task_description", columnNames = ["description"])])
data class Task (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", insertable = false, updatable = false, nullable = false)
    val id: UUID? = null,

    @Column(name=  "simpleId", columnDefinition = "serial", insertable = false, updatable = false, nullable = false)
    val simpleId: Long = 0,

    @NotBlank
    @Column(name = "description", nullable = false, unique = true)
    var description: String = "",

    @Column(name = "is_reminder_set", nullable = false)
    var isReminderSet: Boolean = false,

    @Column(name = "is_task_open", nullable = false)
    var isTaskOpen: Boolean = true,

    @Column(name = "craeted_on", nullable = false)
    val createdOn: LocalDateTime = LocalDateTime.now(),

    @NotNull
    @Column(name = "priority")
    @Enumerated(EnumType.STRING)
    var priority: Priority = Priority.LOW
)