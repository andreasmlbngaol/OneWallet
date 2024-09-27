package com.mightysana.onewallet.di

import com.mightysana.onewallet.model.service.AccountService
import com.mightysana.onewallet.model.service.impl.AccountServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AccountModule {
    @Provides
    fun provideAccountService(): AccountService {
        return AccountServiceImpl() // Or however you create an instance
    }

}