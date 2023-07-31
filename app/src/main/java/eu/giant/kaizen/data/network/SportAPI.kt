package eu.giant.kaizen.data.network

import eu.giant.kaizen.data.network.model.SportsApiModel
import retrofit2.http.*

interface SportAPI {

    @GET("api/sports")
    suspend fun getSports(): SportsApiModel

}
