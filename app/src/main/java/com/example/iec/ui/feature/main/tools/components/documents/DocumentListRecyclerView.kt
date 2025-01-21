package com.example.iec.ui.feature.main.tools.components.documents

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.iec.databinding.ItemRcviewBinding


class DocumentListRecyclerView(

    private var listData: List<String> = listOf("Item 1", "Item 2", "Item 3")
) : RecyclerView.Adapter<DocumentListRecyclerView.ItemViewHolder>() {


    @SuppressLint("NotifyDataSetChanged")
    fun updateListData(list: List<String>) {
        listData = list
        notifyDataSetChanged()
    }

    class ItemViewHolder(private val binding: ItemRcviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.itemText.text = item
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemRcviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size

}