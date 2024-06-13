package com.example.android_footballmatches.domain.usecase

import com.example.android_footballmatches.data.model.League
import com.example.android_footballmatches.domain.LeagueRepository


/**
 * Created by Menes SIMEU on 07/06/2024.
 * Volvo MoCE Android Team member
 */
class GetLeaguesUseCase(private val repository: LeagueRepository) {

    suspend operator fun invoke(): List<League> {
        return repository.getLeagues()
    }
}
