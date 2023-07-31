package eu.giant.kaizen.data.repository

import eu.giant.kaizen.data.Resource
import eu.giant.kaizen.domain.model.Sport

interface SportsRepository {

    suspend fun loadSports(): Resource<List<Sport>>

}
