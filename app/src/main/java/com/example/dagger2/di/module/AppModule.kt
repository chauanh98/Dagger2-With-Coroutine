package com.example.dagger2.di.module

import android.app.Application
import android.content.Context
import com.example.dagger2.UserApplication
import com.example.dagger2.data.UserRepository
import com.example.dagger2.data.local.dao.UserDao
import com.example.dagger2.data.local.UserDatabase
import com.example.dagger2.data.remote.UserRemoteDataSource
import com.example.dagger2.data.remote.UserService
import com.example.dagger2.ui.detail.DetailFragment
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(app: UserApplication): Context = app

    @Provides
    @Singleton
    fun provideApplication(app: UserApplication): Application = app

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)

    @Provides
    @Singleton
    fun provideRoomDB(app: Application) = UserDatabase.getInstance(app)

    @Provides
    @Singleton
    fun provideUserDao(db: UserDatabase) = db.userDao()

    @Provides
    @Singleton
    @Named("IO")
    fun provideBackgroundDispatchers(): CoroutineDispatcher =
        Dispatchers.IO

    @Provides
    @Singleton
    @Named("MAIN")
    fun provideMainDispatchers(): CoroutineDispatcher =
        Dispatchers.Main

    @Provides
    @Singleton
    fun provideDetailFragment(): DetailFragment = DetailFragment()

    @Provides
    @Singleton
    fun provideRemoteDataSource(apiService: UserService) = UserRemoteDataSource(apiService)

    @Provides
    @Singleton
    fun provideUserRepository(dao: UserDao, remote: UserRemoteDataSource) =
        UserRepository(dao, remote)
}