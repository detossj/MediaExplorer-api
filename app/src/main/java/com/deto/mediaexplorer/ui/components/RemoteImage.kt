package com.deto.mediaexplorer.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun RemoteImage(link: String) {
    AsyncImage(
        model = link,
        contentDescription = "Imagen desde internet",
        modifier = Modifier
            .fillMaxWidth()
            .size(300.dp)
    )
}