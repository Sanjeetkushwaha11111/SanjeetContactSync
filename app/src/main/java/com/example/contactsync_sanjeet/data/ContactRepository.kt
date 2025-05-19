package com.example.contactsync_sanjeet.data

import com.example.contactsync_sanjeet.base.ApiService

class ContactRepository(private val apiService: ApiService) {

    suspend fun fetchContacts(): Result<ContactResponse> {
        return try {
            val response = apiService.getContacts()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("API error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
