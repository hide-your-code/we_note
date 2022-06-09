package com.minhdtm.example.we_note.presentations.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.minhdtm.example.we_note.presentations.model.NoteViewData
import com.minhdtm.example.we_note.presentations.theme.WeNoteTheme
import com.minhdtm.example.we_note.presentations.utils.toTimeAgo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteItem(
    modifier: Modifier = Modifier,
    note: NoteViewData,
    onClickNote: (NoteViewData) -> Unit = {},
    onDeleteNote: ((NoteViewData) -> Unit)? = null,
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .clickable {
                    onClickNote.invoke(note)
                }
                .padding(10.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(20.dp))

                    Text(
                        text = note.timeStamp.toTimeAgo(),
                        modifier = Modifier.padding(start = 5.dp),
                        style = MaterialTheme.typography.bodySmall,
                    )
                }

                if (onDeleteNote != null) {
                    IconButton(
                        onClick = {
                            onDeleteNote.invoke(note)
                        },
                        modifier = Modifier.size(30.dp),
                    ) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                    }
                }
            }

            Text(
                text = note.title.ifBlank { "No title" },
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 10.dp)
            )

            Text(
                text = note.description.ifBlank { "No description" },
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 5.dp)
            )
        }
    }
}

@Preview
@Composable
fun NoteItemPreview() {
    WeNoteTheme {
        NoteItem(
            note = NoteViewData(
                id = "", title = "Title", description = "Description", timeStamp = System.currentTimeMillis()
            )
        )
    }
}
