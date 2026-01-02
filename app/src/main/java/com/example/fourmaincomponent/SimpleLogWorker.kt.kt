package com.example.fourmaincomponent

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class SimpleLogWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        Log.d("SimpleLogWorker", "WorkManager executed after delay ✅")
        return Result.success()
    }
}