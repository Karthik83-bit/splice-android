package com.project.splice.auth.presentation.signup

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.splice.auth.domain.use_case.SignupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val signupUseCase: SignupUseCase
) : ViewModel() {

    private val _state = mutableStateOf(SignupState())
    val state: State<SignupState> = _state

    fun onSignup(email: String, password: String, name: String, photoUrl: String) {
        viewModelScope.launch {
            _state.value = SignupState(isLoading = true)
            val result = signupUseCase(email, password, name, photoUrl)
            
            result.onSuccess { user ->
                _state.value = SignupState(user = user)
            }
            result.onFailure { exception ->
                _state.value = SignupState(error = exception.localizedMessage ?: "An unknown error occurred")
            }
        }
    }
}
