package com.example.kmp_footballmatches.domain.usecase

import com.example.kmp_footballmatches.data.model.League
import com.example.kmp_footballmatches.domain.LeagueRepository


/**
 * Created by Menes SIMEU on 10/06/2024.
 * Volvo MoCE Android Team member
 */
class GetLeaguesUseCase(private val repository: LeagueRepository) {

    suspend operator fun invoke(): List<League> {
        return repository.getLeagues()
    }
}
