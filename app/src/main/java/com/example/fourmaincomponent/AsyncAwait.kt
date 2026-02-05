package com.example.fourmaincomponent

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() {
    println("main started")

    runBlocking {
        var firstName = ""
        var lastName = ""

        firstName = getFirstName()
        lastName = getLastName()

        println("name : $firstName , lastName: $lastName")
    }

    println("main ended")
}

suspend fun getFirstName(): String {
    delay(3000)
    return "Ali"
}

suspend fun getLastName(): String {
    delay(3000)
    return "AliPoor"
}