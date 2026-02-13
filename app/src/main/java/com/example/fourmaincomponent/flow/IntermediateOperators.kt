package com.example.fourmaincomponent.flow

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    flowOf(1, 2, 3)
        .map { number -> number * 2 }
        .collect { println(it) }
}


//flowOf(1, 2, 3, 4)
//    .filter { number -> number % 2 == 0 }
//    .collect { println(it) }


//flowOf(1, 2, 3)
//    .onEach { println("Emitting: $it") }
//    .collect()


//flowOf(1, 2, 3, 4)
//    .take(2)
//    .collect { println(it) }


//flow {
//    emit("k")
//    delay(100)
//    emit("ko")
//    delay(600)
//    emit("kot")
//}
//.debounce(500)
//.collect { println(it) }


//flowOf("A", "A", "B", "B", "C")
//    .distinctUntilChanged()
//    .collect { println(it) }



/**
 * Performs a search request for each user query.
 *
 * This function uses `mapLatest` to ensure that only the most recent query
 * is processed. If a new query arrives while a previous API request is
 * still running, the previous request is cancelled automatically.
 *
 * Why is this important?
 *
 * In a search box scenario, users type quickly:
 * k → ko → kot → kotlin
 *
 * Without `mapLatest`, multiple API calls may run concurrently.
 * Because network responses can arrive out of order, an older request
 * (for example "k") might finish after a newer one ("kotlin"),
 * causing outdated results to be displayed.
 *
 * Using `mapLatest` guarantees:
 * - Only the latest query result is delivered
 * - Previous in-flight requests are cancelled
 * - The UI always reflects the most recent user input
 *
 * This pattern is commonly used in search, filtering,
 * and any "latest wins" user interaction.
 */

//fun searchResults(queryFlow: Flow<String>): Flow<String> {
//    return queryFlow
//        .debounce(300)
//        .distinctUntilChanged()
//        .mapLatest { query ->
//            fakeApiSearch(query)
//        }
//}
///** Simulates a slow API request */
//suspend fun fakeApiSearch(query: String): String {
//    delay(1000) // simulate network delay
//    return "Results for \"$query\""
//}

