package com.example.rickandmortyrxjava3

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.rickandmortyrxjava3.api.ApiService
import com.example.rickandmortyrxjava3.database.AppDatabase
import com.example.rickandmortyrxjava3.database.CharacterDao
import com.example.rickandmortyrxjava3.di.App
import com.example.rickandmortyrxjava3.pojo.PojoResult
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class CharacterViewModel(application: Application) : AndroidViewModel(application) {
    private val compositeDisposable = CompositeDisposable()
    @Inject lateinit var db: CharacterDao
    @Inject lateinit var apiService:ApiService

    fun allCharacter(): Observable<List<PojoResult>> = db.getAllCharacter()

    init {
        (application as App).appComponent.inject(this)
    }

    @Inject
    fun loadData() {
        val disposable = apiService.apiGetCharacterFromPage(PAGE)
            .map { it.results }
            .subscribeOn(Schedulers.io())
            .retry()
            .subscribe({
                db.insertAllCharacter(it)
            },{
            })

        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
    }
    companion object{
        const val PAGE = 1
    }
}