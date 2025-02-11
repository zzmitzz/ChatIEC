package com.example.camera_ml.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.camera_ml.databinding.ItemRvTextBinding
import com.google.mlkit.vision.text.Text


class TextDiffCallback: DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem.contentEquals(newItem)
    }

}



class TextExtractAdapter(): RecyclerView.Adapter<TextExtractAdapter.TextExtractViewHolder>() {

    var selectToCopy: (String) -> Unit = {

    }
    var listData = mutableListOf<String>()

    fun setData(data: List<String>){
        listData.clear()
        listData.addAll(data)
        notifyDataSetChanged()
    }


    inner class TextExtractViewHolder(
        private val binding: ItemRvTextBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bindView(item: String){
            binding.tvContent.text = item
            binding.root.setOnClickListener {
                selectToCopy(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextExtractViewHolder {
        val binding = ItemRvTextBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TextExtractViewHolder(binding)
    }

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: TextExtractViewHolder, position: Int) {
        holder.bindView(listData[position])
    }
}