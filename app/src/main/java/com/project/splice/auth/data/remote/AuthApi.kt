package com.project.splice.auth.data.remote

import com.project.splice.auth.data.remote.dto.SignupRequest
import com.project.splice.auth.data.remote.dto.SignupResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApi {

    @Headers(
        "Accept: */*",
        "Content-Type: application/json",
        "User-Agent: curl/8.2.1"
    )
    @POST("auth/signup")
    suspend fun signup(
        @Body request: SignupRequest
    ): SignupResponse

    companion object {
        const val BASE_URL = "https://rancidity-enjoyer-ranging.ngrok-free.dev/"
    }
}
