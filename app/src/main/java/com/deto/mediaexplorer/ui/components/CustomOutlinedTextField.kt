package com.deto.mediaexplorer.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp


@Composable
fun CustomOutlinedTextField(value: String, onValueChange: (String) -> Unit, modifier: Modifier = Modifier, icon: Int, label: Int, placeholder: Int, supportingText: Int, isError: Boolean, iconTint: Color = Color.White) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        leadingIcon = {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "icon",
                tint = iconTint
            )
        },
        label = { Text(stringResource(id = label)) },
        placeholder = { Text(stringResource(id = placeholder)) },
        isError = isError,
        supportingText = {
            if (isError) Text(stringResource(id = supportingText))
        },
        minLines = 1,
        maxLines = 1
    )
}