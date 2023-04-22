package com.igordudka.aichat.presentation.home.chat

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.igordudka.aichat.data.database.ChatMessage
import com.igordudka.aichat.data.database.OfflineChatRepository
import com.igordudka.aichat.data.network.chat.ChatNetworkRepository
import com.igordudka.aichat.data.network.chat.ChatRequest
import com.igordudka.aichat.data.network.chat.Message
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatNetworkRepository: ChatNetworkRepository,
    private val offlineChatRepository: OfflineChatRepository
) : ViewModel() {


    var isTyping = MutableStateFlow(false)


    var messages = offlineChatRepository.getAllMessages().map { it }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        null
    )

    @RequiresApi(Build.VERSION_CODES.O)
    fun onSendClicked(message: String){
        viewModelScope.launch (Dispatchers.IO){
            val simpleDateFormat = SimpleDateFormat("HH.mm")
            addMessage("user", message, simpleDateFormat.format(Date()))
            val messageList = arrayListOf<Message>()
            messageList.add(Message(message, "user"))
            val call = chatNetworkRepository.getMessage(
                ChatRequest(messages = messageList)
            )
            isTyping = MutableStateFlow(!call.isExecuted)
            val result = call.execute().body()?.choices?.get(0)?.message?.content
            isTyping = MutableStateFlow(!call.isExecuted)
            addMessage("bot", result!!, simpleDateFormat.format(Date()))
        }
    }


    suspend fun addMessage(
        author: String,
        message: String,
        time: String
    ) {
        if (message.isNotEmpty()) {
                offlineChatRepository.addMessage(
                    ChatMessage(message = message, author = author,
                        time = time)
                )
            }
    }

    fun deleteMessages(){
        viewModelScope.launch {
            messages.value?.let { offlineChatRepository.deleteAllMessages(it) }
        }
    }

}