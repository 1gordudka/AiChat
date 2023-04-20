package com.igordudka.aichat.data.network


import com.igordudka.aichat.data.network.chat.ChatRequest
import com.igordudka.aichat.data.network.chat.ChatResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Streaming

interface OpenAIApiService {

    @Streaming
    @Headers("Content-Type: application/json",
        "Authorization: Bearer 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")
    @POST("chat/completions")
    fun getMessage(
                   @Body request: ChatRequest
    ): Call<ChatResponse>

}