package com.example.healthyfitness.presentation.screens.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthyfitness.data.data_source.remote.retrofit.api.responses.SignUpResponse
import com.example.healthyfitness.data.data_source.repository.SignUpRepository
import kotlinx.coroutines.launch

class SignUpViewModel(private val repository: SignUpRepository) : ViewModel() {
    private val _signUpResult = MutableLiveData<Result<SignUpResponse>>()
    val signUpResult: LiveData<Result<SignUpResponse>> = _signUpResult

    private val _validationErrors = MutableLiveData<Map<String, String>>(emptyMap())
    val validationErrors: LiveData<Map<String, String>> = _validationErrors

    fun signUp(firstName: String, lastName: String, email: String, password: String) {
        val errors = validateInput(firstName, lastName, email, password)
        if (errors.isNotEmpty()) {
            _validationErrors.value = errors
            return
        }

        viewModelScope.launch {
                _signUpResult.value = repository.signUp(firstName, lastName, email, password)
        }
    }

    private fun validateInput(firstName: String, lastName: String, email: String, password: String): Map<String, String> {
        val errors = mutableMapOf<String, String>()

        if (firstName.isBlank()) {
            errors["firstName"] = "First name cannot be empty"
        }

        if (lastName.isBlank()) {
            errors["lastName"] = "Last name cannot be empty"
        }

        if (!isValidEmail(email)) {
            errors["email"] = "Invalid email format"
        }

        if (!isValidPassword(password)) {
            errors["password"] = "Password must be at least 8 characters long and contain at least one capital letter"
        }

        return errors
    }

    private fun isValidEmail(email: String): Boolean {
        return email.contains("@") && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 8 && password.any { it.isUpperCase() }
    }

    fun clearValidationErrors() {
        _validationErrors.value = emptyMap()
    }

    fun clearFieldError(field: String) {
        _validationErrors.value = _validationErrors.value?.toMutableMap()?.apply { remove(field) }
    }
}


//    suspend fun onSignUp(): Boolean {
//        ValidationUtil.isValidEmail(email.toString())
//        ValidationUtil.isPasswordStrong(password.toString())
//        // Add basic validation logic here
//        if (password != confirmPassword) {
//            return false
//        }
//        val user = User(fullName.toString(), email.toString(), password.toString())
//        return userRepository.createUser(user)
//    }
