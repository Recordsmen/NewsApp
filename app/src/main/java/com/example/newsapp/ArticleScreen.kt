package com.example.newsapp

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.RecyclerViewAdapter
import com.example.newsapp.databinding.ArticleScreenFragmentBinding

class ArticleScreen : Fragment() {

    private lateinit var adapter: RecyclerViewAdapter
    private lateinit var binding: ArticleScreenFragmentBinding
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = ArticleScreenFragmentBinding.inflate(layoutInflater,container,false)
        val args: ArticleScreenArgs by navArgs()
        when (args.category){
            "Business" -> viewModel.getCategoryNewsFromDataBase("business")
            "Entertainment" -> viewModel.getCategoryNewsFromDataBase("entertainment")
            "General" -> viewModel.getCategoryNewsFromDataBase("general")
            "Health" -> viewModel.getCategoryNewsFromDataBase("health")
            "Science" -> viewModel.getCategoryNewsFromDataBase("science")
            "Sports" -> viewModel.getCategoryNewsFromDataBase("sports")
            "Technology" -> viewModel.getCategoryNewsFromDataBase("technology")
            else -> viewModel.getNewsFromDataBase()
        }

        binding.lifecycleOwner = this

        adapter = RecyclerViewAdapter()
        binding.recyclerView.adapter = adapter

        viewModel.response.observe(viewLifecycleOwner, {
            news -> adapter.setData(news)

        })

        return binding.root
    }


}