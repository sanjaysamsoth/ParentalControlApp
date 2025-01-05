package com.example.parentalcontrolapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RestrictedAppsAdapter(private val restrictedApps: MutableList<String>) :
    RecyclerView.Adapter<RestrictedAppsAdapter.AppViewHolder>() {

    class AppViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val appNameTextView: TextView = itemView.findViewById(R.id.app_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_restricted_app, parent, false)
        return AppViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        holder.appNameTextView.text = restrictedApps[position]
    }

    override fun getItemCount(): Int {
        return restrictedApps.size
    }

    fun addApp(packageName: String) {
        restrictedApps.add(packageName)
        notifyItemInserted(restrictedApps.size - 1)
    }
}
