package com.deto.mediaexplorer.ui.auth

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deto.mediaexplorer.data.remote.TokenManager
import com.deto.mediaexplorer.data.remote.services.AuthService
import com.deto.mediaexplorer.model.LoginRequest
import com.deto.mediaexplorer.model.RegisterRequest
import kotlinx.coroutines.launch

sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    data class loggedIn(var logged: Boolean) : AuthUiState()
    data class Success(val token: String) : AuthUiState()
    data class Error(val message: String) : AuthUiState()

}

class AuthViewModel(
    private val authService: AuthService,
    private val context: Context
) : ViewModel() {

    var authState: AuthUiState by mutableStateOf(AuthUiState.Idle)
        private set

    init {
        isLoggedIn()
    }
    fun login(email: String, password: String) {

        viewModelScope.launch {

            authState = AuthUiState.Loading

            try {

                val response = authService.login(LoginRequest(email, password))
                TokenManager.saveToken(context, response.token)
                authState = AuthUiState.Success(response.token)

            } catch (e: Exception) {

                authState = AuthUiState.Error(e.message ?: "Login failed")

            }
        }
    }

    fun register(name: String, email: String, password: String) {

        viewModelScope.launch {

            authState = AuthUiState.Loading

            try {

                val response = authService.register(RegisterRequest( name, email, password, password ))
                TokenManager.saveToken(context, response.token)
                authState = AuthUiState.Success(response.token)


            } catch (e: Exception) {

                authState = AuthUiState.Error(e.message ?: "Login failed")

            }
        }
    }

    fun logout() {
        viewModelScope.launch {

            try {
                authService.logout()
                TokenManager.clearToken(context)
                authState = AuthUiState.Idle
            } catch (_: Exception) {}
        }
    }

    private fun isLoggedIn() {
    viewModelScope.launch {
            try {
                val response = authService.getUser()
                authState = AuthUiState.loggedIn(true)
            } catch (e: Exception) {
                authState = AuthUiState.loggedIn(false)
            }
        }
    }
}
