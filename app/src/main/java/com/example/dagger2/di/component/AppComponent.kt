package com.example.dagger2.di.component

import com.example.dagger2.UserApplication
import com.example.dagger2.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ActivityBuilder::class,
        AppModule::class,
        NetworkModule::class,
        ViewModelModule::class,
        AndroidSupportInjectionModule::class,
        FragmentModule::class
    ]
)

interface AppComponent : AndroidInjector<UserApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: UserApplication): Builder

        fun build(): AppComponent
    }

}