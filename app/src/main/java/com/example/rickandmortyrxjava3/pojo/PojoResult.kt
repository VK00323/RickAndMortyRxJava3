package com.example.rickandmortyrxjava3.pojo

import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PojoResult(
@PrimaryKey
@SerializedName("id") @Expose
val id: Int,
@SerializedName("name") @Expose
val name: String?,
@SerializedName("status") @Expose
val status: String?,
@SerializedName("species") @Expose
val species: String?,
@SerializedName("type") @Expose
val type: String?,
@SerializedName("gender") @Expose
val gender: String?,
@SerializedName("image") @Expose
val image: String?,
@SerializedName("created") @Expose
val created: String?,
)