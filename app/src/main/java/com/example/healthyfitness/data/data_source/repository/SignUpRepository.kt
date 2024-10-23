package com.example.healthyfitness.data.data_source.repository

import android.content.SharedPreferences
import com.example.healthyfitness.data.data_source.remote.retrofit.api.services.ApiService
import com.example.healthyfitness.data.data_source.remote.retrofit.api.requests.SignUpRequest
import com.example.healthyfitness.data.data_source.remote.retrofit.api.responses.SignUpResponse

class SignUpRepository(private val authApi: ApiService,
                       private val prefs: SharedPreferences
) {
    suspend fun signUp(firstName: String, lastName: String, email: String, password: String): Result<SignUpResponse> {
        return try {
            val response = authApi.signUp(SignUpRequest(firstName, lastName, email, password))
            if (response.isSuccessful) {
                response.body()?.let { signUpResponse ->
                    prefs.edit().putString("first_name", firstName).apply()
                    prefs.edit().putString("auth_token", signUpResponse.token).apply()

                    Result.success(signUpResponse)
                } ?: Result.failure(Exception("Sign up failed: Empty response body"))
            } else {
                Result.failure(Exception("Sign up failed: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    fun getFirstName(): String? {
        return prefs.getString("first_name", "User")
    }
}
