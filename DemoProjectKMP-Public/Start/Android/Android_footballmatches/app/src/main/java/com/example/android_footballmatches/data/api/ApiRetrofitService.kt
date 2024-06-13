package com.example.android_footballmatches.data.api

import com.example.android_footballmatches.data.model.LeagueResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * Created by Menes SIMEU on 06/06/2024.
 * Volvo MoCE Android Team member
 */
interface ApiRetrofitService {
    @GET("leagues")
    suspend fun getLeagues(): LeagueResponse
}

object RetrofitInstance {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // Set log level as needed
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor) // Add logging interceptor for debugging
        .addInterceptor { chain ->
            val request = chain.request()
            val response = chain.proceed(request)
            if (!response.isSuccessful) {
                // Handle error here, you can throw custom exception or log error message
                throw RuntimeException("HTTP error: ${response.code}")
            }
            response
        }
        .build()
    val api: ApiRetrofitService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api-football-standings.azharimm.dev/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiRetrofitService::class.java)
    }
}