package com.example.fourmaincomponent

import kotlinx.coroutines.delay

fun main() {
    print("main Started")
    networkCall()
}

fun networkCall(): String {
    delay(2000)
    return "finished network call"
}