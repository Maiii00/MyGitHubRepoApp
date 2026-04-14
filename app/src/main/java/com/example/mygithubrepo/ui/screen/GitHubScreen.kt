package com.example.mygithubrepo.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mygithubrepo.model.GitHubRepo
import com.example.mygithubrepo.viewmodel.GitHubViewModel

@Composable
fun GitHubScreen(
    viewModel: GitHubViewModel,
    onLoginClick: () -> Unit,
    onRepoClick: (String) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        if (viewModel.repos.isEmpty() && !viewModel.isLoading) {
            Button(
                onClick = onLoginClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("使用 GitHub 帳號登入")
            }
        }

        if (viewModel.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn {
                items(viewModel.repos) { repo ->
                    RepoItem(repo = repo, onItemClick = onRepoClick)
                }
            }
        }
    }
}

@Composable
fun RepoItem(repo: GitHubRepo, onItemClick: (String) -> Unit) {
    Card(
        onClick = { onItemClick(repo.repoUrl) },
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = repo.name, style = MaterialTheme.typography.titleLarge)
            Row(modifier = Modifier.padding(top = 8.dp)) {
                Text(text = "Stars: ${repo.stars}", modifier = Modifier.padding(end = 16.dp))
                Text(text = "Language: ${repo.language ?: "Unknown"}")
            }
        }
    }
}