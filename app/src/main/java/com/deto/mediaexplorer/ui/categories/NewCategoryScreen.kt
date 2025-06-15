package com.deto.mediaexplorer.ui.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.deto.mediaexplorer.HomePage
import com.deto.mediaexplorer.R
import com.deto.mediaexplorer.ui.AppViewModelProvider
import com.deto.mediaexplorer.ui.components.CustomOutlinedTextField
import com.deto.mediaexplorer.ui.components.CustomTopAppBar
import com.example.compose.onPrimaryContainerLight
import com.example.compose.secondaryContainerDark
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewCategoryScreen( navController: NavController, viewModel: NewCategoryViewModel = viewModel(factory = AppViewModelProvider.Factory)){

    LaunchedEffect(Unit) {
        viewModel.resetUiState()
    }

    var error by remember { mutableStateOf(false) }

    var title by remember { mutableStateOf("") }

    val categoryState = viewModel.newCategoryUiState
    var shouldRedirect by remember { mutableStateOf(true) }

    // Redirección si se agregó la categoria con éxito
    LaunchedEffect(categoryState) {
        if (shouldRedirect && categoryState is NewCategoryUiState.Success) {
            shouldRedirect = false
            navController.popBackStack()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = secondaryContainerDark,
        contentColor = onPrimaryContainerLight,
        topBar = {
            CustomTopAppBar(navController,
                stringResource(R.string.newcategory_title),
                stringResource(R.string.newcategory_subtitle)
            )
        },
        bottomBar = {
            BottomAppBar() {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextButton(
                        modifier = Modifier.fillMaxWidth(.7f),
                        onClick = {

                            error = title.isBlank()
                            if( !error ){
                                viewModel.addCategory(title)
                            }
                        },
                        shape = CircleShape,
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(stringResource(R.string.newcategory_bottom))
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = "advance",
                            modifier = Modifier.padding(0.dp)
                        )
                    }


                }
            }
        }


    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(horizontal = 40.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomOutlinedTextField(
                    value = title,
                    onValueChange = {
                        title = it
                        error = it.isBlank()
                    },
                    icon = R.drawable.title_24px,
                    label = R.string.form_title,
                    placeholder = R.string.form_title,
                    supportingText = R.string.supportingText_title,
                    isError = error
                )





            }



        }
    }

}

