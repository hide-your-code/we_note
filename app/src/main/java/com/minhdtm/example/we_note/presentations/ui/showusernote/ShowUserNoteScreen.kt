package com.minhdtm.example.we_note.presentations.ui.showusernote

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
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

@Composable
fun ShowUserNote(
    appState: WeNoteAppState,
    viewModel: ShowUserNoteViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsState()

    ShowUserNoteScreen(
        state = state,
        onBack = appState::popBackStack,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowUserNoteScreen(
    state: ShowUserNoteViewState,
    onBack: () -> Unit = {},
) {
    WeNoteScaffold(
        modifier = Modifier.fillMaxSize(),
        state = state,
        topBar = {
            ShowUserNoteAppBar(
                userName = state.userName,
                onBack = onBack,
            )
        },
    ) { _, viewState ->
        val paddingBottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() + 10.dp

        if (state.listNote.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = stringResource(id = R.string.user_does_not_have_any_note, state.userName),
                    style = MaterialTheme.typography.titleLarge
                )
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
                    )
                }
            }
        }
    }
}

@Composable
fun ShowUserNoteAppBar(
    modifier: Modifier = Modifier,
    userName: String = "",
    onBack: () -> Unit = {},
) {
    SmallTopAppBar(
        modifier = modifier.statusBarsPadding(),
        title = {
            Text(text = stringResource(id = R.string.user_notes, userName))
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = null,
                )
            }
        },
    )
}
