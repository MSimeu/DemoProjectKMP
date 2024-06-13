package com.example.android_footballmatches.data.api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.gson.*
import com.example.android_footballmatches.data.model.LeagueResponse

/**
 * Created by Menes SIMEU on 06/06/2024.
 * Volvo MoCE Android Team member
 */
class ApiKtorService {
    private val client = HttpClient {
        install(ContentNegotiation) {
            gson()
        }
    }

    suspend fun getLeagues(): LeagueResponse {
        val response: HttpResponse = client.get("https://api-football-standings.azharimm.dev/leagues")
        if (response.status == HttpStatusCode.OK) {
            return response.body()
        } else {
            throw Exception("Failed to fetch leagues: ${response.status}")
        }
    }
}
