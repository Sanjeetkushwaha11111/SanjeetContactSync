package com.example.contactsync_sanjeet.data

import android.content.ContentResolver
import android.content.Context
import android.provider.ContactsContract
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val getContactsUseCase: GetContactsUseCase,
    @ApplicationContext private val appContext: Context
) : ViewModel() {
    val hasPermissions = MutableLiveData(false)

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    private val _contacts = MutableStateFlow<Result<ContactResponse>?>(null)
    val contacts: StateFlow<Result<ContactResponse>?> = _contacts

    fun onPermissionsGranted() {
        hasPermissions.postValue(true)
        viewModelScope.launch {
            val contacts = fetchSimpleContacts(appContext.contentResolver)
            _uiState.update { it.copy(contacts = contacts) }
        }
    }

    fun onPermissionsDenied() {
        hasPermissions.postValue(false)
        _uiState.update {
            it.copy(message = "Permission denied. Enable Contacts permission.")
        }
    }

    suspend fun fetchSimpleContacts(contentResolver: ContentResolver): List<UserContact> =
        withContext(Dispatchers.IO) {
            val contacts = mutableListOf<UserContact>()
            val projection = arrayOf(
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
            )

            val cursor = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection,
                null,
                null,
                "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} ASC"
            )

            cursor?.use {
                val idIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
                val nameIndex =
                    it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                val numberIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                val photoUriIndex =
                    it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)

                val seenIds =
                    mutableSetOf<Long>()

                while (it.moveToNext()) {
                    val id = it.getLong(idIndex)
                    if (id in seenIds) continue

                    val name = it.getString(nameIndex) ?: "Unknown"
                    val number = it.getString(numberIndex)
                    contacts.add(
                        UserContact(
                            id = id,
                            displayName = name,
                            phoneNumber = number
                        )
                    )
                    seenIds.add(id)
                }
            }
            contacts
        }

    private fun generateInitials(name: String): String {
        val parts = name.trim().split("\\s+".toRegex())
        return when {
            parts.size >= 2 -> "${
                parts[0].firstOrNull()?.uppercaseChar() ?: ""
            }${parts[1].firstOrNull()?.uppercaseChar() ?: ""}"

            parts.size == 1 -> parts[0].firstOrNull()?.uppercaseChar()?.toString() ?: ""
            else -> ""
        }
    }

    fun loadContacts() {
        viewModelScope.launch(Dispatchers.IO) {
            _contacts.value = getContactsUseCase()
        }
    }

}
