package com.vilo.muradyan.task.worker

import android.annotation.SuppressLint
import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.vilo.muradyan.task.data.database.MovieRepo
import com.vilo.muradyan.task.utils.displayNotification
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider

class MovieWorker @Inject constructor(private var context: Context, private var params: WorkerParameters, private var movieRepo: MovieRepo): Worker(context,params){

    @SuppressLint("RestrictedApi", "CheckResult")
    override fun doWork(): Result  = try {
        movieRepo.periodResults().subscribe({
            if (it.isNotEmpty()){
                displayNotification("movie" ,"${it.size}  new movie", context )
            }
        },{
            Result.Retry()
        })

        Result.success()

    }catch (e:Throwable){
        Timber.e(e.message)
        Result.failure()
    }



    class Factory @Inject constructor(private val movieRepo: Provider<MovieRepo>) : IWorkerFactory<MovieWorker> {
        override fun create(context: Context,params: WorkerParameters): MovieWorker {
            return MovieWorker(context, params, movieRepo.get())
        }
    }
}