package com.example.networking.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

const val FREEPIK_TOKEN = "FPSX7c4596fc6fbe4322ba88763c8666ce92"
const val FREEPIK_HEADER = "x-freepik-api-key"

const val URL = "https://api.freepik.com/v1/resources/28741875/download"

object NetworkClient {
    val client = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })

        }
    }
}