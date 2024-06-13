//
//  NetworkService.swift
//  iOS_footballmatches
//
//  Created by Menes  SIMEU on 07/06/2024.
//

import Foundation
import Alamofire

class NetworkService {
    static let shared = NetworkService()
    
    private init() {}
    
    func getLeagues(completion: @escaping (Result<[League], Error>) -> Void) {
        AF.request("https://api-football-standings.azharimm.dev/leagues").responseDecodable(of: LeagueResponse.self) { response in
            switch response.result {
            case .success(let leagues):
                completion(.success(leagues.data))
            case .failure(let error):
                completion(.failure(error))
            }
        }
    }
}
