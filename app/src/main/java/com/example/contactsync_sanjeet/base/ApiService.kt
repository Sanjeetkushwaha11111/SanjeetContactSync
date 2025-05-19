package com.example.contactsync_sanjeet.base

import com.example.contactsync_sanjeet.data.ContactResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("api/contacts")
    suspend fun getContacts(): Response<ContactResponse>
}
