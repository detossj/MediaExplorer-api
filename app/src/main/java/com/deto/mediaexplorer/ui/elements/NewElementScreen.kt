package com.deto.mediaexplorer.ui.elements

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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.deto.mediaexplorer.R
import com.deto.mediaexplorer.ui.AppViewModelProvider
import com.deto.mediaexplorer.ui.components.CustomOutlinedTextField
import com.deto.mediaexplorer.ui.components.CustomTopAppBar
import com.example.compose.onPrimaryContainerLight
import com.example.compose.secondaryContainerDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewElementScreen(navController: NavController, categoryId: Int, viewModel: NewElementViewModel = viewModel(factory = AppViewModelProvider.Factory)){

    LaunchedEffect(Unit) {
        viewModel.resetUiState()
    }

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var icon by remember { mutableStateOf("") }

    var error by remember { mutableStateOf(false) }
    var error2 by remember { mutableStateOf(false) }
    var error3 by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    val classification = listOf<Int>(1,2,3,4,5)
    var selectedClassification by remember { mutableStateOf(classification[0]) }

    val elementState = viewModel.newElementUiState
    var shouldRedirect by remember { mutableStateOf(true) }

    // Redirección si se agregó un elemento con éxito
    LaunchedEffect(elementState) {
        if (shouldRedirect && elementState is NewElementUiState.Success) {
            shouldRedirect = false
            navController.popBackStack()
        }
    }

    Scaffold(

        modifier = Modifier.fillMaxSize(),
        containerColor = secondaryContainerDark,
        contentColor = onPrimaryContainerLight,
        topBar = {
            CustomTopAppBar(
                navController,
                stringResource(R.string.newelement_title),
                stringResource(R.string.newelement_subtitle)
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
                            error2 = description.isBlank()
                            error3 = icon.isBlank()

                            if( !error && !error2 && !error3){

                               viewModel.addElement(title,description,icon,selectedClassification,categoryId)
                            }

                        },
                        shape = CircleShape,
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(stringResource(R.string.newelement_bottom))
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
            modifier = Modifier.padding(innerPadding),

        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(horizontal = 40.dp),
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
                CustomOutlinedTextField(
                    value = description,
                    onValueChange = {
                        description = it
                        error2 = it.isBlank()
                    },

                    icon = R.drawable.description_24px,
                    label = R.string.form_description,
                    placeholder = R.string.form_description,
                    supportingText = R.string.supportingText_description,
                    isError = error2
                )

                CustomOutlinedTextField(
                    value = icon,
                    onValueChange = {
                        icon = it
                        error3 = it.isBlank()
                    },

                    icon = R.drawable.link_24px,
                    label = R.string.form_icon,
                    placeholder = R.string.form_icon,
                    supportingText = R.string.supportingText_icon,
                    isError = error3
                )

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = selectedClassification.toString(),
                        leadingIcon = {
                            Icon(
                                painterResource(R.drawable.star_24px),
                                contentDescription = "title",
                                tint = Color.White
                            )
                        },
                        onValueChange = {},
                        readOnly = true,
                        label = { Text(stringResource(R.string.form_classification)) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded)},
                        modifier = Modifier
                            .menuAnchor(type = MenuAnchorType.PrimaryEditable, enabled = true)
                            .fillMaxWidth()
                            .padding(vertical = 20.dp),


                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        classification.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(item.toString()) },
                                onClick = {
                                    selectedClassification = item
                                    expanded = false
                                }
                            )
                        }
                    }


                }



            }
        }

    }

}