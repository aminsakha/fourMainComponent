package com.example.fourmaincomponent

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking

// First example
fun main() {
    runBlocking {
        val userQueries = flowOf(
            "k", "ko", "kot", "kotl", "kotli", "kotlin"
        )

        userQueries.collect { result ->
            delay(500)
            println("Result is : $result")
        }
    }
}

// Second example

//fun main() {
//    runBlocking {
//        val simpleFlow = flow {
//            repeat(3) {
//                delay(1000)
//                emit(it)
//            }
//        }
//
//        simpleFlow.collect { value ->
//            println("Received $value")
//        }
//    }
//}
