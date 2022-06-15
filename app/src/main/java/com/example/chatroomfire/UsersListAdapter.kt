package com.example.chatroomfire

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatroomfire.databinding.UsersItemBinding

class UsersListAdapter(
    private val context: Context,
    private val UserList: ArrayList<UserDataClass>
) : RecyclerView.Adapter<UsersListAdapter.UsersListViewHolder>() {
    class UsersListViewHolder(val binding: UsersItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersListViewHolder {
        val binding = UsersItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return UsersListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersListViewHolder, position: Int) {
        holder.binding.name.text = UserList[position].name
        holder.binding.email.text = UserList[position].email
    }

    override fun getItemCount(): Int {
        return UserList.size
    }
}