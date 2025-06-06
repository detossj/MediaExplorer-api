package com.deto.mediaexplorer.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.onSecondaryLight
import com.example.compose.secondaryContainerDark
import com.example.compose.secondaryContainerLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar( title: String, subtitle: String){

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
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {

                Text(
                    text = title,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp
                )

                Text(
                    text = subtitle,
                    fontSize = 15.sp
                )

            }
        }

    )

}