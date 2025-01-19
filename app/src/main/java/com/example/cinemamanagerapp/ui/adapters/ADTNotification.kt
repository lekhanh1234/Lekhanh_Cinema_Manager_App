package com.example.cinemamanagerapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.cinemamanagerapp.R
import com.example.cinemamanagerapp.api.NotificationResponse

class ADTNotification(private var notificationList : List<NotificationResponse>?) :

    BaseAdapter() {
    override fun getCount(): Int {
        return if (notificationList == null) 0 else notificationList!!.size
    }
    fun setNewNotification(notificationList: List<NotificationResponse>?){
        this.notificationList = notificationList
    }

    override fun getItem(p0: Int): Any {
        TODO("Not yet implemented")
    }

    override fun getItemId(p0: Int): Long {
        TODO("Not yet implemented")
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var view = LayoutInflater.from(p2?.context).inflate(R.layout.notification_message,null);
        var tv_notification = view.findViewById<TextView>(R.id.tv_notification)
        tv_notification.text = notificationList!!.get(p0).message
        return view
    }
}