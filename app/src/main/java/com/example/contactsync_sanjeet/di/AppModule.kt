package com.example.contactsync_sanjeet.di

import com.example.contactsync_sanjeet.base.ApiService
import com.example.contactsync_sanjeet.base.RetrofitClient
import com.example.contactsync_sanjeet.data.ContactRepository
import com.example.contactsync_sanjeet.data.GetContactsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideApiService(): ApiService = RetrofitClient.api

    @Provides
    fun provideContactRepository(apiService: ApiService): ContactRepository =
        ContactRepository(apiService)

    @Provides
    fun provideGetContactsUseCase(repository: ContactRepository): GetContactsUseCase =
        GetContactsUseCase(repository)
}
