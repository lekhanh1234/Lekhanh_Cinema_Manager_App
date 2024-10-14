package com.example.cinemamanagerapp.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinemamanagerapp.R
import com.example.cinemamanagerapp.models.FoodDrink

class FoodDrinkAdapter(private val foodDrinks: List<FoodDrink>) : RecyclerView.Adapter<FoodDrinkAdapter.ViewHolder>()  {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.img_food_drink)
        val tvName = itemView.findViewById<TextView>(R.id.tv_name_food_drink)
        val tvPrice = itemView.findViewById<TextView>(R.id.tv_price_food_drink)
        val type = itemView.findViewById<TextView>(R.id.tv_type_food_drink)

        @SuppressLint("SetTextI18n")
        fun bind(foodDrink: FoodDrink) {
            tvName.text = foodDrink.getName()
            tvPrice.text = "${foodDrink.getPrice().toString()} đ"
            type.text = foodDrink.getType()
            // Load image using a library like Glide or Picasso
            Glide.with(itemView.context).load(foodDrink.getImage()).into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.food_drink_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = foodDrinks.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(foodDrinks[position])
    }
}