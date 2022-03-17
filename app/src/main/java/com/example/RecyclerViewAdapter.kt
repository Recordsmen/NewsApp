package com.example

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.model.Source
import com.example.ui.R
import com.example.ui.databinding.RecyclerviewItemBinding

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
        viewHolder.binding.apply {
            tvDescription.text = dataSet[position].description
            tvLink.text = dataSet[position].url
            tvCategory.text = dataSet[position].category
            tvLanguage.text = String.format("Language: "+dataSet[position].language)
            tvName.text = dataSet[position].name
            tvCountry.text = String.format("Country: "+dataSet[position].country)

            cbIsFavorite.setButtonDrawable(
                when (dataSet[position].isFavorite){
                    true -> R.drawable.ic_baseline_favorite_24
                    else -> R.drawable.ic_baseline_favorite_border_24
                }
            )
            cbIsFavorite.isChecked =
                when(dataSet[position].isFavorite){
                    true -> true
                    false -> false
                }
            cbIsFavorite.setOnCheckedChangeListener { compoundButton, b ->
                when(b){
                    true -> {
                        dataSet[position].isFavorite = true
                        compoundButton.setButtonDrawable(R.drawable.ic_baseline_favorite_24)
                    }
                    false -> {
                        dataSet[position].isFavorite = false
                        compoundButton.setButtonDrawable(R.drawable.ic_baseline_favorite_border_24)
                    }
                }
            }
            cbIsFavorite.setOnClickListener {
                itemClickListener.toFavorite(dataSet[position].id,dataSet[position].isFavorite)
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

