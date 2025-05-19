package com.example.contactsync_sanjeet.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsync_sanjeet.data.UserContact
import com.example.contactsync_sanjeet.databinding.UserContactsItemBinding

class ContactsListAdapter(
    private val onItemClick: (UserContact) -> Unit,
) : ListAdapter<UserContact, ContactsListAdapter.ContactViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding =
            UserContactsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = getItem(position)
        holder.bind(contact)
    }

    inner class ContactViewHolder(private val binding: UserContactsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: UserContact) {
            binding.apply {
                name.text = contact.firstName
                root.setOnClickListener { onItemClick(contact) }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<UserContact>() {
        override fun areItemsTheSame(oldItem: UserContact, newItem: UserContact): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserContact, newItem: UserContact): Boolean {
            return oldItem == newItem
        }
    }
}
