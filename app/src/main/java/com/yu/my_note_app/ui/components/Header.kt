package com.yu.my_note_app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun Header(modifier: Modifier = Modifier,
           title: String = "My Note App",
           suffixIcon: ImageVector? = null,
           suffixIconOnPush: () -> Unit = {}
           ){
    Row(modifier.fillMaxWidth()
        .height(70.dp)
        .background(color = MaterialTheme.colorScheme.primaryContainer)
        .border(width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)
        .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(40.dp)                                              // 背景の器のサイズ
                .background(MaterialTheme.colorScheme.primary,
                    RoundedCornerShape(percent = 30)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Done,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = modifier.width(10.dp))
        Text(title,
            style = MaterialTheme.typography.titleLarge)

        Spacer(Modifier.weight(1f))

        if (suffixIcon != null)
            IconButton(
                onClick = suffixIconOnPush
            ) {
                Icon(suffixIcon, contentDescription = "suffix button")
            }
    }
}