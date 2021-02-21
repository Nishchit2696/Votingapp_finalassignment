package com.example.voting.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.voting.R
import com.example.voting.Slideritem
import com.makeramen.roundedimageview.RoundedImageView

class SliderAdapter internal constructor(
    sliderItems: MutableList<Slideritem>,
    viewPager2: ViewPager2
):RecyclerView.Adapter<SliderAdapter.SliderViewHolder>(){

    private val sliderItems: List<Slideritem>
    private val viewPager2: ViewPager2

    init {
        this.sliderItems = sliderItems
        this.viewPager2 = viewPager2
    }

    class SliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val imageView: RoundedImageView = itemView.findViewById(R.id.imageSlide)

        fun image(slideritem: Slideritem){
            imageView.setImageResource(slideritem.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        return SliderViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.slide_item_container,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return sliderItems.size
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.image(sliderItems[position])
        if(position == sliderItems.size - 2){
            viewPager2.post(runnable)
        }
    }

    private val runnable = Runnable {
        sliderItems.addAll(sliderItems)
        notifyDataSetChanged()
    }
}