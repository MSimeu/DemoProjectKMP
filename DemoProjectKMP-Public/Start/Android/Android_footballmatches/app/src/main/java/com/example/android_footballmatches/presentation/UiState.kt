package com.example.android_footballmatches.presentation

import com.example.android_footballmatches.data.model.League

internal sealed class UiState {
    data object Loading : UiState()
    data class Success(val leagueList: List<League>) : UiState()
    data class Error(val message: String) : UiState()
}
