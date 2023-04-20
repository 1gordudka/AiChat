package com.igordudka.aichat.data.network.chat


data class ChatRequest(
    val model: String = "gpt-3.5-turbo",
    val messages: List<Message>,
    val max_tokens: Int = 3000,
    val stream: Boolean = false,
    val temperature: Double = 0.6,
)

data class Message(
    val content: String,
    val role: String
)