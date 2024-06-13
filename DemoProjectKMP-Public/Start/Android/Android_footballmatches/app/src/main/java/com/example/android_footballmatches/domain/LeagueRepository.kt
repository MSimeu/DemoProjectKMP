package com.example.android_footballmatches.domain

import com.example.android_footballmatches.data.model.League

interface LeagueRepository {
    suspend fun getLeagues(): List<League>
}