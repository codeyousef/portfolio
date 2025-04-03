package code.yousef.api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.js.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
// import kotlinx.browser.console // Removed by user

/**
 * API client using Ktor Client for making HTTP requests (Reverted to object)
 */
object ApiClient { // Changed back to object
    // Configure Ktor HttpClient (now an object member)
    val client = HttpClient(Js) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true // Keep ignoring unknown keys
            })
        }

        // Optional: Configure default request parameters if needed
        // expectSuccess = true // Throw exceptions for non-2xx responses
    }

    /**
     * Performs a GET request to the specified endpoint using Ktor Client
     */
    suspend inline fun <reified T> get(endpoint: String): T? {
        return try {
            client.get(endpoint) {
                // Optional: Add headers or parameters here if needed
                // headers { append("X-Custom-Header", "value") }
            }.body<T>() // Ktor handles deserialization
        } catch (e: Exception) {
            // console.error("API GET request failed for endpoint '$endpoint': ${e.message}") // Removed console
            println("API GET request failed for endpoint '$endpoint': ${e.message}") // Use println as fallback
            // Consider more specific error handling based on Ktor exceptions if needed
            null
        }
    }

    /**
     * Performs a POST request to the specified endpoint using Ktor Client
     */
    suspend inline fun <reified TRequest, reified TResponse> post(endpoint: String, body: TRequest): TResponse? {
        return try {
            client.post(endpoint) {
                contentType(ContentType.Application.Json) // Set content type
                setBody(body) // Ktor handles serialization
            }.body<TResponse>() // Ktor handles deserialization
        } catch (e: Exception) {
            // console.error("API POST request failed for endpoint '$endpoint': ${e.message}") // Removed console
            println("API POST request failed for endpoint '$endpoint': ${e.message}") // Use println as fallback
            // Consider more specific error handling based on Ktor exceptions if needed
            null
        }
    }

    // Optional: Add methods for PUT, DELETE, etc. as needed following the same pattern

    // Companion object REMOVED
    // close() method REMOVED
}