package com.example.ui

import android.R
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.ui.databinding.MainFragmentBinding
import com.example.viewModel.MainViewModel
import android.util.Log
import androidx.lifecycle.Observer
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException

class MainScreen : Fragment() {

    private lateinit var binding: MainFragmentBinding
    private val viewModel: MainViewModel by activityViewModels()
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var account: GoogleSignInAccount

    companion object {
        private const val RC_SIGN_IN = 100
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(layoutInflater,container,false)

        binding.categoryList.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as String
            findNavController().navigate(MainScreenDirections.actionArticleScreen(selectedItem))
        })
        binding.btnFavorites.setOnClickListener( View.OnClickListener {
            findNavController().navigate(MainScreenDirections.actionFavorite())
            viewModel.getStarredNewsFromDataBase()
        })

        binding.btnSignIn.apply {
            setSize(SignInButton.SIZE_STANDARD)
            setOnClickListener { signIn() }
        }
        viewModel.listOfCategories.observe(viewLifecycleOwner, Observer {
            val adapter = ArrayAdapter(requireContext(), R.layout.simple_list_item_1, it)
            binding.categoryList.adapter = adapter
        })

        viewModel.logged.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                binding.btnSignIn.apply{
                    alpha = 0.5f
                    isClickable = false
                }
                binding.btnFavorites.apply {
                    alpha = 1f
                    isClickable = true
                }
                binding.tvHowToAbleFavorites.alpha = 0.5f
            }
            else{
                binding.btnSignIn.apply{
                    alpha = 1f
                    isClickable = true
                }
                binding.btnFavorites.apply {
                    alpha = 0.2f
                    isClickable = false
                }
            }
        })
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this.requireActivity(), gso)

        return binding.root
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent;
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if (task.isSuccessful){
                try {
                    account = task.getResult(ApiException::class.java)
                    viewModel.loggedByGoogle()
                } catch (e: ApiException) {
                    Log.w("Login", "signInResult:failed code=" + e.statusCode)
                }
            } else {
                Log.w("Login", exception.toString())
            }
        }
    }
}
