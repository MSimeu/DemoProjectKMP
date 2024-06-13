//
//  LeagueViewModel.swift
//  iOS_footballmatches
//
//  Created by Menes  SIMEU on 06/06/2024.
//

import Foundation
import Shared

class LeagueViewModel: ObservableObject {
    @Published var uiState: UIState<[League]>
    
    private let repository: LeagueRepository
    
    init(uiState: UIState<[League]> = .loading, repository: LeagueRepository = LeagueRepositoryImpl()) {
        self.uiState = uiState
        self.repository = repository
        fetchLeagues()
    }
    
    private func fetchLeagues() {
        GetLeaguesUseCase(repository: repository).invoke { [weak self] result, error in
            DispatchQueue.main.async {
                if let league = result {
                    self?.uiState = .success(league)
                } else{
                    self?.uiState = .failure(error?.localizedDescription ??  "Error")
                }
            }
        }
    }
}
