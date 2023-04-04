package com.igordudka.aichat.data.network.chat

import com.igordudka.aichat.data.network.OpenAIApiService

class ChatNetworkRepository(
    private val openAIApiService: OpenAIApiService
) {

    fun getMessage(request: ChatRequest) = openAIApiService.getMessage(request = request)
}