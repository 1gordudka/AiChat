package com.igordudka.aichat.data.network.chat

import com.google.gson.annotations.SerializedName

data class ChatRequest(
    @SerializedName("messages")
    val messages: List<Message>,
    @SerializedName("model")
    val model: String,
)

data class Message(
    @SerializedName("content")
    val content: String,
    @SerializedName("role")
    val role: String
)