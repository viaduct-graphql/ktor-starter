package com.example.viadapp

import com.example.viadapp.injector.ViaductConfiguration
import graphql.ExecutionResult
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import kotlinx.coroutines.future.await
import viaduct.service.api.ExecutionInput

fun Application.configureRouting() {
    val viaduct = ViaductConfiguration().viaductService()

    routing {
        route("/graphql") {
            post {
                @Suppress("UNCHECKED_CAST")
                val request = call.receive<Map<String, Any?>>() as Map<String, Any>

                @Suppress("UNCHECKED_CAST")
                val executionInput = ExecutionInput(
                    query = request["query"] as String,
                    variables = (request["variables"] as? Map<String, Any>) ?: emptyMap(),
                    requestContext = object {},
                    schemaId = SCHEMA_ID
                )

                val result: ExecutionResult = viaduct.executeAsync(executionInput).await()

                when {
                    // This handles the introspection query returning the GraphQL Schema
                    request["operationName"] == "IntrospectionQuery" -> {
                        @Suppress("UNCHECKED_CAST")
                        val data = result.getData<Any>() as Map<String, Any>
                        call.respond(HttpStatusCode.OK, mapOf("data" to data))
                    }

                    else -> {
                        val statusCode = when {
                            result.isDataPresent && result.errors.isNotEmpty() -> HttpStatusCode.BadRequest
                            else -> HttpStatusCode.OK
                        }
                        call.respond(statusCode, result.toSpecification())
                    }
                }
            }
        }
    }
}
