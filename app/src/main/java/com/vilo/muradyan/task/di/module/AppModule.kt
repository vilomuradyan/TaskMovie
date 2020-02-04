package com.vilo.muradyan.task.di.module

import android.content.Context
import androidx.room.Room
import com.vilo.muradyan.task.App
import com.vilo.muradyan.task.data.database.AppDatabase
import com.vilo.muradyan.task.data.database.MovieDao
import com.vilo.muradyan.task.data.database.MovieRepo
import com.vilo.muradyan.task.data.database.MovieRepository
import com.vilo.muradyan.task.data.network.Api
import com.vilo.muradyan.task.utils.Constants
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
class AppModule(private val app: App) {

    @Provides
    fun providesApp():App = app

    @Provides
    @Singleton
    internal fun providesContext(): Context = app.applicationContext

    @Singleton
    @Provides
    internal fun providesAppDatabase(context: Context) : AppDatabase =
        Room.databaseBuilder(context , AppDatabase::class.java , Constants.DATABASE_NAME).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    internal fun providesmovieDaoHelper(appDatabase: AppDatabase): MovieDao = appDatabase.getMovieDao()

    @Provides
    @Singleton
    internal fun providesmovieRepoHelper(appDatabase: AppDatabase, api: Api, context: Context): MovieRepo = MovieRepository(appDatabase.getMovieDao(),api,context)

    @Provides
    internal fun providesCompositeDisposable(): CompositeDisposable = CompositeDisposable()
}