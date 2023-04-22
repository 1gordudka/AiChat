package com.igordudka.aichat.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ChatMessage::class], version = 1, exportSchema = false)
abstract class MessageDatabase : RoomDatabase() {

    abstract fun messageDao() : MessageDao

    companion object {
        @Volatile
        private var Instance: MessageDatabase? = null

        fun getDatabase(context: Context): MessageDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, MessageDatabase::class.java, "message_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}