package com.example.fourmaincomponent

import kotlinx.coroutines.*

fun main() {
    loadHomeDataV1()
}

fun loadHomeDataV1() {
    startLoading()
    runBlocking {
        getBanners()
        getCategories()
    }
    stopLoading()
}

fun loadHomeDataV2() {
    startLoading()
    runBlocking {
        launch {
            getBanners()
        }
       launch {
           getCategories()
       }
    }
    stopLoading()
}

private fun stopLoading() {
    println("stop loading")
}

fun startLoading() {
    print("start loading\n")
}

private suspend fun getBanners() {
    delay(2000)
    print("banners received\n")
}

suspend fun getCategories() {
    delay(2000)
    print("categories received\n")
}


