package com.example.dagger2.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dagger2.di.Factory.ViewModelFactory
import com.example.dagger2.di.ViewModelKey
import com.example.dagger2.ui.detail.DetailViewModel
import com.example.dagger2.ui.user.UserViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel::class)
    internal abstract fun providesUserViewModel(viewModel: UserViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    internal abstract fun providesDetailViewModel(viewModel: DetailViewModel): ViewModel
}