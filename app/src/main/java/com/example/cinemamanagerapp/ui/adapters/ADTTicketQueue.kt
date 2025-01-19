package com.example.cinemamanagerapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.cinemamanagerapp.R
import com.example.cinemamanagerapp.api.ShowTimeResponse

class ADTTicketQueue(private var mList: List<ShowTimeResponse>?,private var context : Context) : BaseAdapter(){
    fun setNewData(mList: List<ShowTimeResponse>?){
        this.mList = mList
        notifyDataSetChanged()
    }
    fun getData() : List<ShowTimeResponse>?{
        return mList
    }
    override fun getCount(): Int {
        return if(mList!=null) mList!!.size else 0
    }

    override fun getItem(p0: Int): Any {
        TODO("Not yet implemented")
    }

    override fun getItemId(p0: Int): Long {
        TODO("Not yet implemented")
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var view = LayoutInflater.from(p2?.context).inflate(R.layout.ticket_queue,null);
        val element = mList!!.get(p0)
        var tv_movieName = view.findViewById<TextView>(R.id.tv_movieName);
        tv_movieName.text= "Tên phim : "+element.movie.name
        var tv_ticket_price = view.findViewById<TextView>(R.id.tv_ticket_price);
        tv_ticket_price.text = "Giá vé : "+element.ticket_price +" đ"
        var tv_showTime = view.findViewById<TextView>(R.id.tv_showTime);
        tv_showTime.text = "Công chiếu : "+element.start_time
        var tv_room = view.findViewById<TextView>(R.id.tv_room);
        tv_room.text = "Phòng : "+element.room
        return view;
    }

}