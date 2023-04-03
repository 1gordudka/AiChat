package com.igordudka.mylove.data.network.chat

import com.igordudka.mylove.data.network.OpenAIApiService

class ChatNetworkRepository(
    private val openAIApiService: OpenAIApiService
) {

    fun getMessage(request: ChatRequest) = openAIApiService.getMessage(request = request)
}