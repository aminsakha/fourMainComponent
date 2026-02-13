package com.example.fourmaincomponent.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.time.Duration.Companion.seconds

fun countdownFlow(start: Int): Flow<Int> = flow {
    for (number in start downTo 0) {
        emit(number)
        delay(1000L)
    }
}

fun main(): Unit = runBlocking {
    val startValue = 10

    withTimeoutOrNull(5.seconds) {
        countdownFlow(start = startValue)
            .collect { value ->
                println("Time left: $value")
            }
    }
}