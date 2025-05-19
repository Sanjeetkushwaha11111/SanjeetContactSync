package com.example.contactsync_sanjeet.data

data class UserContact(
    val id: Long,
    val displayName: String,
    val phoneNumber: String?
)

data class UiState(
    val contacts: List<UserContact> = emptyList(),
    val isSyncing: Boolean = false,
    val message: String? = null
)