package eu.giant.kaizen.ui.home

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import eu.giant.kaizen.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.giant.kaizen.domain.model.Sport
import eu.giant.kaizen.domain.model.SportEvent
import eu.giant.kaizen.domain.usecase.GetSports
import eu.giant.kaizen.domain.usecase.ToggleFavorite
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject constructor(private val getSports: GetSports, private val toggleFavorite: ToggleFavorite) :
    BaseViewModel<HomeContract.Event, HomeContract.State, HomeContract.Effect>() {

    private var userJob: Job? = null

    private val viewItemsStateAll = mutableListOf<ViewItem>()

    val viewItemsState = mutableStateListOf<ViewItem>()

    init {
        handleEvent(HomeContract.Event.LoadSports)
    }

    override fun createInitialState() = HomeContract.State()

    override fun handleEvent(event: HomeContract.Event) {
        when (event) {
            HomeContract.Event.LoadSports -> loadSports()
            is HomeContract.Event.ToggleFavoriteSportEvent -> {
                val index = viewItemsStateAll.indexOfFirst { it.event?.id == event.id }
                val item = viewItemsStateAll[index]

                item.event!!.isFavorite = item.event!!.isFavorite.not()

                val newItem = ViewItem(
                    isExpanded = item.isExpanded,
                    isFavoriteSport = item.isFavoriteSport,
                    sport = item.sport,
                    event = item.event
                )

                viewItemsStateAll[index] = newItem

                updateViewItems()

                viewModelScope.launch {
                    toggleFavorite.invoke(newItem.event!!)
                }
            }

            is HomeContract.Event.ToggleFavoriteSportEventsFilter -> {
                val isFilteredForFavorites =
                    viewItemsStateAll.first { it.sport.id == event.id }.isFavoriteSport.not()

                viewItemsStateAll.forEach {
                    if (it.sport.id == event.id) {
                        it.isFavoriteSport = isFilteredForFavorites
                    }

                    if (it.event?.sportId == event.id) {
                        it.isFavoriteSport = isFilteredForFavorites
                    }
                }

                updateViewItems()
            }

            is HomeContract.Event.ToggleSportDropDown -> {
                val isExpanded =
                    viewItemsStateAll.first { it.sport.id == event.id }.isExpanded.not()

                viewItemsStateAll.forEach {
                    if (it.sport.id == event.id) {
                        it.isExpanded = isExpanded
                    }

                    if (it.event?.sportId == event.id) {
                        it.isExpanded = isExpanded
                    }
                }
                updateViewItems()
            }
        }
    }

    private fun loadSports() {
        if (userJob?.isActive == true) {
            return
        }
        userJob = viewModelScope.launch {
            setState { copy(isLoading = true) }
            when (val result = getSports()) {
                is GetSports.State.Success -> {
                    val list = mutableListOf<ViewItem>()

                    result.sport.forEach { sport ->
                        list.add(
                            ViewItem(
                                isExpanded = true,
                                isFavoriteSport = false,
                                sport = sport,
                                event = null
                            )
                        )
                        sport.events.forEach { sportEvent ->
                            list.add(
                                ViewItem(
                                    isExpanded = true,
                                    isFavoriteSport = false,
                                    sport = sport,
                                    event = sportEvent
                                )
                            )
                        }
                    }

                    viewItemsStateAll.clear()
                    viewItemsStateAll.addAll(list)

                    viewItemsState.clear()
                    viewItemsState.addAll(viewItemsStateAll)

                    setState {
                        copy(
                            hasError = false,
                            isLoading = false,
                        )
                    }

                }

                GetSports.State.Failure -> {
                    setState { copy(isLoading = false, hasError = true) }
                    setEffect { HomeContract.Effect.ShowError }
                }
            }
        }
    }

    private fun updateViewItems() {
        viewItemsState.clear()
        viewItemsState.addAll(viewItemsStateAll)

        val newList = viewItemsState.filter {
            when {
                it.isHeader -> true
                it.isExpanded.not() -> false
                it.isFavoriteSport -> it.event!!.isFavorite
                else -> true
            }
        }

        viewItemsState.clear()
        viewItemsState.addAll(newList)
    }
}

class ViewItem(
    var isFavoriteSport: Boolean,
    var isExpanded: Boolean,
    val sport: Sport,
    var event: SportEvent? = null,
) {
    val isHeader: Boolean
        get() {
            return event == null
        }
}
