package com.example

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.model.Source
import com.example.newsapp.R
import com.example.newsapp.databinding.RecyclerviewItemBinding

class RecyclerViewAdapter() : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
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

        viewHolder.binding.isFavorite.setOnCheckedChangeListener { compoundButton, b ->
            compoundButton.setButtonDrawable( when (b){
                true -> R.drawable.ic_baseline_favorite_24
                false -> R.drawable.ic_baseline_favorite_border_24
            })

            when(b){
                true -> {
                    dataSet[position].isStarred = true
                    viewHolder.binding.viewmodel?.setNewsStarred(
                        dataSet[position].id,
                        dataSet[position].isStarred
                    )
                    Log.i(dataSet[position].id,"InFavorite: ${dataSet[position].isStarred}")
                }
                false -> {
                    dataSet[position].isStarred = false
                    viewHolder.binding.viewmodel?.setNewsStarred(
                        dataSet[position].id,
                        dataSet[position].isStarred
                    )
                    Log.i(dataSet[position].id,"InFavorite: ${dataSet[position].isStarred}")
                }

            }
        }
    }

    override fun getItemCount() = dataSet.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList:List<Source>){
        dataSet = newList
        notifyDataSetChanged()
    }
}

