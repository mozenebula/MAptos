package com.qstack.maptos.ui.home.tabs

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView

import com.qstack.maptos.ui.home.tabs.placeholder.PlaceholderContent.PlaceholderItem
import com.qstack.maptos.databinding.FragmentHomeTokenBinding

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class HomeTokenAdapter(
    private val values: List<PlaceholderItem>
) : RecyclerView.Adapter<HomeTokenAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentHomeTokenBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
//        holder.idView.text = item.id
//        holder.contentView.text = item.content
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentHomeTokenBinding) :
        RecyclerView.ViewHolder(binding.root) {
//        val idView: TextView = binding.tokenName
//        val contentView: TextView = binding.amount

        override fun toString(): String {
            return super.toString() + " '" + "'"
        }
    }

}