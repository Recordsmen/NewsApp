package com.example.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.ItemClickListener
import com.example.RecyclerViewAdapter
import com.example.ui.databinding.ArticleScreenFragmentBinding
import com.example.viewModel.MainViewModel

class ArticleScreen : Fragment(), ItemClickListener {

    private lateinit var adapter: RecyclerViewAdapter
    private lateinit var binding: ArticleScreenFragmentBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = ArticleScreenFragmentBinding.inflate(layoutInflater,container,false)

        val args: ArticleScreenArgs by navArgs()
        viewModel.displayNews(args.category)


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

    override fun toFavorite(NewsId: String, position: Boolean) {
        viewModel.setNewsStarred(NewsId,position)
    }
}