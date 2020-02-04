package com.vilo.muradyan.task.data.network

import com.vilo.muradyan.task.data.model.MovieResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("movie/popular")
    fun getMovie(@Query("page") page : Int,
                 @Query("api_key") apiKey: String): Observable<MovieResponse>
}