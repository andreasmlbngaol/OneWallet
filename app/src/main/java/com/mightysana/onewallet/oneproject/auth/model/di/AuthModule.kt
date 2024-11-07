package com.mightysana.onewallet.oneproject.auth.model.di

import com.mightysana.onewallet.oneproject.auth.model.service.AccountService
import com.mightysana.onewallet.oneproject.auth.model.service.AccountServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AccountModule {
    @Provides
    fun provideAccountService(): AccountService {
        return AccountServiceImpl()
    }
}