package com.project.splice.auth.data.remote.dto

data class SignupRequest(
    val email: String,
    val password: String,
    val name: String,
    val photoUrl: String
)
