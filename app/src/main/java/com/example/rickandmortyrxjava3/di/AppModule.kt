package com.example.rickandmortyrxjava3.di

import android.app.Application
import android.content.Context
import com.example.rickandmortyrxjava3.database.AppDatabase
import com.example.rickandmortyrxjava3.database.CharacterDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private  var application: Application) {
    @Singleton
    @Provides
    fun provideCharacterDao(db: AppDatabase): CharacterDao {
        return db.characterDao()
    }

    @Singleton
    @Provides
    fun provideDatabaseInstance(): AppDatabase {
        return AppDatabase.getInstance(provideAppContext())
    }

    @Singleton
    @Provides
    fun provideAppContext(): Context {
        return application.applicationContext
    }
}