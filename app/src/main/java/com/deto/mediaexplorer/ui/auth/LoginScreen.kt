package com.deto.mediaexplorer.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.deto.mediaexplorer.HomePage
import com.deto.mediaexplorer.Login
import com.deto.mediaexplorer.R
import com.deto.mediaexplorer.Register
import com.deto.mediaexplorer.ui.AppViewModelProvider
import com.deto.mediaexplorer.ui.components.CustomOutlinedTextField
import com.example.compose.onPrimaryContainerLight
import com.example.compose.primaryLight

@Composable
fun LoginScreen(
    viewModel: AuthViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navController: NavHostController
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var error by remember { mutableStateOf(false) }
    var error2 by remember { mutableStateOf(false) }

    val authState = viewModel.authState

    LaunchedEffect(authState) {
        if (authState is AuthUiState.Success) {
            navController.navigate(HomePage) {
                popUpTo(Login) { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp)
            .padding(top = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_round),
            contentDescription = "icon",
            modifier = Modifier
                .size(100.dp)
                .padding(bottom = 8.dp),
            contentScale = ContentScale.Fit
        )

        Text(
            text = stringResource(R.string.name_app),
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )


        Spacer(modifier = Modifier.height(32.dp))


        Text(
            text = stringResource(R.string.login),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        CustomOutlinedTextField(
            value = email,
            onValueChange = { email = it },
            icon = R.drawable.mail_24px,
            label = R.string.login_email_label,
            placeholder = R.string.login_email_placeholder,
            supportingText = R.string.login_email_supportingText,
            isError = error,
            iconTint = onPrimaryContainerLight,
            textStyle = TextStyle(Color.Black)
        )

        CustomOutlinedTextField(
            value = password,
            onValueChange = { password = it },
            icon = R.drawable.password_24px,
            label = R.string.login_password_label,
            placeholder = R.string.login_password_placeholder,
            supportingText = R.string.login_password_supportingText,
            isError = error2,
            iconTint = onPrimaryContainerLight,
            textStyle = TextStyle(Color.Black)
        )

        Button(
            onClick = {
                error = email.isBlank()
                error2 = password.isBlank()

                if (!error && !error2) {
                    viewModel.login(email, password)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text(stringResource(R.string.sign_in))
        }

        Spacer(modifier = Modifier.padding(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(stringResource(R.string.login_message), fontSize = 16.sp)
            TextButton(onClick = { navController.navigate(Register) }) {
                Text(stringResource(R.string.login_message_button), color = primaryLight, fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        when (authState) {
            is AuthUiState.Loading -> {
                CircularProgressIndicator()
            }

            is AuthUiState.Success -> {
                Text(
                    text = stringResource(R.string.auth_successful),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            is AuthUiState.Error -> {
                Text(
                    text = authState.message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            else -> {}

        }

    }

}

