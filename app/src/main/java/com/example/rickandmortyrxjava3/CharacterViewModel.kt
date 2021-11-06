package com.example.rickandmortyrxjava3

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.rickandmortyrxjava3.api.ApiService
import com.example.rickandmortyrxjava3.database.AppDatabase
import com.example.rickandmortyrxjava3.di.App
import com.example.rickandmortyrxjava3.pojo.PojoResult
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class CharacterViewModel(application: Application) : AndroidViewModel(application) {
    private val compositeDisposable = CompositeDisposable()
    fun allCharacter(): Observable<List<PojoResult>> =
        AppDatabase.getInstance(getApplication()).characterDao().getAllCharacter()

    init {
        (application as App).appComponent.inject(this)
    }

    @Inject
    fun loadData(db: AppDatabase, apiService: ApiService) {
        val disposable = apiService.apiGetCharacterFromPage(1)
            .map { it.results }
            .subscribeOn(Schedulers.io())
            .retry()
            .subscribe({
                db.characterDao().insertAllCharacter(it)
            },{
            })

        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
    }
}