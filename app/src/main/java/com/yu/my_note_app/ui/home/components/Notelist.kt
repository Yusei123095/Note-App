package com.yu.my_note_app.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yu.my_note_app.data.alarm.Alarm
import com.yu.my_note_app.ui.components.CustomizedPager
import com.yu.my_note_app.data.note.Note

@Composable
fun NoteList(modifier: Modifier = Modifier, alarms: List<Alarm>, notes: List<Note>){

    Column(modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
        ){
        if (alarms.isNotEmpty()) Text("タイマーメモ", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.secondary)
        CustomizedPager(
            pages = alarms.map { alarm ->
                {
                    AlarmCard(
                        alarm,
                        Modifier.padding(vertical = 10.dp),

                        )
                }
            }
        )

        if (notes.isNotEmpty()) Text("メモ", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)

        LazyColumn(

        ) {
            items( notes.size){
                index ->
                NoteCard(note = notes[index], Modifier.padding(vertical = 10.dp))
            }
        }
    }
}

@Composable
fun EmptyNoteList(modifier: Modifier = Modifier){
    Column(
        modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)                                              // 背景の器のサイズ
                .background(
                    MaterialTheme.colorScheme.primaryContainer,
                    RoundedCornerShape(percent = 30)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Filled.Create,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.size(30.dp)

            )
        }
        Text("No notes", style = MaterialTheme.typography.bodyLarge)
        Text("Let's write new notes", style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun DemoNoteList(modifier: Modifier = Modifier){
    Column(
        modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text("タイマーメモ", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.secondary)
        //TimerNoteCard(Modifier.padding(vertical = 10.dp))

        Text("メモ", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
        //NoteCard(Modifier.padding(vertical = 10.dp), note)


    }
}