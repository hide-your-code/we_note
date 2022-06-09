package com.minhdtm.example.we_note.presentations.ui.register

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
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
fun Register(
    appState: WeNoteAppState,
    viewModel: RegisterViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsState()

    var textUserName by rememberSaveable {
        mutableStateOf("")
    }

    val localFocusManager = LocalFocusManager.current

    LaunchedEffect(true) {
        viewModel.event.collectLatest { event ->
            when (event) {
                is RegisterEvent.NavigateToHome -> {
                    appState.navigateToYourNote()
                }
            }
        }
    }

    RegisterScreen(
        state = state,
        textUserName = textUserName,
        onTextChange = {
            textUserName = it
        },
        onClickLogin = {
            localFocusManager.clearFocus()

            viewModel.login(userName = textUserName)
        },
        onRetry = {
            viewModel.retry()
        },
        onDismissErrorDialog = {
            viewModel.hideError()
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    state: RegisterViewState,
    textUserName: String = "",
    onTextChange: (String) -> Unit = {},
    onClickLogin: () -> Unit = {},
    onRetry: () -> Unit = {},
    onDismissErrorDialog: () -> Unit = {},
) {
    WeNoteScaffold(
        modifier = Modifier.fillMaxSize(),
        state = state,
        onRetry = onRetry,
        onDismissErrorDialog = onDismissErrorDialog,
    ) { _, viewState ->
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(id = R.string.welcome),
                style = MaterialTheme.typography.titleLarge,
            )

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(start = 40.dp, end = 40.dp, top = 20.dp)
                    .clearFocusOnKeyboardDismiss(),
                value = textUserName,
                onValueChange = onTextChange,
                singleLine = true,
                shape = RoundedCornerShape(30.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { onClickLogin.invoke() }),
                placeholder = {
                    Text(text = stringResource(id = R.string.fill_your_name_here))
                },
            )

            ButtonLogin(
                modifier = Modifier.padding(top = 20.dp),
                buttonState = state.buttonLogin,
                onClickLogin = onClickLogin,
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ButtonLogin(
    modifier: Modifier = Modifier,
    buttonState: ButtonLoginState,
    durationMillis: Int = 500,
    onClickLogin: () -> Unit = {},
) {
    val transition = updateTransition(targetState = buttonState, label = null)

    val containerColor by transition.animateColor(
        transitionSpec = {
            tween(durationMillis)
        },
        label = "background_color",
    ) {
        when (it) {
            ButtonLoginState.ERROR -> {
                MaterialTheme.colorScheme.error
            }
            ButtonLoginState.LOADING -> {
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
            }
            else -> {
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
            ButtonLoginState.ERROR -> {
                MaterialTheme.colorScheme.onError
            }
            ButtonLoginState.LOADING -> {
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
            }
            else -> {
                MaterialTheme.colorScheme.onPrimary
            }
        }
    }

    Button(
        modifier = modifier,
        onClick = onClickLogin,
        enabled = buttonState != ButtonLoginState.LOADING,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = containerColor,
            disabledContentColor = contentColor
        )
    ) {
        transition.AnimatedContent(
            transitionSpec = {
                scaleIn(tween(durationMillis)) + fadeIn(tween(durationMillis)) with scaleOut(tween(durationMillis)) + fadeOut(
                    tween(durationMillis)
                )
            },
        ) { targetState ->
            Row(
                modifier = Modifier.defaultMinSize(minWidth = 100.dp, minHeight = 25.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                when (targetState) {
                    ButtonLoginState.NORMAL -> {
                        Text(text = "Login")
                    }
                    ButtonLoginState.LOADING -> {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                        }
                    }
                    ButtonLoginState.ERROR -> {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = null)

                            Text(text = "Error", modifier = Modifier.padding(start = 10.dp))
                        }
                    }
                    ButtonLoginState.DONE -> {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(imageVector = Icons.Default.Done, contentDescription = null)

                            Text(text = "Done", modifier = Modifier.padding(start = 10.dp))
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewButtonLogin() {
    WeNoteTheme {
        ButtonLogin(buttonState = ButtonLoginState.LOADING)
    }
}
