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
import androidx.work.NetworkType
import androidx.work.workDataOf
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = "WorkManager demo",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Spacer(Modifier.height(16.dp))

                    val workManager = WorkManager.getInstance(this@MainActivity)

                    fun enqueueOneTime(label: String, withConstraints: Boolean) {
                        val constraints = if (withConstraints) {
                            Constraints.Builder()
                                .setRequiredNetworkType(NetworkType.CONNECTED)
                                .build()
                        } else {
                            Constraints.NONE
                        }

                        val request = OneTimeWorkRequestBuilder<SimpleLogWorker>()
                            .setInitialDelay(5, TimeUnit.SECONDS)
                            .setConstraints(constraints)
                            .setInputData(workDataOf("label" to label))
                            .build()

                        workManager.enqueue(request)
                    }

                    Button(onClick = {
                        // 1) OneTimeWork (no constraints) — runs after 5s
                        enqueueOneTime(label = "OneTimeWork", withConstraints = false)
                    }) {
                        Text("OneTimeWork (5s)")
                    }

                    Spacer(Modifier.height(10.dp))

                    Button(onClick = {
                        // 2) OneTimeWork + Constraints — needs internet, then runs after 5s
                        enqueueOneTime(label = "OneTime + Constraint(Network)", withConstraints = true)
                    }) {
                        Text("OneTime + Constraint")
                    }

                    Spacer(Modifier.height(10.dp))

                    Button(onClick = {
                        // 3) PeriodicWork — minimum is 15 minutes on Android
                        val periodicRequest = PeriodicWorkRequestBuilder<SimpleLogWorker>(
                            15, TimeUnit.MINUTES
                        )
                            .setInputData(workDataOf("label" to "PeriodicWork"))
                            .build()

                        // Use a fixed name so repeated clicks don't create endless periodic jobs
                        workManager.enqueueUniquePeriodicWork(
                            "SimpleLogWorkerPeriodic",
                            ExistingPeriodicWorkPolicy.UPDATE,
                            periodicRequest
                        )
                    }) {
                        Text("PeriodicWork (15m)")
                    }

                    Spacer(Modifier.height(18.dp))

                    Button(onClick = { workManager.cancelAllWork() }) {
                        Text("Cancel All Works")
                    }
                }
            }
        }
    }
}