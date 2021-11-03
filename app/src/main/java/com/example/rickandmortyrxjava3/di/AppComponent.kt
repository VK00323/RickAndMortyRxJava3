package com.example.rickandmortyrxjava3.di

import com.example.rickandmortyrxjava3.CharacterViewModel
import com.example.rickandmortyrxjava3.MainActivity
import com.example.rickandmortyrxjava3.api.ApiFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ApiFactory::class ])
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(viewModel: CharacterViewModel)
}