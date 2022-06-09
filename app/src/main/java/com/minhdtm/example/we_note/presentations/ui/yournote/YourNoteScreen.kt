package com.minhdtm.example.we_note.presentations.ui.yournote

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.minhdtm.example.we_note.R
import com.minhdtm.example.we_note.presentations.WeNoteAppState
import com.minhdtm.example.we_note.presentations.component.NoteItem
import com.minhdtm.example.we_note.presentations.component.WeNoteScaffold
import com.minhdtm.example.we_note.presentations.model.NoteViewData

@Composable
fun YourNote(
    appState: WeNoteAppState,
    viewModel: YourNoteViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsState()

    YourNoteScreen(state = state, onDrawer = {
        appState.openDrawer()
    }, onClickAdd = {
        appState.navigateToAddNote()
    }, onClickNote = {
        appState.navigateToEditNote(it.id)
    }, onDeleteNote = {
        viewModel.deleteNote(it.id)
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YourNoteScreen(
    state: YourNoteViewState,
    onDrawer: () -> Unit = {},
    onClickAdd: () -> Unit = {},
    onClickNote: (NoteViewData) -> Unit = {},
    onDeleteNote: (NoteViewData) -> Unit = {},
) {
    WeNoteScaffold(
        modifier = Modifier.fillMaxSize(),
        state = state,
        topBar = {
            YourNoteAppBar(onDrawer = onDrawer)
        },
        floatingActionButton = {
            AddNoteButton(onClickAdd = onClickAdd)
        },
    ) { _, viewState ->
        val paddingBottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() + 10.dp

        if (state.listNote.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = stringResource(id = R.string.you_do_not_have_any_note), style = MaterialTheme.typography.titleLarge)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(start = 10.dp, end = 10.dp, bottom = paddingBottom),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(items = viewState.listNote, key = { it.id }) { item ->
                    NoteItem(
                        modifier = Modifier.fillMaxWidth(),
                        note = item,
                        onClickNote = onClickNote,
                        onDeleteNote = onDeleteNote,
                    )
                }
            }
        }
    }
}

@Composable
fun YourNoteAppBar(
    modifier: Modifier = Modifier,
    onDrawer: () -> Unit = {},
) {
    SmallTopAppBar(
        modifier = modifier.statusBarsPadding(),
        title = {
            Text(text = stringResource(id = R.string.your_note))
        },
        navigationIcon = {
            IconButton(onClick = onDrawer) {
                Icon(
                    imageVector = Icons.Outlined.Menu,
                    contentDescription = null,
                )
            }
        },
    )
}

@Composable
fun AddNoteButton(
    modifier: Modifier = Modifier,
    onClickAdd: () -> Unit = {},
) {
    FloatingActionButton(
        modifier = modifier.navigationBarsPadding(),
        onClick = onClickAdd,
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = null)
    }
}
