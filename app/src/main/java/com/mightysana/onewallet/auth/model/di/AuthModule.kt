package com.mightysana.onewallet.auth.model.di

import com.mightysana.onewallet.auth.model.AuthService
import com.mightysana.onewallet.auth.model.impl.AuthServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AccountModule {
    @Provides
    fun provideAccountService(): AuthService {
        return AuthServiceImpl() // Or however you create an instance
    }

}