package com.igordudka.mylove.data.network


import com.igordudka.mylove.data.network.chat.ChatRequest
import com.igordudka.mylove.data.network.chat.ChatResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface OpenAIApiService {

    @POST("chat/completions")
    fun getMessage(@Header("Authorization") apiKey: String = " Bearer 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",
                   @Body request: ChatRequest
    ): Call<ChatResponse>

}