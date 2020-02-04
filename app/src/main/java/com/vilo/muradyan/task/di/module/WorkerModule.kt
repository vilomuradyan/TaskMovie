package com.vilo.muradyan.task.di.module

import androidx.work.ListenableWorker
import com.vilo.muradyan.task.di.annotation.WorkerKey
import com.vilo.muradyan.task.worker.IWorkerFactory
import com.vilo.muradyan.task.worker.MovieWorker
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface WorkerModule {
    @Binds
    @IntoMap
    @WorkerKey(MovieWorker::class)
    fun bindmovieWorker(factory: MovieWorker.Factory): IWorkerFactory<out ListenableWorker>
}