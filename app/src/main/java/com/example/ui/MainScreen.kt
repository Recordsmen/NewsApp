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

import com.google.android.gms.tasks.Task
import android.util.Log
import androidx.lifecycle.Observer
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.SignInButton

import com.google.android.gms.common.api.ApiException







class MainScreen : Fragment() {

    private lateinit var binding: MainFragmentBinding
    private val viewModel: MainViewModel by activityViewModels()
    lateinit var mGoogleSignInClient: GoogleSignInClient
    companion object {
        private const val RC_SIGN_IN = 100
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(layoutInflater,container,false)

        // in viewModel
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

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this.requireActivity(), gso)

        binding.btnSignIn.setSize(SignInButton.SIZE_STANDARD)
        binding.btnSignIn.setOnClickListener{
            signIn()
        }
        viewModel.logged.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                binding.btnSignIn.visibility = View.GONE
                binding.btnFavorites.visibility = View.VISIBLE
            }
            else{
                binding.btnSignIn.visibility = View.VISIBLE
                binding.btnFavorites.visibility = View.GONE
            }
        })

        return binding.root
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent;
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if (task.isSuccessful){
                try {
                    val account = task.getResult(ApiException::class.java)
                    Log.i("Loggin",account.displayName.toString())
                    viewModel.loggedByGoogle()
                } catch (e: ApiException) {
                    // The ApiException status code indicates the detailed failure reason.
                    // Please refer to the GoogleSignInStatusCodes class reference for more information.
                    Log.w("Loggin", "signInResult:failed code=" + e.statusCode)
                }
            } else {
                Log.w("Loggin", exception.toString())
            }
//            handleSignInResult(task)
        }
    }
//    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
//        try {
//            val account = completedTask.getResult(ApiException::class.java)
//            Log.i("Loggin",account.displayName.toString())
//        } catch (e: ApiException) {
//            // The ApiException status code indicates the detailed failure reason.
//            // Please refer to the GoogleSignInStatusCodes class reference for more information.
//            Log.w("Loggin", "signInResult:failed code=" + e.statusCode)
//        }
//    }
}
