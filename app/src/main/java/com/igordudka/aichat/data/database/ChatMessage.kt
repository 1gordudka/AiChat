package com.igordudka.aichat.data.database

import java.util.Date
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

data class ChatMessage(
    val author: String = "",
    val message: String = "",
    val time: String = "",
    val userId: String = "",
    val isCurrentUser: Boolean = false,
    val timeInSeconds: Long = Date().time.seconds.toLong(DurationUnit.SECONDS)
)