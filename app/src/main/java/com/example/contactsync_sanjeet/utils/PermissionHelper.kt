package com.example.contactsync_sanjeet.utils

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

object PermissionHelper {

    private val CONTACT_PERMS = arrayOf(
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.WRITE_CONTACTS
    )

    fun hasContactsPerms(ctx: Context): Boolean = CONTACT_PERMS.all {
        ActivityCompat.checkSelfPermission(ctx, it) ==
                android.content.pm.PackageManager.PERMISSION_GRANTED
    }

    fun ensureContactPermissions(
        activity: AppCompatActivity,
        launcher: ActivityResultLauncher<Array<String>>,
        onGranted: () -> Unit
    ) {
        when {
            hasContactsPerms(activity) -> onGranted()
            shouldShowRationale(activity) -> showRationaleDialog(activity) {
                launcher.launch(CONTACT_PERMS)
            }
            else -> launcher.launch(CONTACT_PERMS)
        }
    }

    fun openAppSettings(activity: AppCompatActivity) {
        val uri = Uri.fromParts("package", activity.packageName, null)
        activity.startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri))
    }

    private fun shouldShowRationale(a: AppCompatActivity) =
        CONTACT_PERMS.any { ActivityCompat.shouldShowRequestPermissionRationale(a, it) }

    private fun showRationaleDialog(a: AppCompatActivity, ok: () -> Unit) {
        AlertDialog.Builder(a)
            .setTitle("Contacts permission required")
            .setMessage("Grant Contacts permission to import and edit users.")
            .setPositiveButton("Allow") { _, _ -> ok() }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
