package com.example.contactsync_sanjeet.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactsync_sanjeet.R
import com.example.contactsync_sanjeet.data.ContactsViewModel
import com.example.contactsync_sanjeet.databinding.FragmentAddContactFromServerBinding
import kotlinx.coroutines.launch
import timber.log.Timber

class AddContactFromServerFragment : Fragment(R.layout.fragment_add_contact_from_server) {


    private var _binding: FragmentAddContactFromServerBinding? = null
    private val binding get() = _binding!!
    val viewModel: ContactsViewModel by activityViewModels()
    private val adapter = ServerContactsListAdapter {
        Timber.e(">>>>>>>> contact clicked $it")
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = FragmentAddContactFromServerBinding.bind(view)
        binding.rvServerContacts.apply {
            setHasFixedSize(true)
            addItemDecoration(
                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            )
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@AddContactFromServerFragment.adapter // <-- Corrected line
        }

        viewModel.loadContacts()
        observeUi()
    }

    private fun observeUi() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.contacts.collect { result ->
                    when {
                        false -> Unit
                        result.isSuccess -> {
                            val data = result.getOrNull()
                            data?.let { contactResponse ->
                                contactResponse.Data.let {
                                    adapter.submitList(it.users)
                                }
                            }
                        }

                        result.isFailure -> {
                            result.exceptionOrNull()
                        }
                    }
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}