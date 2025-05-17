package com.axelbrians.compose_multiplatform

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform