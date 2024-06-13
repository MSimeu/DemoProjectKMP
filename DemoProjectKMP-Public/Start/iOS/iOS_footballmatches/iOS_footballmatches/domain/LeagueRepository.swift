//
//  LeagueRepository.swift
//  iOS_footballmatches
//
//  Created by Menes  SIMEU on 06/06/2024.
//

import Foundation

protocol LeagueRepository {
    func getLeagues(completion: @escaping (Result<[League], Error>) -> Void)
}
