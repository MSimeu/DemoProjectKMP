package com.example.kmp_footballmatches.data.api

import com.example.kmp_footballmatches.data.model.LeagueResponse
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode

/**
 * Created by Menes SIMEU on 10/06/2024.
 * Volvo MoCE Android Team member
 */
class ApiKtorService {
    suspend fun getLeagues(): LeagueResponse {
        val response: HttpResponse =
            client.get("https://api-football-standings.azharimm.dev/leagues")
        if (response.status == HttpStatusCode.OK) {
            return response.body()
        } else {
            throw Exception("Failed to fetch leagues: ${response.status}")
        }
    }
}
