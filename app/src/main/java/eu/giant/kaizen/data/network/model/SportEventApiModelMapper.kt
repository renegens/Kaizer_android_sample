package eu.giant.kaizen.data.network.model

import eu.giant.kaizen.domain.model.SportEvent
import javax.inject.Inject

class SportEventApiModelMapper @Inject constructor() {

    fun mapToDomainModel(model: SportEventApiModel): SportEvent {
        return SportEvent(
            id = model.i,
            sportId = model.si,
            name = model.d,
            startTime = model.tt
        )
    }

}
