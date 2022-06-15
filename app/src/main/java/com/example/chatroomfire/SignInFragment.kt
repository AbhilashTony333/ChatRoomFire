package com.example.chatroomfire

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.chatroomfire.databinding.FragmentSignInBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    @Inject
    lateinit var auth : FirebaseAuth

    private lateinit var signInBinding: FragmentSignInBinding

    private lateinit var navController: NavController

    private val signInViewModel: SignInViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signInBinding = FragmentSignInBinding.bind(view)
        navController = findNavController()
        onClickListeners()
    }


    private fun onClickListeners() {
        signInBinding.signUpPage.setOnClickListener {
            val action = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
            navController.navigate(action)
        }
        signInBinding.signInBtn.setOnClickListener {
            val email = signInBinding.email.text.toString()
            val password = signInBinding.password.text.toString()
            signInViewModel.signInAccount(email, password)
        }

        signInViewModel.result.observe(viewLifecycleOwner) {
            if (it == "Success") {
                Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
                val action = SignInFragmentDirections.actionSignInFragmentToUsersListFragment()
                navController.navigate(action)
            } else {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {

        if (auth.currentUser?.uid!=null){
            val action = SignInFragmentDirections.actionSignInFragmentToUsersListFragment()
            navController.navigate(action)
        }
        super.onStart()
    }
}