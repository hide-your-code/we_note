package com.minhdtm.example.we_note.presentations.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.minhdtm.example.we_note.presentations.base.ViewState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <VS : ViewState> WeNoteScaffold(
    modifier: Modifier = Modifier,
    state: VS,
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = contentColorFor(containerColor),
    onRetry: () -> Unit = {},
    onDismissErrorDialog: () -> Unit = {},
    content: @Composable (PaddingValues, VS) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = topBar,
        bottomBar = bottomBar,
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.navigationBarsPadding(),
            )
        },
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        containerColor = containerColor,
        contentColor = contentColor,
    ) { paddingValues ->
        WeNoteHandleError(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            state = state,
            onRetry = onRetry,
            onDismissErrorDialog = onDismissErrorDialog,
        ) { viewState ->
            content.invoke(paddingValues, viewState)
        }
    }
}