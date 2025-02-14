package com.fitness.data.di

import com.fitness.data.auth.AccountService
import com.fitness.data.auth.AccountServiceImpl
import com.fitness.data.repository.exercise.GlobalExerciseRepositoryImpl
import com.fitness.data.repository.exercise.IGlobalExerciseRepository
import com.fitness.data.repository.exercise.IUserExerciseRepository
import com.fitness.data.repository.exercise.UserExerciseRepositoryImpl
import com.fitness.data.repository.routine.IRoutineRepository
import com.fitness.data.repository.routine.RoutinesRepositoryImpl
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

    @Binds
    @Singleton
    abstract fun bindRoutineRepository(
        routineRepositoryImpl: RoutinesRepositoryImpl
    ): IRoutineRepository

    @Binds
    @Singleton
    abstract fun bindUserExerciseRepository(
        userExerciseRepositoryImpl: UserExerciseRepositoryImpl
    ): IUserExerciseRepository

    @Binds
    @Singleton
    abstract fun bindGlobalExerciseRepository(
        globalExerciseRepositoryImpl: GlobalExerciseRepositoryImpl
    ): IGlobalExerciseRepository

}