package com.example.kmp_footballmatches.domain

import com.example.kmp_footballmatches.data.model.League


/**
 * Created by Menes SIMEU on 10/06/2024.
 * Volvo MoCE Android Team member
 */
interface LeagueRepository {
    suspend fun getLeagues(): List<League>
}