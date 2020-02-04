package com.vilo.muradyan.task.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.vilo.muradyan.task.data.database.MovieRepo
import com.vilo.muradyan.task.di.component.DIComponent
import com.vilo.muradyan.task.utils.iomain
import com.vilo.muradyan.task.worker.MovieWorker
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MovieFragmentViewModel : ViewModel(),DIComponent.Injectable {

    @Inject
    lateinit var movieRepo: MovieRepo
    private lateinit var disposableObserver: DisposableObserver<List<com.vilo.muradyan.task.data.model.Results>>
    protected val disposables: CompositeDisposable = CompositeDisposable()
    var movieResult: MutableLiveData<List<com.vilo.muradyan.task.data.model.Results>> = MutableLiveData()
    var movieError: MutableLiveData<String> = MutableLiveData()
    var movieLoader: MutableLiveData<Boolean> = MutableLiveData()
    var movieResultSaved: MutableLiveData<List<com.vilo.muradyan.task.data.model.Results>> = MutableLiveData()
    var movieSavedError: MutableLiveData<String> = MutableLiveData()

    override fun inject(diComponent: DIComponent) {
        diComponent.inject(this)
    }



    fun movieResult(): LiveData<List<com.vilo.muradyan.task.data.model.Results>> {
        return movieResult
    }

    fun movieError(): LiveData<String> {
        return movieError
    }

    fun movieLoader(): LiveData<Boolean> {
        return movieLoader
    }


    fun movieResultSaved(): LiveData<List<com.vilo.muradyan.task.data.model.Results>> {
        return movieResultSaved
    }

    fun movieSavedError(): LiveData<String> {
        return movieSavedError
    }



    fun loadMovie(limit: Int, offset: Int) {
        disposableObserver = object : DisposableObserver<List<com.vilo.muradyan.task.data.model.Results>>() {
            override fun onComplete() {
            }

            override fun onNext(t: List<com.vilo.muradyan.task.data.model.Results>) {
                movieResult.postValue(t)
                movieLoader.postValue(false)
            }

            override fun onError(e: Throwable) {
                movieError.postValue(e.message)
                movieLoader.postValue(false)
            }

        }


        movieRepo.allResults(limit, offset).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .debounce(400, TimeUnit.MILLISECONDS)
            .subscribe(disposableObserver)

    }

    fun getMovieSaved(limit: Int, offset: Int){

        disposables.add(movieRepo.allResultSaved(limit,offset,true).iomain()
            .subscribe({
                movieResultSaved.postValue(it)
            },{
                movieSavedError.postValue(it.message)
            }))
    }




        fun disposeElements(){
            if (!disposableObserver.isDisposed) disposableObserver.dispose()
            if (!disposables.isDisposed) disposables.dispose()
        }


    fun loadDataFromWorker(){
        WorkManager.getInstance().enqueueUniquePeriodicWork(
            "movieWorker",
            ExistingPeriodicWorkPolicy.REPLACE,
            PeriodicWorkRequestBuilder<MovieWorker>(15, TimeUnit.MINUTES).build())

    }
}


