//
//  ContentView.swift
//  iOS_footballmatches
//
//  Created by Menes  SIMEU on 06/06/2024.
//

import SwiftUI

struct ContentView: View {
    @StateObject private var viewModel = LeagueViewModel()
    
    var body: some View {
        VStack {
            switch viewModel.uiState {
            case .loading:
                ProgressView()
            case .success(let leagues):
                LeagueListView(leagues: leagues)
            case .failure(let error):
                Text(error)
            }
        }
    }
}

struct LeagueListView: View {
    let leagues: [League]
    
    var body: some View {
        List(leagues) { league in
            LeagueRow(league: league)
        }
    }
}


struct LeagueRow: View {
    let league: League
    
    var body: some View {
        HStack {
            AsyncImage(url: URL(string: league.logos.light)) { phase in
                switch phase {
                case .empty:
                    ProgressView()
                case .success(let image):
                    image
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                        .frame(width: 50, height: 50)
                case .failure:
                    Image(systemName: "exclamationmark.triangle")
                        .foregroundColor(.red)
                @unknown default:
                    EmptyView()
                }
            }
            .frame(width: 50, height: 50)
            .cornerRadius(25)
            
            VStack(alignment: .leading) {
                Text(league.name)
                Text(league.abbr)
                    .font(.caption)
            }
            
            Spacer()
        }
        .padding()
        .cornerRadius(10)
        .shadow(radius: 5)
    }
}


struct LoadingPreview: PreviewProvider {
    static var previews: some View {
        ContentView()
            .environmentObject(LeagueViewModel(uiState: .loading))
    }
}

struct SuccessPreview: PreviewProvider {
    static var previews: some View {
        ContentView()
            .environmentObject(LeagueViewModel(uiState: .success([
                League(id: "1", name: "Premier League", abbr: "PL", logos: Logos(light: "", dark: "")),
                League(id: "2", name: "La Liga", abbr: "LL", logos: Logos(light: "", dark: ""))
            ])))
    }
}

struct FailurePreview: PreviewProvider {
    static var previews: some View {
        ContentView()
            .environmentObject(LeagueViewModel(uiState: .failure("Failed to load data")))
    }
}

struct LeagueItemPreview: PreviewProvider {
    static var previews: some View {
        LeagueRow(league: League(id: "1", name: "Premier League", abbr: "PL", logos: Logos(light: "", dark: "")))
            .previewLayout(.fixed(width: 300, height: 50))
    }
}
