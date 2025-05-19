package com.example.contactsync_sanjeet.data

import com.example.contactsync_sanjeet.base.ApiService
import timber.log.Timber

class ContactRepository(private val apiService: ApiService) {

    suspend fun fetchContacts(): Result<ContactResponse> {
        Timber.e(">>>>>>>>>>>>>>>>4")
        return try {
            val response = apiService.getContacts()
            if (response.isSuccessful && response.body() != null) {
                Timber.e(">>>>>>>>>>>>>>>>5")
                Result.success(response.body()!!)
            } else {
                Timber.e(">>>>>>>>>>>>>>>>6")
                Result.failure(Exception("API error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Timber.e(">>>>>>>>>>>>>>>>7")
            Result.failure(e)
        }
    }
}
