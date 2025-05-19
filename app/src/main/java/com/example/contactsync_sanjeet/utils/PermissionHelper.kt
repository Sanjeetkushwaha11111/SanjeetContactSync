package com.example.contactsync_sanjeet.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment

object PermissionHelper {

    private val CONTACT_PERMS = arrayOf(
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.WRITE_CONTACTS
    )
    fun Fragment.ensureContactPermissions(
        permLauncher: ActivityResultLauncher<Array<String>>,
        onGranted: () -> Unit
    ) {
        when {
            hasContactsPerms(requireContext()) -> onGranted()
            shouldShowRationale() -> showRationaleDialog {
                permLauncher.launch(CONTACT_PERMS)
            }
            else -> {
                permLauncher.launch(CONTACT_PERMS)
            }
        }
    }

    fun Fragment.onPermissionsResult(
        grants: Map<String, Boolean>,
        onGranted: () -> Unit
    ) {
        if (grants.values.all { it }) {
            onGranted()
        } else {
            if (!shouldShowRationale()) {
                showSettingsDialog()
            } else {
                // transient deny (snackbar / dialog optional)
            }
        }
    }


    private fun Fragment.shouldShowRationale(): Boolean =
        CONTACT_PERMS.any { ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), it) }

    private fun hasContactsPerms(ctx: Context): Boolean =
        CONTACT_PERMS.all { ActivityCompat.checkSelfPermission(ctx, it) == android.content.pm.PackageManager.PERMISSION_GRANTED }

    private fun Fragment.showRationaleDialog(onPositive: () -> Unit) {
        AlertDialog.Builder(requireContext())
            .setTitle("Contacts permission required")
            .setMessage("The app needs access to your contacts to import and edit training users.")
            .setPositiveButton("Allow") { _, _ -> onPositive() }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun Fragment.showSettingsDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Permission permanently denied")
            .setMessage("Enable the Contacts permission in Settings ▶︎ Apps ▶︎ ContactSync ▶︎ Permissions.")
            .setPositiveButton("Open Settings") { _, _ -> openAppSettings() }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun Fragment.openAppSettings() {
        val uri = Uri.fromParts("package", requireContext().packageName, null)
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri)
        startActivity(intent)
    }
}