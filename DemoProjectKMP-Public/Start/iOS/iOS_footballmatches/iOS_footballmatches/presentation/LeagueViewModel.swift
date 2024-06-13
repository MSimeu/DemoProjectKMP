//
//  LeagueViewModel.swift
//  iOS_footballmatches
//
//  Created by Menes  SIMEU on 06/06/2024.
//

import Foundation

class LeagueViewModel: ObservableObject {
    @Published var uiState: UIState<[League]>
    
    private let repository: LeagueRepository
    
    init(uiState: UIState<[League]> = .loading, repository: LeagueRepository = LeagueRepositoryImpl()) {
        self.uiState = uiState
        self.repository = repository
        fetchLeagues()
    }
    
    private func fetchLeagues() {
        GetLeaguesUseCase(repository: repository).execute { [weak self] result in
            DispatchQueue.main.async {
                switch result {
                case .success(let leagues):
                    self?.uiState = .success(leagues)
                case .failure(let error):
                    self?.uiState = .failure(error.localizedDescription)
                }
            }
        }
    }
}
