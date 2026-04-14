package com.example.mygithubrepo.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mygithubrepo.BuildConfig
import com.example.mygithubrepo.model.GitHubRepo
import com.example.mygithubrepo.network.RetrofitClient
import kotlinx.coroutines.launch

class GitHubViewModel : ViewModel() {
    var repos by mutableStateOf<List<GitHubRepo>>(emptyList())
    var isLoading by mutableStateOf(false)

    fun exchangeCodeForToken(code: String) {
        viewModelScope.launch {
            isLoading = true
            try {
                // 1. 換 Token
                val response = RetrofitClient.authInstance.getAccessToken(
                    clientId = BuildConfig.CLIENT_ID,
                    clientSecret = BuildConfig.CLIENT_SECRET,
                    code = code
                )

                val result = RetrofitClient.instance.getUserReposWithToken("Bearer ${response.accessToken}")
                repos = result
            } catch (e: Exception) {
                Log.e("GitHubOAuth", "Error: ${e.message}", e)
            } finally {
                isLoading = false
            }
        }
    }
}