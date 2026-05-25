package com.project.splice.auth.domain.repository

import com.project.splice.auth.domain.model.User

interface AuthRepository {
    suspend fun signup(email: String, password: String, name: String, photoUrl: String): Result<User>
}
