package com.project.splice.auth.domain.use_case

import com.project.splice.auth.domain.model.User
import com.project.splice.auth.domain.repository.AuthRepository

class SignupUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        name: String,
        photoUrl: String
    ): Result<User> {
        if (email.isBlank() || password.isBlank() || name.isBlank()) {
            return Result.failure(Exception("Fields cannot be empty"))
        }
        return repository.signup(email, password, name, photoUrl)
    }
}
