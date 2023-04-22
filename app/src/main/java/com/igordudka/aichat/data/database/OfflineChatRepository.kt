package com.igordudka.aichat.data.database

class OfflineChatRepository(
    private val messageDao: MessageDao
) {

    suspend fun addMessage(chatMessage: ChatMessage) = messageDao.addMessage(chatMessage)
    suspend fun deleteAllMessages(list: List<ChatMessage>) = messageDao.deleteAllMessages(list)
    fun getAllMessages() = messageDao.getAllMessages()
}