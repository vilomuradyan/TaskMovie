package com.vilo.muradyan.task.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vilo.muradyan.task.data.database.MovieRepo
import com.vilo.muradyan.task.di.component.DIComponent
import com.vilo.muradyan.task.utils.iomain
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MovieDetailFragmentViewModel : ViewModel(),DIComponent.Injectable {


    @Inject
    lateinit var movieRepo: MovieRepo
    protected val disposables: CompositeDisposable = CompositeDisposable()
    var isSaveError: MutableLiveData<String> = MutableLiveData()
    var isSaveResult: MutableLiveData<Boolean> = MutableLiveData()

    override fun inject(diComponent: DIComponent) {
        diComponent.inject(this)
    }

    fun isSaveError(): LiveData<String> {
        return isSaveError
    }

    fun isSaveResult(): LiveData<Boolean> {
        return isSaveResult
    }


    fun isSavemovie(id:String, isSave:Boolean){

        disposables.add(movieRepo.isSaveResult(id, isSave).iomain().subscribe(
            {
                isSaveResult.postValue(true)
            },
            {
                isSaveError.postValue(it.message)
            }
        ))

    }

    fun disposeElements(){
        if (!disposables.isDisposed) disposables.dispose()
    }


}