package com.vilo.muradyan.task.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertResults(results: com.vilo.muradyan.task.data.model.Results)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllResults(results: List<com.vilo.muradyan.task.data.model.Results>)

    @Query("SELECT * FROM results WHERE id = :id")
    fun resultById(id: Long): Single<com.vilo.muradyan.task.data.model.Results>

    @Query("SELECT * FROM results")
    fun loadAllResults(): Single<List<com.vilo.muradyan.task.data.model.Results>>

    @Query("SELECT * FROM results ORDER BY release_date limit :limit offset :offset")
    fun loadResults(limit: Int, offset: Int): Single<List<com.vilo.muradyan.task.data.model.Results>>

    @Query("Update  results SET adult= :isSave WHERE  id= :id")
    fun isSaveResult(id:String, isSave: Boolean)

    @Query("SELECT * FROM results WHERE adult = :isSaved  ORDER BY release_date limit :limit offset :offset")
    fun allResultsSaved(limit: Int, offset: Int , isSaved:Boolean): Single<List<com.vilo.muradyan.task.data.model.Results>>

    @Query("SELECT * FROM results ORDER BY release_date DESC limit :limit")
    fun lastResultByDate(limit: Int): Single<com.vilo.muradyan.task.data.model.Results>
}