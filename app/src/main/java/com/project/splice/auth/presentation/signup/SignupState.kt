package com.project.splice.auth.presentation.signup

import com.project.splice.auth.domain.model.User

data class SignupState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String? = null
)
