package eu.giant.kaizen.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import eu.giant.kaizen.platform.errorToast
import eu.giant.kaizen.ui.theme.Grey
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen() {
    val viewModel = hiltViewModel<HomeViewModel>()
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest {
            when (it) {
                is HomeContract.Effect.ShowError -> context.errorToast("Something went wrong")
            }
        }
    }

    val pullRefreshState = rememberPullRefreshState(refreshing = state.isLoading,
        { viewModel.setEvent(HomeContract.Event.LoadSports) })
    Content(
        hasError = state.hasError,
        isLoading = state.isLoading,
        pullRefreshState,
        toggleFavoriteEvent = { viewModel.setEvent(HomeContract.Event.ToggleFavoriteSportEvent(id = it)) },
        toggleSportFilter = { viewModel.setEvent(HomeContract.Event.ToggleFavoriteSportEventsFilter(id = it)) },
        toggleExpandSport = { viewModel.setEvent(HomeContract.Event.ToggleSportDropDown(id = it)) },
        reload = { viewModel.setEvent(HomeContract.Event.LoadSports) },
        viewItemsState = viewModel.viewItemsState,
    )
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
private fun Content(
    hasError: Boolean,
    isLoading: Boolean,
    pullRefreshState: PullRefreshState,
    viewItemsState: SnapshotStateList<ViewItem>,
    toggleFavoriteEvent: (String) -> Unit,
    toggleSportFilter: (String) -> Unit,
    toggleExpandSport: (String) -> Unit,
    reload: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
            .background(color = MaterialTheme.colors.Grey)
    ) {

        if (hasError && isLoading.not()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = reload) { Text("Please try again") }
            }
        }

        LazyVerticalGrid(columns = GridCells.Fixed(4)) {
            items(viewItemsState, key = { item -> item.hashCode() }, span = { item ->
                val span = if (item.event == null) 4 else 1
                GridItemSpan(span)
            }) { item ->
                if (item.isHeader) {
                    SportHeader(
                        sport = item.sport,
                        isFiltered = item.isFavoriteSport,
                        onFilterClicked = toggleSportFilter,
                        onToggleEventVisibilityClicked = toggleExpandSport,
                        isExpanded = item.isExpanded
                    )
                } else {
                    EventItem(
                        event = item.event!!,
                        onFavoriteClicked = toggleFavoriteEvent,
                    )
                }
            }
        }
        PullRefreshIndicator(
            refreshing = isLoading,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}
