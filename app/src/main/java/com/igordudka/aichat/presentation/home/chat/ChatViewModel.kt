package com.igordudka.aichat.presentation.home.chat

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.igordudka.aichat.data.network.chat.ChatNetworkRepository
import com.igordudka.aichat.data.network.chat.ChatRequest
import com.igordudka.aichat.data.network.chat.ChatResponse
import com.igordudka.aichat.data.network.chat.Message
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.Response
import okhttp3.sse.EventSource
import okhttp3.sse.EventSourceListener
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatNetworkRepository: ChatNetworkRepository
) : ViewModel() {

    init {
        getMessages()
    }

    var isTyping = MutableStateFlow(false)


    private var _messages = MutableStateFlow(emptyList<Map<String, Any>>().toMutableList())
    val messages: StateFlow<MutableList<Map<String, Any>>> = _messages

    @RequiresApi(Build.VERSION_CODES.O)
    fun onSendClicked(message: String, context: Context){
        viewModelScope.launch (Dispatchers.IO){
            val simpleDateFormat = SimpleDateFormat("HH.mm")
            addMessage("user", message, simpleDateFormat.format(Date()),
                Firebase.auth.currentUser?.uid.toString(), Date().time.seconds.toLong(DurationUnit.SECONDS))
            val messageList = arrayListOf<Message>()
            messageList.add(Message(message, "user"))
            val call = chatNetworkRepository.getMessage(
                ChatRequest(messages = messageList)
            )
            isTyping = MutableStateFlow(!call.isExecuted)
            val result = call.execute().body()?.choices?.get(0)?.message?.content
            isTyping = MutableStateFlow(!call.isExecuted)
            addMessage("bot", result!!, simpleDateFormat.format(Date()),
                Firebase.auth.currentUser?.uid.toString(), Date().time.seconds.toLong(DurationUnit.SECONDS))
        }
    }


    fun addMessage(
        author: String,
        message: String,
        time: String,
        userId: String,
        timeInSeconds: Long
    ) {
        if (message.isNotEmpty()) {
            Firebase.firestore.collection("messages").document().set(
                hashMapOf(
                    "author" to author,
                    "message" to message,
                    "time" to time,
                    "userId" to userId,
                    "timeInSeconds" to timeInSeconds
                )
            ).addOnSuccessListener {}
        }
    }

    private fun getMessages() {
        Firebase.firestore.collection("messages")
            .orderBy("timeInSeconds")
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w("Tag", "Listen failed.", e)
                    return@addSnapshotListener
                }

                val list = emptyList<Map<String, Any>>().toMutableList()

                if (value != null) {
                    for (doc in value) {
                        val data = doc.data
                        if(Firebase.auth.currentUser?.uid.toString() == data["userId"].toString()){
                            list.add(data)
                        }
                    }
                }

                updateMessages(list)
            }
    }

    private fun updateMessages(list: MutableList<Map<String, Any>>) {
        _messages.value = list
    }

    fun deleteMessages(){
        Firebase.firestore.collection("messages")
            .addSnapshotListener { value, error ->
                if (value != null){
                    for (doc in value){
                        val data = doc.data
                        if(Firebase.auth.currentUser?.uid.toString() == data["userId"].toString()){
                            Firebase.firestore.collection("messages").document(doc.id).delete()
                        }
                    }
                }
            }
    }

}