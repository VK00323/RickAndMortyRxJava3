package com.example.rickandmortyrxjava3.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickandmortyrxjava3.pojo.PojoResult
import io.reactivex.rxjava3.core.Observable

@Dao
interface CharacterDao {
    @Query("SELECT * FROM character_table")
    fun getAllCharacter(): Observable<List<PojoResult>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCharacter(list : List<PojoResult>)
}