package com.example.newsapp

import android.R
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.example.newsapp.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    private lateinit var binding: MainFragmentBinding

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(layoutInflater,container,false)
        binding.lifecycleOwner = this
        val listOfCategory = arrayListOf(
            "Business",
            "Entertainment",
            "General",
            "Health",
            "Science",
            "Sports",
            "Technology",
            "All News")

        val adapter = ArrayAdapter(requireContext(), R.layout.simple_list_item_1, listOfCategory)
        binding.categoryList.adapter = adapter
        binding.categoryList.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as String
            findNavController().navigate(MainFragmentDirections.actionArticleScreen(selectedItem))
        })

        binding.btnFavorites.setOnClickListener( View.OnClickListener {
            findNavController().navigate(MainFragmentDirections.actionFavorite())
        })

        return binding.root
    }

}
