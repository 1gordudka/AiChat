package com.igordudka.aichat.di


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.GsonBuilder
import com.igordudka.aichat.data.database.MessageDatabase
import com.igordudka.aichat.data.database.OfflineChatRepository
import com.igordudka.aichat.data.network.chat.ChatNetworkRepository
import com.igordudka.aichat.data.network.OpenAIApiService
import com.igordudka.aichat.data.preferences.UserPreferencesRepository
import com.igordudka.aichat.presentation.home.settings.SettingsViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


private const val LAYOUT_PREFERENCE_NAME = "layout_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = LAYOUT_PREFERENCE_NAME
)

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideOpenAIApiService() : OpenAIApiService {

        val gson = GsonBuilder()
            .setLenient()
            .create()

        val client = OkHttpClient.Builder()
            .readTimeout(360, TimeUnit.SECONDS)
            .writeTimeout(360, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://api.openai.com/v1/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(OpenAIApiService::class.java)
    }

    @Provides
    fun provideChatNetworkRepository() : ChatNetworkRepository {
        return ChatNetworkRepository(provideOpenAIApiService())
    }

    @Provides
    fun provideUserPreferencesRepository(@ApplicationContext context: Context) : UserPreferencesRepository{
        return UserPreferencesRepository(dataStore = context.dataStore)
    }

    @Provides
    fun provideOfflineChatRepository(@ApplicationContext context: Context) : OfflineChatRepository{
        return OfflineChatRepository(MessageDatabase.getDatabase(context).messageDao())
    }
}