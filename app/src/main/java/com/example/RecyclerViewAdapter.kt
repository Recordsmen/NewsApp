package com.example

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.model.Source
import com.example.newsapp.databinding.RecyclerviewItemBinding

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    private var dataSet = listOf<Source>()

    class ViewHolder private constructor(val binding: RecyclerviewItemBinding): RecyclerView.ViewHolder(binding.root) {


        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecyclerviewItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.tvDescription.text = dataSet[position].description
        viewHolder.binding.tvLink.text = dataSet[position].url
        viewHolder.binding.tvCategory.text = dataSet[position].category
        viewHolder.binding.tvLanguage.text = dataSet[position].language
        viewHolder.binding.tvName.text = dataSet[position].name
        viewHolder.binding.tvCountry.text = dataSet[position].country

    }

    override fun getItemCount() = dataSet.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList:List<Source>){
        dataSet = newList
        notifyDataSetChanged()
    }
}
class favoriteSettings(val clickListener: (source: Source) -> Unit) {
    fun onClick(source: Source){ source.isStarred
    }
}