package com.example.dagger2.di.module

import com.example.dagger2.ui.user.UserActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract fun contributesUserActivity(): UserActivity

}