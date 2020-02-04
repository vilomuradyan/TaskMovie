package com.vilo.muradyan.task

import android.app.Activity
import android.app.Application
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.work.Configuration
import androidx.work.WorkManager
import com.vilo.muradyan.task.di.component.DIComponent
import com.vilo.muradyan.task.di.component.DaggerDIComponent
import com.vilo.muradyan.task.di.module.ApiModule
import com.vilo.muradyan.task.di.module.AppModule
import com.vilo.muradyan.task.worker.MovieWorkerFactory

class App:Application() {

    lateinit var di:DIComponent

    override fun onCreate() {
        super.onCreate()

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity?) {

            }

            override fun onActivityResumed(activity: Activity?) {

            }

            override fun onActivityStarted(activity: Activity?) {

            }

            override fun onActivityDestroyed(activity: Activity?) {

            }

            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {

            }

            override fun onActivityStopped(activity: Activity?) {

            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
        })

        di = DaggerDIComponent
            .builder()
            .apiModule(ApiModule())
            .appModule(AppModule(this))
            .build()







        var workerFactory: MovieWorkerFactory =  di.inject()


        val config = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
        WorkManager.initialize(this, config)
    }
}