package com.moment2.moment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.video_row.view.*


class MyMomentAdapter : RecyclerView.Adapter<CustomViewHolder>() {
    //number of items
    override fun getItemCount(): Int {
        return 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.video_row, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        holder.view.text_view_video_title?.hint = "Video Title"
    }
}

class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view){

}