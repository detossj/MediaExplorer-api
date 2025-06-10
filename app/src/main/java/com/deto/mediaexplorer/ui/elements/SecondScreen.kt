package com.deto.mediaexplorer.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.deto.mediaexplorer.ElementScreenPage
import com.deto.mediaexplorer.NewElementScreenPage
import com.deto.mediaexplorer.R
import com.deto.mediaexplorer.ui.AppViewModelProvider
import com.example.compose.onPrimaryContainerLight
import com.example.compose.onTertiaryDark
import com.example.compose.onTertiaryLight
import com.example.compose.primaryContainerDark
import com.example.compose.secondaryContainerDark
import com.example.compose.surfaceContainerDark
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import com.deto.mediaexplorer.ui.categories.CategoriesUiState
import com.deto.mediaexplorer.ui.components.CustomAlertDialog
import com.deto.mediaexplorer.ui.components.CustomTopAppBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondScreen(navController: NavController, categoryId: Int, viewModel: SecondViewModel = viewModel(factory = AppViewModelProvider.Factory)){

    LaunchedEffect(Unit) {
        viewModel.refreshElements()
    }

    val uiState = viewModel.elementsUiState
    var selectedElementId by remember { mutableIntStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomTopAppBar(
                stringResource(R.string.second_title),
                stringResource(R.string.second_subtitle)
            )
        },
        containerColor = secondaryContainerDark,
        contentColor = onPrimaryContainerLight,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(NewElementScreenPage(categoryId))},
                containerColor = surfaceContainerDark,
                contentColor = onTertiaryLight

            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        },
        bottomBar = {
            BottomAppBar() {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextButton(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            if(selectedElementId == 0){
                                showDialog = false
                            }
                            else{
                                showDialog = true
                            }
                        }
                    ) {
                        Text(stringResource(R.string.second_bottom_delete))
                    }

                    TextButton(
                        modifier = Modifier.weight(1f),
                        onClick = { if( selectedElementId == 0 ) {} else navController.navigate(ElementScreenPage(selectedElementId)) },
                        shape = CircleShape,
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonColors(
                            containerColor = primaryContainerDark,
                            contentColor = Color.White,
                            disabledContentColor = Color.White,
                            disabledContainerColor = Color.Transparent
                        )
                    ) {
                        Text(stringResource(R.string.second_bottom_view))
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
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxHeight()
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            when (uiState) {
                is ElementsUiState.Loading -> {
                    Text(text = "Loading categories...")
                }

                is ElementsUiState.Error -> {
                    Text(text = "Error: ${uiState.message}")
                }

                is ElementsUiState.Success -> {
                    val elements = uiState.elements

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(1),
                        contentPadding = PaddingValues(20.dp),
                        modifier = Modifier.fillMaxWidth()

                    ) {
                        items(elements) { element ->
                            Card(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .clickable { selectedElementId = element.id },
                                colors = CardColors(
                                    contentColor = Color.White,
                                    containerColor = if (selectedElementId == element.id) onTertiaryDark else primaryContainerDark,
                                    disabledContentColor = Color.White,
                                    disabledContainerColor = Color.Transparent
                                )
                            ) {

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(20.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Columna izquierda: imagen
                                    Column(
                                        modifier = Modifier
                                            .weight(1f),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        if (element.imagen == null) {
                                            Image(
                                                painter = painterResource(R.drawable.no_photography_24px),
                                                contentDescription = "Image Element",
                                                modifier = Modifier.size(200.dp)
                                            )

                                        } else {
                                            Image(
                                                painter = painterResource(element.imagen!!),
                                                contentDescription = "Image Element",
                                                modifier = Modifier.size(200.dp)
                                            )
                                        }

                                    }

                                    // Columna derecha: título y descripción
                                    Column(
                                        modifier = Modifier
                                            .weight(2f)
                                            .padding(start = 16.dp),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.Start
                                    ) {

                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.End,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                        ) {
                                            Text(
                                                text = element.classification.toString(),
                                                fontSize = 18.sp
                                            )
                                            Icon(
                                                painter = painterResource(R.drawable.star_24px),
                                                contentDescription = "classification",
                                                modifier = Modifier
                                                    .padding(start = 4.dp)
                                                    .size(18.dp)
                                            )
                                        }
                                        Text(
                                            text = element.title,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold
                                        )

                                        Text(
                                            text = if (element.description.length > 100) element.description.take(
                                                100
                                            ) + "..." else element.description,
                                            fontSize = 14.sp
                                        )


                                    }

                                }

                            }
                        }


                    }

                    /* CustomAlertDialog(
                    showDialog,
                    {
                        showDialog = false
                    },
                    {
                        viewModel.deleteElementById(selectedElementId)
                        showDialog = false
                        selectedElementId = 0
                    },
                    stringResource(R.string.alertdialog_title_element),
                    stringResource(R.string.alertdialog_message)
                )
                */

                }

                else -> {}
            }

        }


    }
    }

