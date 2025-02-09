package com.fitness

import android.app.Application
import com.fitness.data.auth.AccountService
import com.fitness.data.auth.AccountServiceImpl
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FitnessApplication : Application()