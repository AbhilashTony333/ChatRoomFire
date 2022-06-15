package com.example.chatroomfire

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.chatroomfire.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {


    private val signUpModel: SignUpViewModel by viewModels()

    private lateinit var signUpBinding: FragmentSignUpBinding
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signUpBinding = FragmentSignUpBinding.bind(view)
        navController = findNavController()
        listeners()
        observers()
        signUpBinding.signUpBtn.setOnClickListener {
            validateFields()
        }
    }

    private fun observers() {
        signUpModel.res.observe(viewLifecycleOwner) {
            if (it == "Success") {
                Toast.makeText(requireContext(),"New Account Created Successfully",Toast.LENGTH_SHORT).show()
                val action = SignUpFragmentDirections.actionSignUpFragmentToUsersListFragment()
                navController.navigate(action)
            } else {
                Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateFields() {
        if (signUpBinding.tlOne.helperText.isNullOrEmpty() && signUpBinding.tlTwo.helperText.isNullOrEmpty() && signUpBinding.tlThree.helperText.isNullOrEmpty() && signUpBinding.tlFour.helperText.isNullOrEmpty()) {
            resetForm()
        } else {
            errorForm()
        }
    }

    private fun errorForm() {
        var message = "\n\nName : " + signUpBinding.tlOne.helperText.toString()
        message += "\nEmail : " + signUpBinding.tlTwo.helperText.toString()
        message += "\nPassword : " + signUpBinding.tlThree.helperText.toString()
        message += "\nConfirm Password : " + signUpBinding.tlFour.helperText.toString()
        AlertDialog.Builder(requireContext())
            .setTitle("Invalid Details")
            .setMessage(message)
            .setPositiveButton("Okay") { _, _ ->
                Toast.makeText(
                    requireContext(),
                    "PLease Enter All Fields As Required",
                    Toast.LENGTH_SHORT
                ).show()
            }.show()
    }

    private fun resetForm() {
        val name = signUpBinding.name.text.toString()
        val email = signUpBinding.email.text.toString()
        val password = signUpBinding.password.text.toString()
        var message = "\n\nName : $name"
        message += "\nEmail : $email"
        message += "\nPassword : $password"

        AlertDialog.Builder(requireContext())
            .setTitle("Submit Form")
            .setMessage(message)
            .setNegativeButton("Cancel") { _, _ ->
            }
            .setPositiveButton("Okay") { _, _ ->
                signUpModel.createAccount(name, email, password)
                clearAllFields()
            }.show()
    }

    private fun clearAllFields() {
        signUpBinding.name.setText("")
        signUpBinding.email.setText("")
        signUpBinding.password.setText("")
        signUpBinding.confirmPassword.setText("")
    }


    private fun listeners() {

        signUpBinding.name.setOnFocusChangeListener { _, focus ->
            if (!focus) {
                signUpBinding.tlOne.helperText = checkName()
            }
        }
        signUpBinding.email.setOnFocusChangeListener { _, focus ->
            if (!focus) {
                signUpBinding.tlTwo.helperText = checkEmail()
            }
        }
        signUpBinding.password.setOnFocusChangeListener { _, focus ->
            if (!focus) {
                signUpBinding.tlThree.helperText = checkPassword()
            }
        }
        signUpBinding.confirmPassword.setOnFocusChangeListener { _, focus ->
            if (!focus) {
                signUpBinding.tlFour.helperText = checkConfirmPassword()
                signUpBinding.tlThree.helperText = checkConfirmPassword()
            }

        }
    }

    private fun checkConfirmPassword(): String? {
        val password = signUpBinding.password.text.toString()
        val confirmPassword = signUpBinding.confirmPassword.text.toString()
        if (confirmPassword != password) {
            return "Passwords Doesn't match"
        }
        return null
    }

    private fun checkPassword(): String? {
        val password = signUpBinding.password.text?.toString()
        if (password?.trim().isNullOrEmpty()) {
            return "Password cannot be Empty"
        }
        if (password?.length!! < 8) {
            return "Password should be greater than 8"
        }
        if (!password.matches(".*[A-Z].*".toRegex()) || !password.matches(".*[a-z].*".toRegex()) || !password.matches(
                ".*[!@#$%].*".toRegex()
            ) || !password.matches(".*[0-9].*".toRegex())
        ) {
            return "Should Contain [A-Z,a-z,0-9,!@#$%]"
        }
        return null
    }

    private fun checkEmail(): String? {
        if (signUpBinding.email.text?.trim().isNullOrEmpty() || !Patterns.EMAIL_ADDRESS.matcher(
                signUpBinding.email.text.toString()
            ).matches()
        ) {
            return "Email Cannot be Empty"
        }
        return null
    }

    private fun checkName(): String? {
        if (signUpBinding.name.text?.trim().isNullOrEmpty()) {
            return "Name Cannot be Empty"
        }
        return null
    }

}