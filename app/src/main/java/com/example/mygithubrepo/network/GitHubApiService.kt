package com.example.mygithubrepo.network

import com.example.mygithubrepo.model.GitHubRepo
import retrofit2.http.GET
import retrofit2.http.Header

interface GitHubApiService {
    @GET("user/repos")
    suspend fun getUserReposWithToken(
        @Header("Authorization") token: String
    ): List<GitHubRepo>
}