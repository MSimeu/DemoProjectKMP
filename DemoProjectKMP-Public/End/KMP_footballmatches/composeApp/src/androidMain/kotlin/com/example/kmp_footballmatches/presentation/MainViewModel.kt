package com.example.kmp_footballmatches.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmp_footballmatches.domain.LeagueRepositoryImpl
import com.example.kmp_footballmatches.domain.usecase.GetLeaguesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class MainViewModel : ViewModel() {

    private val repository = LeagueRepositoryImpl()
    val getLeaguesUseCase = GetLeaguesUseCase(repository)

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        fetchLeagues()
    }

    private fun fetchLeagues() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val leagues = withContext(Dispatchers.IO) {
                    getLeaguesUseCase()
                }
                _uiState.value = UiState.Success(leagues)
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Failed to fetch leagues: ${e.message}")
            }
        }
    }
}