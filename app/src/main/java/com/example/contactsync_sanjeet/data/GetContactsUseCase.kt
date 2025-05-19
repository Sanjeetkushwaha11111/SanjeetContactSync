package com.example.contactsync_sanjeet.data

import timber.log.Timber

class GetContactsUseCase(private val repository: ContactRepository) {
    suspend operator fun invoke(): Result<ContactResponse> {
        Timber.e(">>>>>>>>>>>>>>>>3")
        return repository.fetchContacts()
    }
}
