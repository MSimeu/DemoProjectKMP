package com.example.android_footballmatches.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Menes SIMEU on 06/06/2024.
 * Volvo MoCE Android Team member
 */
data class LeagueResponse(
    @SerializedName("data") val data: List<League>
)

data class League(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("abbr") val abbr: String,
    @SerializedName("logos") val logos: Logos
)

data class Logos(
    @SerializedName("light") val light: String,
    @SerializedName("dark") val dark: String
)
