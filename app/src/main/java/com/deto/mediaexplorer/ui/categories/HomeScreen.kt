package com.deto.mediaexplorer.ui.categories

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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
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
import com.deto.mediaexplorer.NewCategoryScreenPage
import com.deto.mediaexplorer.SecondScreenPage
import com.deto.mediaexplorer.ui.components.CustomAlertDialog
import com.deto.mediaexplorer.ui.components.CustomTopAppBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen( navController: NavController, viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)){

    LaunchedEffect(Unit) {
        viewModel.refreshCategories()
    }

    val uiState = viewModel.categoriesUiState
    var selected by remember { mutableIntStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = secondaryContainerDark,
        contentColor = onPrimaryContainerLight,
        topBar = { CustomTopAppBar(navController,stringResource(R.string.home_title),stringResource(R.string.home_subtitle))},
        floatingActionButton = {
            FloatingActionButton(
                onClick = {  navController.navigate(NewCategoryScreenPage)},
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
                            if(selected == 0){
                                showDialog = false
                            }
                            else{
                                showDialog = true
                            }

                        }
                    ) {
                        Text(stringResource(R.string.home_bottom_delete))
                    }

                    TextButton(
                        modifier = Modifier.weight(1f),
                        onClick = { if( selected == 0 ) {} else navController.navigate(
                            SecondScreenPage(selected)) },
                        shape = CircleShape,
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonColors(
                            containerColor = primaryContainerDark,
                            contentColor = Color.White,
                            disabledContentColor = Color.White,
                            disabledContainerColor = Color.Transparent
                        )
                    ) {
                        Text(stringResource(R.string.home_bottom_view))
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
                is CategoriesUiState.Loading -> {
                    Text(text = "Loading categories...")
                }

                is CategoriesUiState.Error -> {
                    Text(text = "Error: ${uiState.message}")
                }

                is CategoriesUiState.Success -> {
                    val categories = uiState.categories

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(20.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(categories) { category ->
                            Card(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .clickable { selected = category.id },
                                colors = CardColors(
                                    contentColor = Color.White,
                                    containerColor = if (selected == category.id) onTertiaryDark else primaryContainerDark,
                                    disabledContentColor = Color.White,
                                    disabledContainerColor = Color.Transparent
                                )
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight()
                                        .padding(horizontal = 20.dp, vertical = 35.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    if (category.icon == null) {
                                        Icon(
                                            painterResource(R.drawable.noimage),
                                            contentDescription = "Category Box",
                                            modifier = Modifier.size(60.dp)
                                        )
                                    } else {
                                        Icon(
                                            painterResource(category.icon),
                                            contentDescription = "Category Box",
                                            modifier = Modifier.size(60.dp)
                                        )
                                    }

                                    Text(text = category.title)
                                }
                            }
                        }
                    }
                }

                else -> {}

            }

            CustomAlertDialog(
                showDialog,
                { showDialog = false},
                {
                    viewModel.deleteCategoryById(selected)
                    showDialog = false
                    selected = 0
                },
                stringResource(R.string.alertdialog_title_category),
                stringResource(R.string.alertdialog_message)
            )
        }
    }
    }





