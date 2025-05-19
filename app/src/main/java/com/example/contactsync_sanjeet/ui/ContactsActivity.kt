package com.example.contactsync_sanjeet.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.contactsync_sanjeet.data.ContactsViewModel
import com.example.contactsync_sanjeet.databinding.ActivityContactsBinding
import com.example.contactsync_sanjeet.utils.PermissionHelper

class ContactsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactsBinding
    val viewModel by lazy { ContactsViewModel(this) }

    private val permLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { grants ->
        if (grants.values.all { it }) viewModel.onPermissionsGranted()
        else viewModel.onPermissionsDenied()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        PermissionHelper.ensureContactPermissions(this, permLauncher) {
            viewModel.onPermissionsGranted()
        }
    }

    fun requestPermissions() {
        PermissionHelper.ensureContactPermissions(this, permLauncher) {
            viewModel.onPermissionsGranted()
        }
    }

}
