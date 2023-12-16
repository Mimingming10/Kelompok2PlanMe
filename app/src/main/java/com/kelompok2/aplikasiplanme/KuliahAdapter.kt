package com.kelompok2.aplikasiplanme

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.kelompok2.aplikasiplanme.databinding.ItemViewKuliahProfileBinding

class KuliahAdapter : RecyclerView.Adapter<KuliahAdapter.KuliahViewHolder>(){
    class KuliahViewHolder(val binding : ItemViewKuliahProfileBinding) : ViewHolder(binding.root)

    val diffUtil = object : DiffUtil.ItemCallback<Users>(){
        override fun areItemsTheSame(oldItem: Users, newItem: Users): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Users, newItem: Users): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, diffUtil)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KuliahAdapter.KuliahViewHolder {
        return KuliahViewHolder(ItemViewKuliahProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: KuliahAdapter.KuliahViewHolder, position: Int) {
        val kulData = differ.currentList[position]
        holder.binding.apply {
            Glide.with(holder.itemView).load(kulData.userImage).into(ivKuliahProfile)
            tvKuliahName.text = kulData.userName
        }
        holder.itemView.setOnClickListener {
            val action = KuliahFragmentDirections.actionKuliahFragmentToKerjaFragment(kulData)
            Navigation.findNavController(it).navigate(action)
        }
    }

}