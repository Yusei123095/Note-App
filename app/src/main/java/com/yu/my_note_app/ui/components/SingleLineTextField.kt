package com.yu.my_note_app.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SingleLineTextField(label:String, text: String, onChangeText: (String) -> Unit){
    OutlinedTextField(

        value = text,
        onValueChange = onChangeText,
        textStyle = MaterialTheme.typography.bodyMedium,
        placeholder = {
            Text(
                label,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        maxLines = 1,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            focusedBorderColor = MaterialTheme.colorScheme.outline,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline
        ),
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 10.dp)
    )
}