package com.example.chatroomfire

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatroomfire.databinding.FragmentUsersListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersListFragment : Fragment(R.layout.fragment_users_list) {

    private val userModel: UsersListViewModel by viewModels()

    private lateinit var binding: FragmentUsersListBinding
    private var usersList: ArrayList<UserDataClass> = ArrayList()
    private lateinit var userAdapter: UsersListAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUsersListBinding.bind(view)
        binding.usersRcv.layoutManager = LinearLayoutManager(requireContext())
        userAdapter = UsersListAdapter(requireContext(), usersList)
        binding.usersRcv.adapter = userAdapter
        userModel.user.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    usersList.addAll(it.data ?: arrayListOf())
                    userAdapter.notifyDataSetChanged()
                    binding.progressBar.isVisible = false
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT).show()
                    binding.progressBar.isVisible = false
                }
                is Resource.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }

        }
    }


}