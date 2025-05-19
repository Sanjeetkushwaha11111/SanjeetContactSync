package com.example.contactsync_sanjeet.data

data class ContactResponse(
    val success: Boolean,
    val Data: ContactData
)

data class ContactData(
    val date: String,
    val totalUsers: Int,
    val users: List<User>
)

data class User(
    val id: String,
    val fullName: String,
    val phone: String,
    val email: String,
    val course: String,
    val enrolledOn: String
)
