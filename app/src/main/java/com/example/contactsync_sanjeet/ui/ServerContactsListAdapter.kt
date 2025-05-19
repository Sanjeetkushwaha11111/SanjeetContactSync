package com.example.contactsync_sanjeet.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsync_sanjeet.data.User
import com.example.contactsync_sanjeet.databinding.ServerContactItemBinding
import timber.log.Timber


class ServerContactsListAdapter(
    private val onItemClick: (User) -> Unit,
) : ListAdapter<User, ServerContactsListAdapter.ContactViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding =
            ServerContactItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding)
    }
    override fun submitList(list: List<User>?) {
        Timber.e(">>>>>>>>>>>>>>Adapter received list: $list")
        super.submitList(list)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = getItem(position)
        holder.bind(contact)
    }

    inner class ContactViewHolder(private val binding: ServerContactItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: User) {
            Timber.e(">>>>>>>>>>. $contact")
            binding.apply {
                name.text = contact.fullName
                occupation.text = contact.course
                number.text = contact.phone
                email.text = contact.email
                root.setOnClickListener { onItemClick(contact) }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }
}
