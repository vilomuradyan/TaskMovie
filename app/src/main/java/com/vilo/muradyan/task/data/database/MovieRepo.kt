package com.vilo.muradyan.task.data.database

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface MovieRepo {

    fun isSaveResult(id:String, isSave:Boolean):Completable

    fun allResults(limit: Int, offset: Int): Observable<List<com.vilo.muradyan.task.data.model.Results>>

    fun periodResults(): Single<List<com.vilo.muradyan.task.data.model.Results>>

    fun allResultSaved(limit: Int, offset: Int, isSaved:Boolean): Single<List<com.vilo.muradyan.task.data.model.Results>>
}