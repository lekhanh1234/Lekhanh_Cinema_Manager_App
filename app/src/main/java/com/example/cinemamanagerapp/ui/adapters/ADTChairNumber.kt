package com.example.cinemamanagerapp.ui.adapters

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemamanagerapp.R
import com.example.cinemamanagerapp.ui.activities.ChooseChair

class ADTChair(private var selectedList: List<Int>?,private var context: Context) : RecyclerView.Adapter<ADTChair.ViewHold>() {
    private var currentlySelectedChair :  MutableSet<Int> =  mutableSetOf()
    fun getCurrentlySelectedChair() :  MutableSet<Int>{
        return currentlySelectedChair
    }
    class ViewHold(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img : ImageView;
        init {
            img = itemView.findViewById(R.id.Img_SeatCondition)
        }
    }
    fun setNewSelectedSeList(selectedList: List<Int>?){
        this.selectedList = selectedList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHold {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.seat_condition, parent, false)
        return ViewHold(view)
    }

    override fun getItemCount(): Int {
        return 100;
    }

    override fun onBindViewHolder(holder: ViewHold, position: Int) {
        var img = holder.img
        if(selectedList != null && selectedList!!.contains(position + 1)){
            img.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, R.color.red_exp))
        }
        else{
            img.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, R.color.white))
        }
        img.setOnClickListener({
            if(selectedList == null || selectedList!!.contains(position+1) == false){
                if(currentlySelectedChair.contains(position + 1)){
                    img.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, R.color.white))
                    currentlySelectedChair.remove(position + 1)
                }else{
                    img.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, R.color.green_ext))
                    currentlySelectedChair.add(position + 1)
                }
                var activity = context as ChooseChair
                activity.getPaymentSumTV().text ="Tổng Thanh toán : " + (currentlySelectedChair.size * activity.getTicketPrice())+"đ"
            }
        })

    }
}