package com.example.contactsync_sanjeet.ui

import android.icu.lang.UCharacter.DecompositionType.CIRCLE
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.contactsync_sanjeet.data.UserContact
import com.example.contactsync_sanjeet.databinding.UserContactsItemBinding
import com.example.contactsync_sanjeet.utils.AvatarGenerator

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
                if (contact.displayName.isNotEmpty()) {

                    name.text = contact.displayName

                    Glide.with(binding.userImage.context).load("image_url")
                        .diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(
                            AvatarGenerator.avatarImage(
                                binding.userImage.context,
                                200,
                                CIRCLE,
                                getFirstAlphabeticCharacter(contact.displayName)
                            )
                        ).apply(RequestOptions.circleCropTransform()).into(binding.userImage)
                }
                root.setOnClickListener { onItemClick(contact) }
            }
        }
    }

    private fun getFirstAlphabeticCharacter(name: String?): String {
        return name?.firstOrNull { it.isLetter() }?.toString() ?: "#"
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
