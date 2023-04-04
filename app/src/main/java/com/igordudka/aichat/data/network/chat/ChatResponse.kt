package com.igordudka.aichat.data.network.chat

import com.google.gson.annotations.SerializedName

data class ChatResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("object")
    val objectt: String,
    @SerializedName("created")
    val created: Int,
    @SerializedName("choices")
    val choices: List<Choice>,
    @SerializedName("usage")
    val usage: Usage
)

data class Choice(
    @SerializedName("index")
    val index: Int,
    @SerializedName("message")
    val message: ResponseMessage,
    @SerializedName("finish_reason")
    val finish_reason: String
)

data class ResponseMessage(
    @SerializedName("role")
    val role: String,
    @SerializedName("content")
    val content: String
)

data class Usage(
    @SerializedName("prompt_tokens")
    val prompt_tokens: Int,
    @SerializedName("completion_tokens")
    val completion_tokens: Int,
    @SerializedName("total_tokens")
    val total_tokens: Int
)