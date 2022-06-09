package com.minhdtm.example.we_note.presentations

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.composable
import com.minhdtm.example.we_note.R
import com.minhdtm.example.we_note.presentations.ui.addnote.AddNote
import com.minhdtm.example.we_note.presentations.ui.anotherusers.AnotherUsers
import com.minhdtm.example.we_note.presentations.ui.editnote.EditNote
import com.minhdtm.example.we_note.presentations.ui.register.Register
import com.minhdtm.example.we_note.presentations.ui.showusernote.ShowUserNote
import com.minhdtm.example.we_note.presentations.ui.splash.Splash
import com.minhdtm.example.we_note.presentations.ui.yournote.YourNote
import com.minhdtm.example.we_note.presentations.utils.Constants

sealed class Screen(val route: String) {
    object Splash : Screen("splash")

    object Register : Screen("register")

    object ShowYourNote : Screen("show_your_note")

    object AddNote : Screen("add_note")

    object EditNote : Screen("edit_note")

    object AnotherUsers : Screen("another_users")

    object ShowUserNote : Screen("show_user_note")
}

enum class NestedGraph(val route: String) {
    SPLASH(route = "splash_nav"), REGISTER(route = "register_nav"), YOUR_NOTE(route = "your_note_nav"), ANOTHER_NOTE(
        route = "another_nav"
    ),
}

enum class DrawerTab(
    val route: String,
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
) {
    YOUR_NOTE(
        route = NestedGraph.YOUR_NOTE.route,
        title = R.string.your_note,
        icon = R.drawable.ic_person,
    ),
    ANOTHER_USERS_NOTE(
        route = NestedGraph.ANOTHER_NOTE.route,
        title = R.string.another_users_note,
        icon = R.drawable.ic_group,
    ),
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.splash(appState: WeNoteAppState) {
    navigation(
        route = NestedGraph.SPLASH.route,
        startDestination = Screen.Splash.route,
    ) {
        composable(
            route = Screen.Splash.route,
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
            },
            popEnterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))
            },
            popExitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))
            },
        ) {
            Splash(
                appState = appState,
                viewModel = hiltViewModel(),
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.register(appState: WeNoteAppState) {
    navigation(
        route = NestedGraph.REGISTER.route,
        startDestination = Screen.Register.route,
    ) {
        composable(
            route = Screen.Register.route,
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
            },
            popEnterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))
            },
            popExitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))
            },
        ) {
            Register(
                appState = appState,
                viewModel = hiltViewModel(),
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.yourNote(appState: WeNoteAppState) {
    navigation(
        route = NestedGraph.YOUR_NOTE.route,
        startDestination = Screen.ShowYourNote.route,
    ) {
        composable(
            route = Screen.ShowYourNote.route,
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
            },
            popEnterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))
            },
            popExitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))
            },
        ) {
            YourNote(
                appState = appState,
                viewModel = hiltViewModel(),
            )
        }

        composable(
            route = Screen.AddNote.route,
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
            },
            popEnterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))
            },
            popExitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))
            },
        ) {
            AddNote(
                appState = appState,
                viewModel = hiltViewModel(),
            )
        }

        composable(
            route = "${Screen.EditNote.route}?id={${Constants.Keys.NOTE_ID}}",
            arguments = listOf(navArgument(Constants.Keys.NOTE_ID) { type = NavType.StringType }),
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
            },
            popEnterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))
            },
            popExitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))
            },
        ) {
            EditNote(
                appState = appState,
                viewModel = hiltViewModel(),
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.anotherNote(appState: WeNoteAppState) {
    navigation(
        route = NestedGraph.ANOTHER_NOTE.route,
        startDestination = Screen.AnotherUsers.route,
    ) {
        composable(
            route = Screen.AnotherUsers.route,
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
            },
            popEnterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))
            },
            popExitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))
            },
        ) {
            AnotherUsers(
                appState = appState,
                viewModel = hiltViewModel(),
            )
        }

        composable(
            route = "${Screen.ShowUserNote.route}?user_name={${Constants.Keys.USER_NAME}}",
            arguments = listOf(
                navArgument(Constants.Keys.USER_NAME) { type = NavType.StringType },
            ),
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
            },
            popEnterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))
            },
            popExitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))
            },
        ) {
            ShowUserNote(
                appState = appState,
                viewModel = hiltViewModel(),
            )
        }
    }
}