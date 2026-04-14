package com.example.mygithubrepo.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object RetrofitClient {
    private const val BASE_URL = "https://api.github.com/"

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    private val contentType = "application/json".toMediaType()

    private fun createRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    val authInstance: GitHubAuthApiService by lazy {
        createRetrofit("https://github.com/").create(GitHubAuthApiService::class.java)
    }

    val instance: GitHubApiService by lazy {
        createRetrofit("https://api.github.com/").create(GitHubApiService::class.java)
    }
}