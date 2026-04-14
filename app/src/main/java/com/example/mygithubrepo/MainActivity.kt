package com.example.mygithubrepo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import com.example.mygithubrepo.model.GitHubRepo
import com.example.mygithubrepo.network.RetrofitClient
import com.example.mygithubrepo.ui.screen.GitHubScreen
import com.example.mygithubrepo.viewmodel.GitHubViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val viewModel: GitHubViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleIntent(intent)

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    GitHubScreen(
                        viewModel = viewModel,
                        onLoginClick = { startOAuthFlow() },
                        onRepoClick = { url ->
                            startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
                        }
                    )
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        intent?.data?.let { uri ->
            if (uri.toString().startsWith("mygithubapp://callback")) {
                uri.getQueryParameter("code")?.let { code ->
                    viewModel.exchangeCodeForToken(code)
                }
            }
        }
    }

    private fun startOAuthFlow() {
        val clientId = BuildConfig.CLIENT_ID
        val intent = Intent(
            Intent.ACTION_VIEW,
            "https://github.com/login/oauth/authorize?client_id=$clientId&scope=repo".toUri()
        )
        startActivity(intent)
    }
}