package com.project.splice.auth.data.remote.dto

data class SignupResponse(
    val id: String,
    val email: String,
    val name: String,
    val photoUrl: String,
    val token: String? = null
)
