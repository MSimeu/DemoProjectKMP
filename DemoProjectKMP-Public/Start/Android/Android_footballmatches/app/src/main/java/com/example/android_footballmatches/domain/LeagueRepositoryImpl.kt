package com.example.android_footballmatches.domain

import com.example.android_footballmatches.data.api.RetrofitInstance
import com.example.android_footballmatches.data.model.League
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
/**
 * Created by Menes SIMEU on 06/06/2024.
 * Volvo MoCE Android Team member
 */
class LeagueRepositoryImpl : LeagueRepository {
   private val apiService = RetrofitInstance.api

    override suspend fun getLeagues(): List<League> {
        return withContext(Dispatchers.IO) {
            apiService.getLeagues().data
        }
    }
/*
    private val apiService = ApiKtorService()

    suspend fun getLeagues(): List<League> {
        return withContext(Dispatchers.IO) {
            apiService.getLeagues().data
        }
    }*/
}
