package com.example.fourmaincomponent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.work.*
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val workManager = WorkManager.getInstance(this)

        setContent {
            MaterialTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    val workInfos by workManager
                        .getWorkInfosForUniqueWorkFlow("retryable_sync")
                        .collectAsStateWithLifecycle(emptyList())

                    val workInfo: WorkInfo? = workInfos.firstOrNull()

                    val stateText = workInfo?.state?.name ?: "IDLE"
                    val attemptNumber = (workInfo?.runAttemptCount ?: 0) + 1

                    val stableMessage = when (workInfo?.state) {
                        WorkInfo.State.RUNNING ->
                            workInfo.progress.getString("message") ?: "Running..."
                        WorkInfo.State.ENQUEUED ->
                            if ((workInfo.runAttemptCount) > 0) "No internet (will retry)..."
                            else "Waiting to start..."
                        WorkInfo.State.SUCCEEDED -> "Sync succeeded ✅"
                        WorkInfo.State.CANCELLED -> "Cancelled"
                        WorkInfo.State.FAILED -> "Failed"
                        else -> "-"
                    }

                    Text("State: $stateText")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Attempt: $attemptNumber")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Last message: $stableMessage")

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(onClick = {
                        val request =
                            OneTimeWorkRequestBuilder<RetryableSyncWorker>()
                                .setInitialDelay(5, TimeUnit.SECONDS)
                                .setBackoffCriteria(
                                    BackoffPolicy.LINEAR,
                                    5,
                                    TimeUnit.SECONDS
                                )
                                .build()

                        workManager.enqueueUniqueWork(
                            "retryable_sync",
                            ExistingWorkPolicy.REPLACE,
                            request
                        )
                    }) {
                        Text("Start Retryable Sync")
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(onClick = {
                        workManager.cancelUniqueWork("retryable_sync")
                    }) {
                        Text("Cancel Sync")
                    }
                }
            }
        }
    }
}