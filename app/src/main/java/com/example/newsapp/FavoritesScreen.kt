package com.example.newsapp

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import com.example.RecyclerViewAdapter
import com.example.newsapp.databinding.FavoritesScreenFragmentBinding

class FavoritesScreen : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }
    lateinit var binding:FavoritesScreenFragmentBinding
    private lateinit var adapter: RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FavoritesScreenFragmentBinding.inflate(layoutInflater,container,false)
        adapter = RecyclerViewAdapter()
        binding.recyclerView.adapter = adapter
        viewModel.getStarredNewsFromDataBase()
        viewModel.response.observe(viewLifecycleOwner, {
                news -> adapter.setData(news)

        })

        return binding.root
    }

}