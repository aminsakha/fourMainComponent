package com.example.fourmaincomponent

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AirplaneModeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
            val isEnabled = intent.getBooleanExtra("state", false)

            if (isEnabled) {
                Log.d("AirplaneReceiver", "Airplane Mode is ON ✈️")
            } else {
                Log.d("AirplaneReceiver", "Airplane Mode is OFF 📶")
            }
        }
    }
}