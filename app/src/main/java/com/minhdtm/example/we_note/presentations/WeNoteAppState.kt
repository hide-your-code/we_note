package com.minhdtm.example.we_note.presentations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun rememberWeNoteAppState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    controller: NavHostController = rememberAnimatedNavController(),
    drawer: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    snackbarHost: SnackbarHostState = remember { SnackbarHostState() },
): WeNoteAppState = remember(coroutineScope, controller, drawer, snackbarHost) {
    WeNoteAppState(coroutineScope, controller, drawer, snackbarHost)
}

@OptIn(ExperimentalMaterial3Api::class)
class WeNoteAppState constructor(
    private val coroutineScope: CoroutineScope,
    val controller: NavHostController,
    val drawer: DrawerState,
    val snackbarHost: SnackbarHostState,
) {
    private val drawerTabsRoute = listOf(Screen.ShowYourNote.route, Screen.AnotherUsers.route)

    val shouldEnableGesture: Boolean
        @Composable get() = controller.currentBackStackEntryAsState().value?.destination?.route in drawerTabsRoute

    val drawerItemSelected: DrawerTab
        @Composable get() {
            val route = controller.currentBackStackEntryAsState().value?.destination?.parent?.route
            return DrawerTab.values().firstOrNull { it.route == route } ?: DrawerTab.YOUR_NOTE
        }

    fun currentDestinationIs(route: String): Boolean = controller.currentBackStackEntry?.destination?.route == route

    fun popBackStack(popToRoute: String? = null, params: Map<String, Any>? = null) {
        if (popToRoute == null) {
            params?.forEach { data ->
                controller.previousBackStackEntry?.savedStateHandle?.set(data.key, data.value)
            }
            controller.popBackStack()
        } else {
            params?.forEach { data ->
                controller.getBackStackEntry(popToRoute).savedStateHandle[data.key] = data.value
            }
            controller.popBackStack(route = popToRoute, inclusive = false)
        }
    }

    fun openDrawer() {
        coroutineScope.launch {
            drawer.open()
        }
    }

    fun closeDrawer() {
        coroutineScope.launch {
            drawer.close()
        }
    }

    fun navigateToRegister() {
        controller.navigate(NestedGraph.REGISTER.route) {
            popUpTo(0)
        }
    }

    fun navigateToYourNote() {
        closeDrawer()

        controller.navigate(NestedGraph.YOUR_NOTE.route) {
            popUpTo(0)
        }
    }

    fun navigateToAnotherUsers() {
        closeDrawer()

        controller.navigate(NestedGraph.ANOTHER_NOTE.route)
    }

    fun navigateToAddNote() {
        controller.navigate(Screen.AddNote.route)
    }

    fun navigateToEditNote(idNote: String) {
        controller.navigate("${Screen.EditNote.route}?id=${idNote}")
    }

    fun navigateToShowUserNote(userName: String) {
        controller.navigate("${Screen.ShowUserNote.route}?user_name=${userName}")
    }
}