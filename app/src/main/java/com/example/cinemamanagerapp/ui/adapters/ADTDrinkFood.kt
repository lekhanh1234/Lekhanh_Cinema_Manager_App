package com.example.cinemamanagerapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.cinemamanagerapp.R
import com.example.cinemamanagerapp.api.FoodDrinksResponse
import com.example.cinemamanagerapp.ui.activities.Payment

class ADTDrinkFood(private var drinkFoodList : List<FoodDrinksResponse>?,private val context: Context) : BaseAdapter() {
    private val map :MutableMap <Int,Int> = mutableMapOf()
    fun getDrinkFoodMap(): MutableMap<Int,Int>{
        return map
    }
    fun setDrinkFookList(drinkFood : List<FoodDrinksResponse>?){
        this.drinkFoodList = drinkFood
    }
    fun getDrinkFookList() : List<FoodDrinksResponse>?{
        return drinkFoodList
    }
    override fun getCount(): Int {
        return if(drinkFoodList == null) 0 else drinkFoodList!!.size
    }

    override fun getItem(p0: Int): Any {
        TODO("Not yet implemented")
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view = LayoutInflater.from(p2?.context).inflate(R.layout.drink_food,null)
        val img_FoodImage = view.findViewById<ImageView>(R.id.img_FoodImage);
        val element = drinkFoodList!!.get(p0)
        Glide.with(context)
            .load(element.imgUrl)
            .into(img_FoodImage)
        val tv_FoodName = view.findViewById<TextView>(R.id.tv_FoodName)
        tv_FoodName.text =element.name
        val img_Sub = view.findViewById<ImageButton>(R.id.img_Sub)
        val tv_FoodAmount = view.findViewById<TextView>(R.id.tv_FoodAmount)
        val img_Add = view.findViewById<ImageButton>(R.id.img_Add)


        img_Sub.setOnClickListener({
            val currentSelectedAmount= map.get(element.foodDrinkId)?: 0
            if(currentSelectedAmount <= 1){
                map.remove(element.foodDrinkId)
                tv_FoodAmount.text = ""+0
            }else {
                map.put(element.foodDrinkId, currentSelectedAmount - 1)
                tv_FoodAmount.text = ""+ (currentSelectedAmount - 1)
                (context as Payment).updatePaymentSum()
            }
        })
        img_Add.setOnClickListener({
            val currentSelectedAmount = map.get(element.foodDrinkId)?: 0
            if(currentSelectedAmount < 10){ // chỉ cho phép tối đa 10 .
                map.put(element.foodDrinkId, currentSelectedAmount + 1)
                tv_FoodAmount.text = ""+ (currentSelectedAmount + 1)
                (context as Payment).updatePaymentSum()
            }
        })
        return view;
    }

}