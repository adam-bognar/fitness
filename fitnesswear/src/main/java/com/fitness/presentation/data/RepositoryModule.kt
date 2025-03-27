package com.fitness.presentation.data

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideSendDataRepository(
        @ApplicationContext context: Context
    ): ISendDataRepository {
        return SendDataRepository(context)
    }




}