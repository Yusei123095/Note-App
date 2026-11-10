package com.yu.my_note_app.ui.editor.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.yu.my_note_app.ui.components.SingleLineTextField
import com.yu.my_note_app.ui.editor.NoteForm

@Composable
fun NoteFormPanel(modifier: Modifier = Modifier,
                  note: NoteForm,
                  onTitleChange: (String) -> Unit,
                  onTextChanged: (String) -> Unit
                ){
    Column(modifier
        .verticalScroll(rememberScrollState())) {

    SingleLineTextField(
        label = "Title",
        text = note.title,
        onChangeText = onTitleChange
    )

        TextField(
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyMedium,
            value = note.content,
            onValueChange = onTextChanged,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
            ),
            placeholder = {
                Text(
                    "Noteを書こう",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        )

    }

}