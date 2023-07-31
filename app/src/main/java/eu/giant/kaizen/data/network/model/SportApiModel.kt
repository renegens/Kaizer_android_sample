package eu.giant.kaizen.data.network.model

import com.google.gson.annotations.SerializedName

data class SportApiModel(
    @SerializedName("i")
    val i: String,
    @SerializedName("d")
    val d: String,
    @SerializedName("e")
    val e: List<SportEventApiModel>,
)
