package com.example.rickandmortyrxjava3

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.rickandmortyrxjava3.api.ApiService
import com.example.rickandmortyrxjava3.database.AppDatabase
import com.example.rickandmortyrxjava3.di.App
import com.example.rickandmortyrxjava3.pojo.PojoResult
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class CharacterViewModel (application: Application) : AndroidViewModel(application) {
    private val compositeDisposable = CompositeDisposable()
    fun allCharacter(): LiveData<List<PojoResult>> =AppDatabase.getInstance(getApplication()).characterDao().getAllCharacter()
    init {
        (application as App).appComponent. inject(this)
    }

    @Inject
    fun loadData(db: AppDatabase, apiService: ApiService) {
        val disposable = apiService.apiGetCharacterFromPage(1)
            .map { it.results }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.newThread())
            .retry()
            .subscribe({
                Log.d("TEST", it.toString())
                db.characterDao().insertAllCharacter(it)
            }, {
            })
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
    }


}