package com.deto.mediaexplorer.ui.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.deto.mediaexplorer.HomePage
import com.deto.mediaexplorer.Login
import com.deto.mediaexplorer.ui.AppViewModelProvider
import com.deto.mediaexplorer.AuthManager

@Composable
fun AuthManager(viewModel: AuthViewModel = viewModel(factory = AppViewModelProvider.Factory),
                navController: NavHostController
) {
    val authState = viewModel.authState

    LaunchedEffect(authState) {
        when (authState) {
            is AuthUiState.loggedIn -> {
                if (authState.logged) {
                    navController.navigate(HomePage) {
                        popUpTo(AuthManager) {
                            inclusive = true
                        } // Quitar la pantalla de la pila de navegación
                    }
                }
                if (!authState.logged) {
                    navController.navigate(Login) {
                        popUpTo(AuthManager) {
                            inclusive = true
                        } // Quitar la pantalla de la pila de navegación
                    }
                }
            }
            else -> {}
        }
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}