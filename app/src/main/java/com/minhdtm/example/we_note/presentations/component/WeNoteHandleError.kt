package com.minhdtm.example.we_note.presentations.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.minhdtm.example.we_note.R
import com.minhdtm.example.we_note.presentations.base.ViewState

@Composable
fun <VS : ViewState> WeNoteHandleError(
    modifier: Modifier = Modifier,
    state: VS,
    onRetry: () -> Unit = {},
    onDismissErrorDialog: () -> Unit = {},
    content: @Composable (VS) -> Unit,
) {
    Box(modifier = modifier) {
        content(state)

        if (state.isLoading) {
            FullScreenLoading()
        }

        if (state.error != null) {
            WeNoteAlertDialog(
                throwable = state.error!!, // Not null
                onRetry = onRetry,
                onDismissRequest = onDismissErrorDialog,
            )
        }
    }
}

@Composable
fun FullScreenLoading(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.then(
            Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = {},
                ),
        ),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.background(Color.Transparent),
            strokeWidth = 5.dp,
        )
    }
}

@Composable
fun WeNoteAlertDialog(
    throwable: Throwable,
    onRetry: () -> Unit = {},
    onDismissRequest: () -> Unit = {},
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(text = stringResource(id = R.string.error))
        },
        text = {
            Text(text = throwable.message ?: stringResource(id = R.string.error_message_default))
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDismissRequest.invoke()
                    onRetry.invoke()
                },
            ) {
                Text(text = stringResource(id = R.string.retry))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest.invoke()
                },
            ) {
                Text(text = stringResource(id = R.string.cancel))
            }
        },
    )
}
