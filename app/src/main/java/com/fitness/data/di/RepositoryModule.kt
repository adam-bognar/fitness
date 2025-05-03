package com.fitness.data.di

import com.fitness.data.auth.AccountService
import com.fitness.data.auth.AccountServiceImpl
import com.fitness.data.repository.exercise.GlobalExerciseRepositoryImpl
import com.fitness.data.repository.exercise.IGlobalExerciseRepository
import com.fitness.data.repository.exercise.IUserExerciseRepository
import com.fitness.data.repository.exercise.UserExerciseRepositoryImpl
import com.fitness.data.repository.macros.IMacrosRepository
import com.fitness.data.repository.macros.MacrosRepositoryImpl
import com.fitness.data.repository.routine.IRoutineRepository
import com.fitness.data.repository.routine.RoutinesRepositoryImpl
import com.fitness.data.repository.session.ISessionRepository
import com.fitness.data.repository.session.SessionRepositoryImpl
import com.fitness.data.repository.user_information.IUserInformationRepository
import com.fitness.data.repository.user_information.UserInfoRepoImpl
import com.fitness.data.running.IRunningRepository
import com.fitness.data.running.RunningRepositoryImpl
import com.fitness.data.steps.IStepTrackingRepository
import com.fitness.data.steps.StepTrackingRepository
import com.fitness.data.streak.IStreakRepository
import com.fitness.data.streak.StreakRepositoryImpl
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
    abstract fun bindStreakRepository(
        streakRepositoryImpl: StreakRepositoryImpl
    ): IStreakRepository

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

    @Binds
    @Singleton
    abstract fun bindMacrosRepository(
        macrosRepositoryImpl: MacrosRepositoryImpl
    ): IMacrosRepository

    @Binds
    @Singleton
    abstract fun bindUserInformationRepository(
        userInformationRepositoryImpl: UserInfoRepoImpl
    ): IUserInformationRepository

    @Binds
    @Singleton
    abstract fun bindStepTrackingRepository(
        stepTrackingRepositoryImpl: StepTrackingRepository
    ): IStepTrackingRepository

    @Binds
    @Singleton
    abstract fun bindRunningRepository(
        runningRepositoryImpl: RunningRepositoryImpl
    ): IRunningRepository

    @Binds
    @Singleton
    abstract fun bindSessionRepository(
        sessionRepositoryImpl: SessionRepositoryImpl
    ): ISessionRepository


}