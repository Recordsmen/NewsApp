package com.example

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.model.Source
import com.example.newsapp.R
import com.example.newsapp.databinding.RecyclerviewItemBinding

class RecyclerViewAdapter(private val itemClickListener: ItemClickListener) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>(){
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
        viewHolder.binding.tvCategory.text = "Category:"+dataSet[position].category
        viewHolder.binding.tvLanguage.text = "Language:"+dataSet[position].language
        viewHolder.binding.tvName.text = dataSet[position].name
        viewHolder.binding.tvCountry.text = "Country:"+dataSet[position].country
        viewHolder.binding.tvId.text = dataSet[position].id
        viewHolder.binding.isFavorite.setButtonDrawable(
            when (dataSet[position].isStarred){
                true -> R.drawable.ic_baseline_favorite_24
                else -> R.drawable.ic_baseline_favorite_border_24
            }
        )
        viewHolder.binding.isFavorite.isChecked =
            when(dataSet[position].isStarred){
                true -> true
                false -> false
            }

        viewHolder.binding.isFavorite.setOnCheckedChangeListener { compoundButton, b ->
            when(b){
                true -> {
                    dataSet[position].isStarred = true
                    compoundButton.setButtonDrawable(R.drawable.ic_baseline_favorite_24)
                }
                false -> {
                    dataSet[position].isStarred = false
                    compoundButton.setButtonDrawable(R.drawable.ic_baseline_favorite_border_24)
                }

            }
        }

        viewHolder.binding.isFavorite.setOnClickListener {
            itemClickListener.toFavorite(dataSet[position].id,dataSet[position].isStarred)
            Log.i(dataSet[position].id,"InFavoriteINTERFACE: ${dataSet[position].isStarred}")
        }
    }

    override fun getItemCount() = dataSet.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList:List<Source>){
        dataSet = newList
        notifyDataSetChanged()
    }
}

