package com.deto.mediaexplorer.ui.components

import androidx.compose.foundation.layout.Box
import com.deto.mediaexplorer.R

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.deto.mediaexplorer.HomePage
import com.deto.mediaexplorer.Login
import com.deto.mediaexplorer.ui.AppViewModelProvider
import com.deto.mediaexplorer.ui.auth.AuthViewModel
import com.example.compose.onSecondaryLight
import com.example.compose.secondaryContainerDark
import com.example.compose.secondaryContainerLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar( navController: NavController, title: String, subtitle: String, viewModel: AuthViewModel = viewModel(factory = AppViewModelProvider.Factory)){

    LargeTopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp),
        colors = TopAppBarColors(
            containerColor = secondaryContainerDark,
            titleContentColor = onSecondaryLight,
            actionIconContentColor = secondaryContainerDark,
            scrolledContainerColor = secondaryContainerLight,
            navigationIconContentColor = secondaryContainerLight
        ),
        title = {
            Box(modifier = Modifier.fillMaxWidth()) {
                // Texto alineado a la izquierda
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 20.dp)
                ) {
                    Text(
                        text = title,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp
                    )
                    Text(
                        text = subtitle,
                        fontSize = 15.sp,
                        color = Color.White
                    )
                }

                // Botón alineado a la derecha
                Button(
                    onClick = { viewModel.logout()
                        navController.navigate(Login) {
                            popUpTo(HomePage) {
                                inclusive = true
                            } // Quitar la pantalla de la pila de navegación
                        }
                              },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(end = 20.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.logout_24px),
                        contentDescription = "logout",
                        tint = Color.Red
                    )
                }
            }
        }
    )

}