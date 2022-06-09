package com.minhdtm.example.we_note.presentations.ui.anotherusers

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.minhdtm.example.we_note.presentations.WeNoteAppState
import com.minhdtm.example.we_note.presentations.component.WeNoteScaffold
import com.minhdtm.example.we_note.presentations.model.UserViewData

@Composable
fun AnotherUsers(
    appState: WeNoteAppState,
    viewModel: AnotherUsersViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsState()

    AnotherUsersScreen(
        state = state,
        onDrawer = appState::openDrawer,
        onClickUser = { user ->
            appState.navigateToShowUserNote(user.name)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnotherUsersScreen(
    state: AnotherUsersViewState,
    onDrawer: () -> Unit = {},
    onClickUser: (UserViewData) -> Unit = {},
) {
    WeNoteScaffold(
        modifier = Modifier.fillMaxSize(),
        state = state,
        topBar = {
            AnotherUsersAppBar(onDrawer = onDrawer)
        },
    ) { _, viewState ->
        val paddingBottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() + 10.dp

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(bottom = paddingBottom, start = 10.dp, end = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            items(items = viewState.listUser) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onClickUser.invoke(item)
                    },
                ) {
                    Surface(
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .size(70.dp)
                            .align(Alignment.CenterHorizontally),
                        shape = RoundedCornerShape(50),
                        color = MaterialTheme.colorScheme.background,
                    ) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(text = item.name.first().toString(), style = MaterialTheme.typography.headlineLarge)
                        }
                    }

                    Text(
                        text = item.name,
                        modifier = Modifier
                            .padding(10.dp)
                            .align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.headlineSmall,
                    )
                }
            }
        }
    }
}

@Composable
fun AnotherUsersAppBar(
    modifier: Modifier = Modifier,
    onDrawer: () -> Unit = {},
) {
    SmallTopAppBar(
        modifier = modifier.statusBarsPadding(),
        title = {
            Text(text = "Another users screen")
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
