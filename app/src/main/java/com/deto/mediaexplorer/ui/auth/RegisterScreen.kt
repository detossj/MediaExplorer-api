package com.deto.mediaexplorer.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.deto.mediaexplorer.ui.AppViewModelProvider
import com.deto.mediaexplorer.ui.components.CustomOutlinedTextField
import com.example.compose.onPrimaryContainerLight
import com.example.compose.primaryLight

@Composable
fun RegisterScreen(
    viewModel: AuthViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navController: NavHostController
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var error by remember { mutableStateOf(false) }
    var error2 by remember { mutableStateOf(false) }
    var error3 by remember { mutableStateOf(false) }

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
            text = "Register",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomOutlinedTextField(
            value = name,
            onValueChange = { name = it },
            icon = R.drawable.person_24px,
            label = R.string.register_name_label,
            placeholder = R.string.register_name_placeholder,
            supportingText = R.string.register_name_supportingText,
            isError = error,
            iconTint = onPrimaryContainerLight,
            textStyle = TextStyle(Color.Black)
        )

        CustomOutlinedTextField(
            value = email,
            onValueChange = { email = it },
            icon = R.drawable.mail_24px,
            label = R.string.register_email_label,
            placeholder = R.string.register_email_placeholder,
            supportingText = R.string.register_email_supportingText,
            isError = error2,
            iconTint = onPrimaryContainerLight,
            textStyle = TextStyle(Color.Black)
        )

        CustomOutlinedTextField(
            value = password,
            onValueChange = { password = it },
            icon = R.drawable.password_24px,
            label = R.string.register_password_label,
            placeholder = R.string.register_password_placeholder,
            supportingText = R.string.register_password_supportingText,
            isError = error3,
            iconTint = onPrimaryContainerLight,
            textStyle = TextStyle(Color.Black)
        )

        Spacer(modifier = Modifier.height(16.dp))


        Button(
            onClick = {
                error = name.isBlank()
                error2 = email.isBlank()
                error3 = password.isBlank()

                if (!error && !error2 && !error3) {
                    viewModel.register(name,email,password)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text(stringResource(R.string.sign_up))
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(stringResource(R.string.register_message), fontSize = 16.sp)
            TextButton(onClick = { navController.navigate(Login) }) {
                Text(stringResource(R.string.register_message_button), color = primaryLight, fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        when (authState) {
            is AuthUiState.Loading -> CircularProgressIndicator()
            is AuthUiState.Success -> Text(
                text = stringResource(R.string.register_successful),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 8.dp)
            )
            is AuthUiState.Error -> Text(
                text = authState.message,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
            else -> {}
        }
    }


}
