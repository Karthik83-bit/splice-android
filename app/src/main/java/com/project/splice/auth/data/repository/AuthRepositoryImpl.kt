package com.project.splice.auth.data.repository

import com.project.splice.auth.data.remote.AuthApi
import com.project.splice.auth.data.remote.dto.SignupRequest
import com.project.splice.auth.domain.model.User
import com.project.splice.auth.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val api: AuthApi
) : AuthRepository {
    override suspend fun signup(
        email: String,
        password: String,
        name: String,
        photoUrl: String
    ): Result<User> {
        return try {
            val response = api.signup(
                SignupRequest(email, password, name, photoUrl)
            )
            Result.success(
                User(
                    id = response.id,
                    email = response.email,
                    name = response.name,
                    photoUrl = response.photoUrl
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
