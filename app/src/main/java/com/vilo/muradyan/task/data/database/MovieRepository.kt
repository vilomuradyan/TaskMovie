package com.vilo.muradyan.task.data.database

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.vilo.muradyan.task.data.network.Api
import com.vilo.muradyan.task.utils.isNetworkConnected
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject

class MovieRepository @Inject internal constructor(private val movieDao: MovieDao, private val api: Api,
                                                   private val context: Context):MovieRepo {


    override fun allResultSaved(limit: Int, offset: Int, isSaved:Boolean): Single<List<com.vilo.muradyan.task.data.model.Results>> =  movieDao.allResultsSaved(limit, offset,isSaved)

    override fun isSaveResult(id:String, isSave: Boolean): Completable =Completable.fromAction { (movieDao.isSaveResult(id,isSave)) }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun periodResults(): Single<List<com.vilo.muradyan.task.data.model.Results>> {
        val hasConnection = isNetworkConnected(context)
        var observableFromApi : Single<List<com.vilo.muradyan.task.data.model.Results>>? = null
        return observableFromApi!!

    }

    override fun allResults(limit: Int, offset: Int): Observable<List<com.vilo.muradyan.task.data.model.Results>> {
        val hasConnection = isNetworkConnected(context)
        var observableFromApi : Observable<List<com.vilo.muradyan.task.data.model.Results>>? = null



        var page = 1
        if (offset>0)
            page = (offset/10) + 1

        if (hasConnection){
            observableFromApi = getMovieFromApi(page)
        }
        var observableFromDb = getMovieFromDb(limit,offset)

        return if (hasConnection) Observable.concatArrayEager(observableFromApi,observableFromDb)
        else observableFromDb
    }


    private fun getMovieFromApi(page:Int):Observable<List<com.vilo.muradyan.task.data.model.Results>>?{
        return  api.getMovie(page,"d4170e4d87c04438aedcfb31101df620").doOnNext{
            Timber.e(it.total_pages.toString())
            for (item:com.vilo.muradyan.task.data.model.Results in it.results) {
                item?.apply {
                    movieDao.insertResults(item)
                }
            }
        }.map{ it.results}
    }

    private fun getMovieFromDb(limit: Int, offset: Int):Observable<List<com.vilo.muradyan.task.data.model.Results>>{
        return movieDao.loadResults(limit,offset).toObservable()
    }
}