package com.example.newsapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.ItemClickListener
import com.example.RecyclerViewAdapter
import com.example.newsapp.databinding.FavoritesScreenFragmentBinding

class FavoritesScreen : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    lateinit var binding:FavoritesScreenFragmentBinding
    private lateinit var adapter: RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FavoritesScreenFragmentBinding.inflate(layoutInflater,container,false)
        adapter = RecyclerViewAdapter(object : ItemClickListener {
            override fun toFavorite(NewsId: String, position: Boolean) {
                viewModel.setNewsStarred(NewsId,position)
            }
        })
        binding.recyclerView.adapter = adapter

        viewModel.response.observe(viewLifecycleOwner, {
                news -> adapter.setData(news)

        })

        return binding.root
    }

}