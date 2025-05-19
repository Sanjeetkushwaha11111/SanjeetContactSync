package com.example.contactsync_sanjeet.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.contactsync_sanjeet.R
import com.example.contactsync_sanjeet.data.ContactsViewModel
import com.example.contactsync_sanjeet.databinding.FragmentUserContactsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.getValue

@AndroidEntryPoint
class UserContactsFragment : Fragment(R.layout.fragment_user_contacts) {

    private var _binding: FragmentUserContactsBinding? = null
    private val binding get() = _binding!!
    val viewModel: ContactsViewModel by viewModels()



    private val adapter = ContactsListAdapter { }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentUserContactsBinding.bind(view)

        binding.rvUserContacts.apply {
            adapter = this@UserContactsFragment.adapter
            setHasFixedSize(true)
            addItemDecoration(
                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            )
        }

        binding.searchBar.setOnClickListener {
            viewModel.loadContacts()
        }

        lifecycleScope.launchWhenStarted {
            viewModel.contacts.collect { result ->
                when {
                    result == null -> Unit
                    result.isSuccess -> {
                        val data = result.getOrNull()
                        Timber.e(">>>>>>>>>>>>>>>>Contacts: $data")
                    }
                    result.isFailure -> {
                        val error = result.exceptionOrNull()
                        // Show error
                    }
                }
            }
        }

        viewModel.hasPermissions.observe(viewLifecycleOwner) { updateUi(it) }

        binding.contactsText.setOnClickListener {
            if (viewModel.hasPermissions.value == false) (requireActivity() as ContactsActivity).requestPermissions()
        }

        observeUiState()
    }

    private fun observeUiState() = lifecycleScope.launch {
        viewModel.uiState.collect { state ->
            adapter.submitList(state.contacts)
            state.message?.let { binding.contactsText.text = it }
        }
    }

    private fun updateUi(granted: Boolean) = with(binding) {
        noPermissionLl.isVisible = !granted
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
