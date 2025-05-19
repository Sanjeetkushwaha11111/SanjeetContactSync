package com.example.contactsync_sanjeet.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ContactsViewModel : ViewModel() {

    val hasPermissions = MutableLiveData(false)

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    fun onPermissionsGranted() = hasPermissions.postValue(true)

    fun onPermissionsDenied() {
        hasPermissions.postValue(false)
        _uiState.update {
            it.copy(message = "Permission denied. Enable Contacts permission.")
        }
    }

    data class UiState(
        val contacts: List<UserContact> = emptyList(),
        val isSyncing: Boolean = false,
        val message: String? = null
    )
}
