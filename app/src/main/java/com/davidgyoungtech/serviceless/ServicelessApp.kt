package com.davidgyoungtech.serviceless

import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import java.util.concurrent.Executors

class ServicelessApp: Application() {
    val executor = Executors.newFixedThreadPool(1)
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "The app just started.")
        // Set up for any other programmatic broadcasts here
        val filter = IntentFilter(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED)
        this?.registerReceiver(MyBroadcastReceiver(), filter)

        executor.execute(Runnable {
            while (true) {
                Log.d(TAG, "I will log this line every 10 seconds forever")
                Thread.sleep(10000);
            }
        })
    }

    class MyBroadcastReceiver: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (Intent.ACTION_BOOT_COMPLETED.equals(intent?.action)) {
                Log.d(TAG, "The phone just booted.")
            }

            // This cannot execute for more than 10 seconds otherwise Android kills your app
        }
    }

    companion object {
        val TAG = "ServicelessApp"
    }
}
