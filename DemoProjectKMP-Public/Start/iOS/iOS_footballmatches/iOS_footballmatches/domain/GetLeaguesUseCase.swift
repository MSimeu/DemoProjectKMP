//
//  GetLeaguesUseCase.swift
//  iOS_footballmatches
//
//  Created by Menes  SIMEU on 07/06/2024.
//
import Foundation

class GetLeaguesUseCase {
    private let repository: LeagueRepository

    init(repository: LeagueRepository) {
        self.repository = repository
    }

    func execute(completion: @escaping (Result<[League], Error>) -> Void) {
        repository.getLeagues(completion: completion)
    }
}
