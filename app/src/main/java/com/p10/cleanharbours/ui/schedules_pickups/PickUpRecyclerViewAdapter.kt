package com.p10.cleanharbours.ui.schedules_pickups

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.p10.cleanharbours.R


class PickUpRecyclerViewAdapter(recyclerDataArrayList: ArrayList<PickupsItem>, mcontext: Context) :
    RecyclerView.Adapter<PickUpRecyclerViewAdapter.RecyclerViewHolder>() {
    val pickUpList: ArrayList<PickupsItem>
    private val mcontext: Context

    init {
        pickUpList = recyclerDataArrayList
        this.mcontext = mcontext
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        // Inflate Layout
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.schedule_pickup_item, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        // Set the data to textview and imageview.
        val recyclerData: PickupsItem = pickUpList[position]
        when(recyclerData.type){
            "eWaste" -> {
                holder.imageView.setImageDrawable(mcontext.getDrawable(R.drawable.zolo_pick))
                holder.title.text = "ELECTRONICS"
            }
            "plastic_paper" -> {
                holder.imageView.setImageDrawable(mcontext.getDrawable(R.drawable.khali_bottle))
                holder.title.text = "PLASTIC / PAPER"
            }
            "organic" -> {
                holder.imageView.setImageDrawable(mcontext.getDrawable(R.drawable.bbmp))
                holder.title.text = "ORGANIC"
            }
            "donation" -> {
                holder.imageView.setImageDrawable(mcontext.getDrawable(R.drawable.bbmp))
                holder.title.text = "DONATION"
            }

            else -> {
                holder.imageView.setImageDrawable(mcontext.getDrawable(R.drawable.bbmp))
                holder.title.text = recyclerData.type
            }
        }
        holder.address.text = getAddress(recyclerData.address)
        holder.title.text = recyclerData.type
    }

    private fun getAddress(address: Address?): String{
        return address?.city +",\n"+ address?.street +",\n"+ address?.pinCode +",\n"+ address?.otherInfo
    }

    override fun getItemCount(): Int {
        // this method returns the size of recyclerview
        return pickUpList.size
    }

    // View Holder Class to handle Recycler View.
    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView
        val imageView: ImageView
        val address: TextView

        init {
            title = itemView.findViewById(R.id.wasteType)
            imageView = itemView.findViewById(R.id.vendor)
            address = itemView.findViewById(R.id.addressLocality)
        }
    }
}