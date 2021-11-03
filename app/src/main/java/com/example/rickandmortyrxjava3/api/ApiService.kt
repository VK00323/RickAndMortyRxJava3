package com.example.rickandmortyrxjava3.api

import com.example.rickandmortyrxjava3.pojo.PojoExample
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface ApiService {

    @GET("character")
    fun apiGetCharacterFromPage(
        @retrofit2.http.Query("page") page: Int
    ): Single<PojoExample>
}
