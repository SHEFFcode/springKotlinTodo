package com.sheffmachine.task_app.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
data class BadRequestException(override val message: String) : RuntimeException()
