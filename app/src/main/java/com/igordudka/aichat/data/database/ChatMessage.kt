package com.igordudka.aichat.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

@Entity("messages")
data class ChatMessage(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Int = 0,
    @ColumnInfo("author")
    val author: String = "",
    @ColumnInfo("message")
    val message: String = "",
    @ColumnInfo("time")
    val time: String = ""
)