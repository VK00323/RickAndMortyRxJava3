package com.example.rickandmortyrxjava3.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PojoExample (
    @SerializedName("info") @Expose val info: PojoInfo,
    @SerializedName("results") @Expose val results:  List<PojoResult>
)