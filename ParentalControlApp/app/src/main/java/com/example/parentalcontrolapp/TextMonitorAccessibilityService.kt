package com.example.parentalcontrolapp

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.content.Context
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.core.app.NotificationCompat
import android.util.Log
import java.util.Locale

class TextMonitorAccessibilityService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        Log.d("AccessibilityService", "Event type: ${event.eventType}")

        if (event.eventType == AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED) {
            val textContent = event.text.toString().toLowerCase(Locale.getDefault())
            Log.d("AccessibilityService", "Captured text: $textContent")

            val keywords = resources.getStringArray(R.array.suspicious_keywords)

            for (keyword in keywords) {
                if (textContent.contains(keyword.toLowerCase(Locale.getDefault()))) {
                    sendAlert("Suspicious content detected: $keyword")
                    Log.d("AccessibilityService", "Suspicious content detected: $keyword")
                    break
                }
            }
        }
    }

    override fun onInterrupt() {}

    private fun sendAlert(message: String) {
        val channelId = "alert_channel"
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Alerts",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        // Build and send the notification
        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Parental Control Alert")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(1, notification)
        Log.d("AccessibilityService", "Notification sent: $message")
    }
}
