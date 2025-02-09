package com.fitness.data.di

import com.fitness.data.auth.AccountService
import com.fitness.data.auth.AccountServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAccountServiceRepository(
        accountServiceImpl: AccountServiceImpl
    ): AccountService

}