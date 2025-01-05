package com.example.parentalcontrolapp

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

object EmailSender {

    private const val TAG = "EmailSender"
    private const val EMAIL_API_URL = "http://192.168.29.155:8080/api/email/send"

    fun sendEmail(context: Context, subject: String, messageBody: String) {
        val sharedPreferences = context.getSharedPreferences("ParentalControlApp", Context.MODE_PRIVATE)
        val toEmail = sharedPreferences.getString("loggedInEmail", null)

        if (toEmail.isNullOrEmpty()) {
            Log.e(TAG, "No logged-in email found. Cannot send email.")
            return
        }

        val emailData = JSONObject().apply {
            put("toEmail", toEmail)
            put("subject", subject)
            put("messageBody", messageBody)
        }

        val requestQueue = Volley.newRequestQueue(context)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,
            EMAIL_API_URL,
            emailData,
            { response ->
                Log.d(TAG, "Email sent successfully: $response")
            },
            { error ->
                Log.e(TAG, "Error sending email: ${error.message}")
            }
        )

        requestQueue.add(jsonObjectRequest)
    }
}
