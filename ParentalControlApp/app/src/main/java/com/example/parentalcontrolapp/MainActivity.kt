package com.example.parentalcontrolapp

import android.app.AppOpsManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.usage.UsageStatsManager
import android.app.usage.UsageEvents
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var restrictedAppsRecyclerView: RecyclerView
    private lateinit var addAppButton: Button
    private lateinit var appPackageInput: EditText
    private lateinit var adapter: RestrictedAppsAdapter
    private val restrictedAppsList = mutableListOf<String>()
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var usageStatsManager: UsageStatsManager

    private val REQUEST_POST_NOTIFICATIONS = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set up UI components
        restrictedAppsRecyclerView = findViewById(R.id.restricted_apps_recyclerview)
        addAppButton = findViewById(R.id.add_app_button)
        appPackageInput = findViewById(R.id.app_package_input)

        // Set up RecyclerView
        adapter = RestrictedAppsAdapter(restrictedAppsList)
        restrictedAppsRecyclerView.layoutManager = LinearLayoutManager(this)
        restrictedAppsRecyclerView.adapter = adapter

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), REQUEST_POST_NOTIFICATIONS)
            }

            val openAccessibilityButton: Button = findViewById(R.id.open_accessibility_button)
            openAccessibilityButton.setOnClickListener {
                openAccessibilitySettings()
            }
        }

        // Check permissions and start monitoring
        checkUsageStatsPermission()
        createNotificationChannel()
        startMonitoring()

        addAppButton.setOnClickListener {
            val packageName = appPackageInput.text.toString().trim()
            if (packageName.isNotEmpty()) {
                adapter.addApp(packageName)
                appPackageInput.text.clear()
            }
        }
    }

    private fun openAccessibilitySettings() {
        try {
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Accessibility settings not available", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkUsageStatsPermission() {
        val appOpsManager = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            appOpsManager.unsafeCheckOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), packageName)
        } else {
            appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), packageName)
        }
        if (mode != AppOpsManager.MODE_ALLOWED) {
            Toast.makeText(this, "Please enable Usage Access in settings", Toast.LENGTH_LONG).show()
            startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "alert_channel",
                "Suspicious Activity Alerts",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Alerts when restricted apps are opened"
            }
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun startMonitoring() {
        usageStatsManager = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        handler.post(checkUsageRunnable)
    }

    private val checkUsageRunnable = object : Runnable {
        override fun run() {
            val restrictedAppOpened = isRestrictedAppInForeground()
            if (restrictedAppOpened) {
                sendNotification("Restricted app opened!")
            }
            handler.postDelayed(this, 5000)
        }
    }

    private fun isRestrictedAppInForeground(): Boolean {
        val endTime = System.currentTimeMillis()
        val startTime = endTime - 5000

        val usageEvents = usageStatsManager.queryEvents(startTime, endTime)
        val event = UsageEvents.Event()
        while (usageEvents.hasNextEvent()) {
            usageEvents.getNextEvent(event)
            if (event.eventType == UsageEvents.Event.MOVE_TO_FOREGROUND &&
                restrictedAppsList.contains(event.packageName)) {
                return true
            }
        }
        return false
    }

    private fun sendNotification(message: String) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(this, "alert_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Parental Control Alert")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
        notificationManager.notify(1, notification)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_POST_NOTIFICATIONS) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            } else {
                Toast.makeText(this, "Notifications permission denied. Alerts may not be shown.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
