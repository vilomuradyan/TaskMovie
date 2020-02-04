package com.vilo.muradyan.task.di.component

import androidx.annotation.Keep
import com.vilo.muradyan.task.di.module.ApiModule
import com.vilo.muradyan.task.di.module.AppModule
import com.vilo.muradyan.task.di.module.WorkerModule
import com.vilo.muradyan.task.view.activity.MainActivity
import com.vilo.muradyan.task.viewModel.MovieDetailFragmentViewModel
import com.vilo.muradyan.task.viewModel.MovieFragmentViewModel
import com.vilo.muradyan.task.worker.MovieWorkerFactory
import dagger.Component
import javax.inject.Singleton

@Keep
@Singleton
@Component(modules = [AppModule::class , ApiModule::class, WorkerModule::class])
interface DIComponent {
    interface Injectable{
        fun inject(diComponent: DIComponent)
    }

    fun inject(mainActivity: MainActivity)
    fun inject(movieFragmentViewModel: MovieFragmentViewModel)
    fun inject(movieDetailFragmentViewModel: MovieDetailFragmentViewModel)
    fun inject(): MovieWorkerFactory
}