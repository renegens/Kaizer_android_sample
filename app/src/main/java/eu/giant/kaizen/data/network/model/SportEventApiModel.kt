package eu.giant.kaizen.data.network.model

import com.google.gson.annotations.SerializedName

data class SportEventApiModel(
    @SerializedName("i") val i: String,
    @SerializedName("si") val si: String,
    @SerializedName("d") val d: String,
    @SerializedName("tt") val tt: Int,
)
