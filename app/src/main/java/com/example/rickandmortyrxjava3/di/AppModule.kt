package com.example.rickandmortyrxjava3.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private  var application: Application) {

    @Singleton
    @Provides
    fun provideAppContext(): Context {
        return application.applicationContext
    }

}