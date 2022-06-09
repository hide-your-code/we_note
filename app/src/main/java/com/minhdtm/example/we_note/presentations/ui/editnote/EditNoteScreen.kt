package com.minhdtm.example.we_note.presentations.ui.editnote

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.minhdtm.example.we_note.R
import com.minhdtm.example.we_note.presentations.WeNoteAppState
import com.minhdtm.example.we_note.presentations.component.WeNoteScaffold
import com.minhdtm.example.we_note.presentations.utils.clearFocusOnKeyboardDismiss
import kotlinx.coroutines.flow.collectLatest

@Composable
fun EditNote(
    appState: WeNoteAppState,
    viewModel: EditNoteViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(true) {
        viewModel.event.collectLatest { event ->
            when (event) {
                is EditNoteEvent.BackToPreviousScreen -> {
                    appState.popBackStack()
                }
            }
        }
    }

    EditNoteScreen(
        state = state,
        onTitleChange = viewModel::setTextTitle,
        onDescriptionChange = viewModel::setTextDescription,
        onClickEdit = viewModel::updateNote,
        onBack = {
            appState.popBackStack()
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(
    state: EditNoteViewState,
    onTitleChange: (String) -> Unit = {},
    onDescriptionChange: (String) -> Unit = {},
    onClickEdit: () -> Unit = {},
    onBack: () -> Unit = {},
) {
    WeNoteScaffold(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        state = state,
        topBar = {
            EditNoteAppBar(onBack = onBack)
        },
        floatingActionButton = {
            EditNoteButton(
                buttonState = state.buttonEditNoteState,
                onClickEdit = onClickEdit,
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
fun EditNoteAppBar(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
) {
    SmallTopAppBar(
        modifier = modifier.statusBarsPadding(),
        title = {
            Text(text = stringResource(id = R.string.edit_note))
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
fun EditNoteButton(
    modifier: Modifier = Modifier,
    buttonState: ButtonEditNoteState,
    durationMillis: Int = 1000,
    onClickEdit: () -> Unit = {},
) {
    val transition = updateTransition(targetState = buttonState, label = null)

    val containerColor by transition.animateColor(
        transitionSpec = {
            tween(durationMillis)
        },
        label = "background_color",
    ) {
        when (it) {
            ButtonEditNoteState.DECLINE -> {
                MaterialTheme.colorScheme.error
            }
            ButtonEditNoteState.ACCEPT -> {
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
            ButtonEditNoteState.DECLINE -> {
                MaterialTheme.colorScheme.onError
            }
            ButtonEditNoteState.ACCEPT -> {
                MaterialTheme.colorScheme.onPrimary
            }
        }
    }

    FloatingActionButton(
        modifier = modifier,
        onClick = onClickEdit,
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
            if (targetState == ButtonEditNoteState.ACCEPT) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = null)
            } else {
                Icon(imageVector = Icons.Default.Close, contentDescription = null)
            }
        }
    }
}
