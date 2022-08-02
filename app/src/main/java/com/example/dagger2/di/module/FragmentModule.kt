package com.example.dagger2.di.module

import com.example.dagger2.ui.detail.DetailFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributesDetailFragment(): DetailFragment
}