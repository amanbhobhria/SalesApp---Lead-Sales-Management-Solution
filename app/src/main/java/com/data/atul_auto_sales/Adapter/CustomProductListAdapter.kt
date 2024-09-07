package com.data.atul_auto_sales.Adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.data.atul_auto_sales.Model.LeadDetailBean
import com.data.atul_auto_sales.R
import com.data.atul_auto_sales.Utills.RvStatusClickListner
import com.stpl.antimatter.Utils.ApiContants
import java.io.OutputStream


class CustomProductListAdapter(
    var context: Activity,
    var list: List<LeadDetailBean.Data.LeadRfqImg>,
    var rvClickListner: RvStatusClickListner
) : RecyclerView.Adapter<CustomProductListAdapter.MyViewHolder>() {
    val out: OutputStream? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder { // infalte the item Layout
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_ref_img, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.setIsRecyclable(false)

        /*     holder.tvAdd.background = RoundView(context.resources.getColor(R.color.orange), RoundView.getRadius(20f))
             holder.tvQtyAdd.background = RoundView(context.resources.getColor(R.color.orange), RoundView.getRadius(100f))
             holder.tvQtyMinus.background = RoundView(context.resources.getColor(R.color.orange), RoundView.getRadius(100f))
             holder.tvQty.background = RoundView(Color.TRANSPARENT, RoundView.getRadius(20f), true, R.color.orange)
             holder.tvOff.background = RoundView(context.resources.getColor(R.color.orange), RoundView.getRadius(20f))
             holder.tvAdd.visibility = View.VISIBLE*/


        holder.tvTitle.text = list[position].remarks.toString()
        Glide.with(context)
            .load(ApiContants.BaseUrl+list[position].img)
            .into(holder.ivRqfImg)
        // holder.ivImage.setImageDrawable(context.resources.getDrawable(list[position].drawableId))

        /*  if ("Retailer"=="Retailer"){
        //      holder.itemView.visibility=View.GONE
          }*/


        holder.itemView.setOnClickListener {
              rvClickListner.clickPos(list[position].img,list[position].id)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


    inner class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val ivRqfImg: ImageView = itemview.findViewById(R.id.ivRqfImg)
        val tvTitle: TextView = itemview.findViewById(R.id.tvTitle)


    }

}