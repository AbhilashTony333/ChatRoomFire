package com.example.chatroomfire

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.chatroomfire.databinding.FragmentMyAccountBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyAccountFragment : Fragment(R.layout.fragment_my_account) {

    private lateinit var binding: FragmentMyAccountBinding

    private val myAccountViewModel : MyAccountViewModel by viewModels()

    @Inject
    lateinit var fire:FirebaseAuth

    @Inject
    lateinit var fireDeb : DatabaseReference



    private var profileData : UserDataClass = UserDataClass()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMyAccountBinding.bind(view)
        setUI()
        myAccountViewModel.actDetails.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading ->{
                    binding.myaccountProgress.isVisible = true
                }
                is Resource.Success ->{
                    binding.nameBind.text = it.data?.name.toString()
                    binding.emailBind.text = it.data?.email.toString()
                    binding.passwordBind.text = it.data?.password.toString()
                    binding.myaccountProgress.isVisible = false
                }
                is Resource.Error -> {
                    binding.nameBind.text = it.message.toString()
                    binding.myaccountProgress.isVisible=false
                }
            }
        }
    }

    private fun setUI() {
        binding.nameBind.text = profileData.name.toString()
        binding.emailBind.text = profileData.email.toString()
        binding.passwordBind.text= profileData.password.toString()
    }


    override fun onStart() {

        super.onStart()
        if (fire.currentUser?.uid != null){
            myAccountViewModel.accountUid(fire.currentUser?.uid!!)
            binding.signedOut.isVisible = false
            binding.nameTv.isVisible = true
            binding.emailTv.isVisible = true
            binding.passwordTV.isVisible = true
            binding.nameBind.isVisible = true
            binding.emailBind.isVisible = true
            binding.passwordBind.isVisible = true

        }else{
            binding.signedOut.isVisible = true
            binding.nameTv.isVisible = false
            binding.emailTv.isVisible = false
            binding.passwordTV.isVisible = false
            binding.nameBind.isVisible = false
            binding.emailBind.isVisible = false
            binding.passwordBind.isVisible = false
        }

    }

}