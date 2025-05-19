package com.example.contactsync_sanjeet.data

data class UserContact(
    val id: Long,
    val firstName: String,
    val surName: String,
    val company: String,
    val phoneNumber: String?,
    val photoUri: String?,
    val createdTime: Long,
    val source: ContactSource
)

enum class ContactSource {
    SERVER,
    MANUAL
}