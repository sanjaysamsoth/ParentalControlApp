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
        // Log event type to ensure the service is capturing the intended events
        Log.d("AccessibilityService", "Event type: ${event.eventType}")

        // Only proceed if the event type is VIEW_TEXT_CHANGED (or adjust as needed)
        if (event.eventType == AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED) {
            // Capture text content from the accessibility event
            val textContent = event.text.toString().toLowerCase(Locale.getDefault())
            Log.d("AccessibilityService", "Captured text: $textContent")

            // Load the list of suspicious keywords from resources
            val keywords = resources.getStringArray(R.array.suspicious_keywords)

            // Check if any keyword is in the text content
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

        // Create a notification channel for Android 8.0+ if it doesn’t exist
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
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Ensure this drawable exists
            .setContentTitle("Parental Control Alert")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(1, notification)
        Log.d("AccessibilityService", "Notification sent: $message")
    }
}
