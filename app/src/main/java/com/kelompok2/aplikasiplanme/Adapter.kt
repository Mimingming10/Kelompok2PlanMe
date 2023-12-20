package com.kelompok2.aplikasiplanme

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class Adapter(private val context:android.content.Context, private var dataList:List<DataClassNote>): RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recyecler_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(context).load(dataList[position].dataImage).into(holder.recImage)
        holder.recTitle.text = dataList[position].dataTitle
        holder.recDesc.text = dataList[position].dataDesc
        holder.recPriority.text = dataList[position].dataPriority
        holder.recCard.setOnClickListener {
            val intent = Intent(context, detailActivity::class.java)
            intent.putExtra("Image", dataList[holder.adapterPosition].dataImage)
            intent.putExtra("Description", dataList[holder.adapterPosition].dataDesc)
            intent.putExtra("Title", dataList[holder.adapterPosition].dataTitle)
            intent.putExtra("Priority", dataList[holder.adapterPosition].dataPriority)
            context.startActivity(intent)
        }
    }

    fun searchDataList(searchList: List<DataClassNote>) {
        dataList = searchList
        notifyDataSetChanged()
    }

}

class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    var recImage: ImageView
    var recTitle: TextView
    var recDesc: TextView
    var recPriority: TextView
    var recCard: CardView

    init{
        recImage = itemView.findViewById(R.id.recImage)
        recCard = itemView.findViewById(R.id.recCard)
        recDesc = itemView.findViewById(R.id.recDesc)
        recTitle = itemView.findViewById(R.id.recTitle)
        recPriority = itemView.findViewById(R.id.recPriority)
    }
}