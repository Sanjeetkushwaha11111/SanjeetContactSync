package com.example.contactsync_sanjeet.data

class GetContactsUseCase(private val repository: ContactRepository) {
    suspend operator fun invoke(): Result<ContactResponse> {
        return repository.fetchContacts()
    }
}
