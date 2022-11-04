package com.p10.cleanharbours.ui.category

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.p10.cleanharbours.R


class RecyclerViewAdapter(recyclerDataArrayList: ArrayList<WasteCategory>, mcontext: Context) :
    RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {
    val categoryList: ArrayList<WasteCategory>
    private val mcontext: Context

    init {
        categoryList = recyclerDataArrayList
        this.mcontext = mcontext
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        // Inflate Layout
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.grid_item_view, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        // Set the data to textview and imageview.
        val recyclerData: WasteCategory = categoryList[position]
        when(recyclerData.category){
            "eWaste" -> {
                holder.imageView.setImageDrawable(mcontext.getDrawable(R.drawable.e_waste))
                holder.title.text = "ELECTRONICS"
            }
            "plastic_paper" -> {
                holder.imageView.setImageDrawable(mcontext.getDrawable(R.drawable.plastic_waste))
                holder.title.text = "PLASTIC / PAPER"
            }
            "organic" -> {
                holder.imageView.setImageDrawable(mcontext.getDrawable(R.drawable.organic_waste))
                holder.title.text = "ORGANIC"
            }
            "donation" -> {
                holder.imageView.setImageDrawable(mcontext.getDrawable(R.drawable.donate))
                holder.title.text = "DONATION"
            }

        }
        if(recyclerData.isSelected){
            holder.title.visibility = View.VISIBLE
        }
        else {
            holder.title.visibility = View.GONE
        }
        holder.imageView.setOnClickListener {
            recyclerData.isSelected = !recyclerData.isSelected
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        // this method returns the size of recyclerview
        return categoryList.size
    }

    // View Holder Class to handle Recycler View.
    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView
        val imageView: ImageView

        init {
            title = itemView.findViewById(R.id.idTVCourse)
            imageView = itemView.findViewById(R.id.gridItemImage)
        }
    }
}