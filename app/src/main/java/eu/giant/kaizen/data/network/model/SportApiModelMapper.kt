package eu.giant.kaizen.data.network.model

import eu.giant.kaizen.domain.model.Sport
import javax.inject.Inject

class SportApiModelMapper @Inject constructor(private val mapper: SportEventApiModelMapper) {

    fun mapToDomainModel(model: SportApiModel): Sport {
        return Sport(id = model.i,
            name = model.d,
            events = model.e.map { mapper.mapToDomainModel(it) })
    }

}
