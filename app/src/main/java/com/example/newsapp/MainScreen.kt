package com.example.newsapp

import android.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.newsapp.databinding.MainFragmentBinding

class MainScreen : Fragment() {

    private lateinit var binding: MainFragmentBinding
    private val viewModel:MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(layoutInflater,container,false)

        val listOfCategory = arrayListOf(
            "All News",
            "Business",
            "Entertainment",
            "General",
            "Health",
            "Science",
            "Sports",
            "Technology")

        val adapter = ArrayAdapter(requireContext(), R.layout.simple_list_item_1, listOfCategory)
        binding.categoryList.adapter = adapter
        binding.categoryList.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as String
            findNavController().navigate(MainScreenDirections.actionArticleScreen(selectedItem))
        })

        binding.btnFavorites.setOnClickListener( View.OnClickListener {
            findNavController().navigate(MainScreenDirections.actionFavorite())
            viewModel.getStarredNewsFromDataBase()
        })

        return binding.root
    }

}
