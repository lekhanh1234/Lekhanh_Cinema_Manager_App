package com.example.cinemamanagerapp.ui.adapters
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinemamanagerapp.R
import com.example.cinemamanagerapp.api.ShowTimeResponse
import com.example.cinemamanagerapp.ui.activities.Movie


open class ADTShowTimeByCategory(private var category: String?,private var mList: List<ShowTimeResponse>?, private val context : Context) : RecyclerView.Adapter<ADTShowTimeByCategory.ViewHold>() {
    class ViewHold(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
    fun setShowTimeList(list : List<ShowTimeResponse>?){
        if(list != mList){
            this.mList = list
            notifyDataSetChanged()
        }
    }
    fun setCategory(category: String?){
        this.category = category
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHold {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_info, parent, false)
        return ViewHold(view)
    }
    override fun getItemCount(): Int {
        return mList?.let { it.size } ?: 0
    }
    override fun onBindViewHolder(holder: ViewHold, position: Int) {
        var CL_MovieInfoContainer = holder.itemView.findViewById<ConstraintLayout>(R.id.CL_MovieInfoContainer);
        CL_MovieInfoContainer.setOnClickListener({
            var intent = Intent(context,Movie::class.java)
            intent.putExtra("showtimeId",mList!!.get(position).showTimeId)
            intent.putExtra("movieId",mList!!.get(position).movie.id)
            intent.putExtra("movieName",mList!!.get(position).movie.name)
            intent.putExtra("category",category)
            intent.putExtra("ticketPrice",mList!!.get(position).ticket_price)
            intent.putExtra("startTime",mList!!.get(position).start_time)
            context?.startActivity(intent)}
        )
        try {
            var img_movie  = holder.itemView.findViewById<ImageView>(R.id.img_movie)
            Glide.with(holder.itemView.context)
                .load(mList!!.get(position).movie.img_url)
                .into(img_movie)
            img_movie.isClickable = false

        } catch (e: Exception) {
            // TODO: handle exception
            Toast.makeText(context, "Error connecting", Toast.LENGTH_SHORT).show()
        }
        var moviename = holder.itemView.findViewById<TextView>(R.id.tvMovieName);
         moviename.text = mList!!.get(position).movie.name
        var tvShowtime = holder.itemView.findViewById<TextView>(R.id.tvShowtime);
        tvShowtime.text = mList!!.get(position).start_time
    }

}