package com.example.fourmaincomponent

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.work.*

class RetryableSyncWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        setProgressAsync(workDataOf("message" to "Checking internet connection..."))

        if (!isInternetAvailable(applicationContext)) {
            setProgressAsync(workDataOf("message" to "No internet, retrying..."))
            // آموزشی: یک مکث کوتاه که UI فرصت دیدن پیام RUNNING رو داشته باشه
            Thread.sleep(600)
            return Result.retry()
        }

        setProgressAsync(workDataOf("message" to "Internet OK. Syncing..."))
        Thread.sleep(1500)

        setProgressAsync(workDataOf("message" to "Sync succeeded"))
        Thread.sleep(300)

        return Result.success()
    }

    private fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)

    }
}
