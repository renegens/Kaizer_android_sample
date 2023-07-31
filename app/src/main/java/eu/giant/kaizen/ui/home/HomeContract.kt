package eu.giant.kaizen.ui.home

import eu.giant.kaizen.ui.base.UiEffect
import eu.giant.kaizen.ui.base.UiEvent
import eu.giant.kaizen.ui.base.UiState

class HomeContract {

    data class State(
        val isLoading: Boolean = false,
        val hasError: Boolean = false,
    ) : UiState

    sealed class Effect : UiEffect {
        object ShowError : Effect()
    }

    sealed class Event : UiEvent {
        object LoadSports : Event()
        class ToggleFavoriteSportEvent(val id: String) : Event()
        class ToggleFavoriteSportEventsFilter(val id: String) : Event()
        class ToggleSportDropDown(val id: String) : Event()
    }
}
