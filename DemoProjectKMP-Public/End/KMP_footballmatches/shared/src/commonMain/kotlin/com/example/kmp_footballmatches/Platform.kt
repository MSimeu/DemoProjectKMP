package com.example.kmp_footballmatches

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform