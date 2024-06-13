package com.example.kmp_footballmatches.domain

import com.example.kmp_footballmatches.data.api.ApiKtorService
import com.example.kmp_footballmatches.data.model.League
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
/**
 * Created by Menes SIMEU on 10/06/2024.
 * Volvo MoCE Android Team member
 */
class LeagueRepositoryImpl : LeagueRepository {
    private val apiService = ApiKtorService()


    override suspend fun getLeagues(): List<League> {
        return withContext(Dispatchers.IO) {
            apiService.getLeagues().data
        }
    }
}
