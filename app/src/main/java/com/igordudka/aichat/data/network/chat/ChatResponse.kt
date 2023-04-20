package com.igordudka.aichat.data.network.chat

import com.google.gson.annotations.SerializedName

data class ChatResponse(
    val choices: List<MessageResponse>
)

data class MessageResponse(
    val message: Message
)