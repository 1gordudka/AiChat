package com.igordudka.aichat.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {

    @Insert
    suspend fun addMessage(message: ChatMessage)

    @Delete
    suspend fun deleteAllMessages(list: List<ChatMessage>)

    @Query("SELECT * FROM messages")
    fun getAllMessages() : Flow<List<ChatMessage>>
}