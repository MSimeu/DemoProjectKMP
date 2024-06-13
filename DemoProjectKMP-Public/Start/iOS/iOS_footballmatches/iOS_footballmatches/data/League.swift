//
//  League.swift
//  iOS_footballmatches
//
//  Created by Menes  SIMEU on 07/06/2024.
//

import Foundation

struct LeagueResponse: Codable {
    let data: [League]
}

struct League: Codable, Identifiable {
    let id: String
    let name: String
    let abbr: String
    let logos: Logos
}

struct Logos: Codable {
    let light: String
    let dark: String
}
