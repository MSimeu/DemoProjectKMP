package com.example.kmp_footballmatches.data.model

import kotlinx.serialization.Serializable

/**
 * Created by Menes SIMEU on 10/06/2024.
 * Volvo MoCE Android Team member
 */
@Serializable
data class LeagueResponse(
    val data: List<League>
)

@Serializable
data class League(
    val id: String,
    val name: String,
    val abbr: String,
    val logos: Logos
)

@Serializable
data class Logos(
    val light: String,
    val dark: String
)