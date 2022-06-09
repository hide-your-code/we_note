package com.minhdtm.example.we_note.presentations

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.minhdtm.example.we_note.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun WeNoteApp(
    appState: WeNoteAppState = rememberWeNoteAppState(),
    appViewModel: WeNoteViewModel = viewModel(),
) {
    val state by appViewModel.state.collectAsState()

    val localFocusManager = LocalFocusManager.current

    ModalNavigationDrawer(
        drawerState = appState.drawer,
        modifier = Modifier.pointerInput(Unit) {
            detectTapGestures {
                localFocusManager.clearFocus()
            }
        },
        gesturesEnabled = appState.shouldEnableGesture,
        drawerContent = {
            WeNoteDrawerContent(
                selectedItem = appState.drawerItemSelected,
                userName = state.userName,
                onClickYourNote = {
                    if (!appState.currentDestinationIs(Screen.ShowYourNote.route)) {
                        appState.navigateToYourNote()
                    } else {
                        appState.closeDrawer()
                    }
                },
                onClickAnotherUser = {
                    if (!appState.currentDestinationIs(Screen.AnotherUsers.route)) {
                        appState.navigateToAnotherUsers()
                    } else {
                        appState.closeDrawer()
                    }
                },
                onClickLogout = {
                    appViewModel.logout()
                    appState.closeDrawer()
                    appState.navigateToRegister()
                },
            )
        },
    ) {
        Scaffold(
            containerColor = Color.Transparent,
        ) {
            AnimatedNavHost(
                navController = appState.controller,
                startDestination = NestedGraph.SPLASH.route,
            ) {
                splash(appState)

                register(appState)

                yourNote(appState)

                anotherNote(appState)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColumnScope.WeNoteDrawerContent(
    userName: String = "",
    selectedItem: DrawerTab,
    onClickYourNote: () -> Unit = {},
    onClickAnotherUser: () -> Unit = {},
    onClickLogout: () -> Unit = {},
) {
    Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars))

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 24.dp, top = 20.dp),
        text = stringResource(id = R.string.app_name),
        style = MaterialTheme.typography.displaySmall,
    )

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 24.dp, top = 10.dp, bottom = 24.dp),
        text = stringResource(id = R.string.hi, userName),
        style = MaterialTheme.typography.headlineSmall,
    )

    NavigationDrawerItem(
        icon = {
            Icon(
                painter = painterResource(id = DrawerTab.YOUR_NOTE.icon),
                modifier = Modifier.size(24.dp),
                contentDescription = null,
            )
        },
        label = {
            Text(text = stringResource(id = R.string.your_note))
        },
        selected = DrawerTab.YOUR_NOTE == selectedItem,
        onClick = onClickYourNote,
        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
    )

    NavigationDrawerItem(
        icon = {
            Icon(
                painter = painterResource(id = DrawerTab.ANOTHER_USERS_NOTE.icon),
                modifier = Modifier.size(24.dp),
                contentDescription = null,
            )
        },
        label = {
            Text(text = stringResource(id = R.string.another_users_note))
        },
        selected = DrawerTab.ANOTHER_USERS_NOTE == selectedItem,
        onClick = onClickAnotherUser,
        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
    )

    Spacer(modifier = Modifier.weight(1f))

    Button(
        onClick = onClickLogout,
        shape = RoundedCornerShape(size = 56.dp),
        modifier = Modifier
            .padding(start = 12.dp, end = 12.dp, bottom = 20.dp)
            .height(56.dp)
            .fillMaxWidth(),
    ) {
        Text(text = stringResource(id = R.string.logout))
    }

    Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
}
