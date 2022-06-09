package com.minhdtm.example.we_note.presentations.ui.addnote

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.minhdtm.example.we_note.R
import com.minhdtm.example.we_note.presentations.WeNoteAppState
import com.minhdtm.example.we_note.presentations.component.WeNoteScaffold
import com.minhdtm.example.we_note.presentations.theme.WeNoteTheme
import com.minhdtm.example.we_note.presentations.utils.clearFocusOnKeyboardDismiss
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddNote(
    appState: WeNoteAppState,
    viewModel: AddNoteViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(true) {
        viewModel.event.collectLatest { event ->
            when (event) {
                is AddNoteEvent.BackToPreviousScreen -> {
                    appState.popBackStack()
                }
            }
        }
    }

    AddNoteScreen(
        state = state,
        onTitleChange = viewModel::setTextTitle,
        onDescriptionChange = viewModel::setTextDescription,
        onBack = appState::popBackStack,
        onAddNote = viewModel::addNote,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(
    state: AddNoteViewState,
    onTitleChange: (String) -> Unit = {},
    onDescriptionChange: (String) -> Unit = {},
    onAddNote: () -> Unit = {},
    onBack: () -> Unit = {},
) {
    WeNoteScaffold(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        state = state,
        topBar = {
            AddNoteAppBar(onBack = onBack)
        },
        floatingActionButton = {
            AddNoteButton(
                buttonState = state.buttonAddNoteState,
                onClickAdd = onAddNote,
            )
        },
    ) { _, viewState ->
        Column(modifier = Modifier.fillMaxSize()) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .clearFocusOnKeyboardDismiss(),
                value = viewState.title,
                onValueChange = onTitleChange,
                singleLine = true,
                textStyle = MaterialTheme.typography.titleLarge,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.title),
                        style = MaterialTheme.typography.titleLarge,
                    )
                },
            )

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .clearFocusOnKeyboardDismiss(),
                value = viewState.description,
                onValueChange = onDescriptionChange,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
                textStyle = MaterialTheme.typography.bodyMedium,
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.description),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                },
            )
        }
    }
}

@Composable
fun AddNoteAppBar(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
) {
    SmallTopAppBar(
        modifier = modifier.statusBarsPadding(),
        title = {
            Text(text = stringResource(id = R.string.add_note))
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

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AddNoteButton(
    modifier: Modifier = Modifier,
    buttonState: ButtonAddNoteState,
    durationMillis: Int = 1000,
    onClickAdd: () -> Unit = {},
) {
    val transition = updateTransition(targetState = buttonState, label = null)

    val containerColor by transition.animateColor(
        transitionSpec = {
            tween(durationMillis)
        },
        label = "background_color",
    ) {
        when (it) {
            ButtonAddNoteState.DECLINE -> {
                MaterialTheme.colorScheme.error
            }
            ButtonAddNoteState.ACCEPT -> {
                MaterialTheme.colorScheme.primary
            }
        }
    }

    val contentColor by transition.animateColor(
        transitionSpec = {
            tween(durationMillis)
        },
        label = "content_color",
    ) {
        when (it) {
            ButtonAddNoteState.DECLINE -> {
                MaterialTheme.colorScheme.onError
            }
            ButtonAddNoteState.ACCEPT -> {
                MaterialTheme.colorScheme.onPrimary
            }
        }
    }

    FloatingActionButton(
        modifier = modifier,
        onClick = onClickAdd,
        containerColor = containerColor,
        contentColor = contentColor,
    ) {
        transition.AnimatedContent(
            transitionSpec = {
                scaleIn(tween(durationMillis)) + fadeIn(tween(durationMillis)) with scaleOut(tween(durationMillis)) + fadeOut(
                    tween(durationMillis)
                )
            },
        ) { targetState ->
            if (targetState == ButtonAddNoteState.ACCEPT) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            } else {
                Icon(imageVector = Icons.Default.Close, contentDescription = null)
            }
        }
    }
}

@Preview(name = "Light", device = Devices.PIXEL, showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Preview(name = "Dark", device = Devices.PIXEL, showBackground = true)
@Composable
fun AddNoteScreenPreview() {
    WeNoteTheme {
        AddNoteScreen(state = AddNoteViewState())
    }
}
