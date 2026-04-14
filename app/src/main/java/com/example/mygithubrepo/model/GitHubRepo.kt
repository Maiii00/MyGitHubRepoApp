package com.example.mygithubrepo.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GitHubRepo(
    val name: String,
    @SerialName("stargazers_count")
    val stars: Int,
    val language: String? = null,
    @SerialName("html_url")
    val repoUrl: String
)