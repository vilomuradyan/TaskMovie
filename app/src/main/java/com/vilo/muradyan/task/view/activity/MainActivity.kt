package com.vilo.muradyan.task.view.activity

import android.os.Build
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.vilo.muradyan.task.App
import com.vilo.muradyan.task.R
import com.vilo.muradyan.task.data.model.Results
import com.vilo.muradyan.task.view.adapter.MovieAdapter
import com.vilo.muradyan.task.view.adapter.MovieSavedAdapter
import com.vilo.muradyan.task.view.base.BaseActivity
import com.vilo.muradyan.task.view.fragment.MovieDetailFragment
import com.vilo.muradyan.task.view.fragment.MovieFragment

class MainActivity : BaseActivity() , MovieAdapter.Callback ,MovieSavedAdapter.Callback{
    override fun initBeforeView() {
        with(application as App){
            di.inject(this@MainActivity)
        }
    }

    override fun getContentViewId(): Int = R.layout.activity_main

    override fun initViews() {
        supportFragmentManager.
                beginTransaction().
                add(R.id.root_layout,MovieFragment.newInstance(this,this),"movie").
                commit()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onItemClick(item: Results, imageView: ImageView, textView: TextView) {
           supportFragmentManager.
            beginTransaction().
            add(R.id.root_layout, MovieDetailFragment.newInstance(item),"movie").
            addSharedElement(imageView,imageView.transitionName).
            addSharedElement(textView,textView.transitionName).
            addToBackStack(javaClass.simpleName).
            commit()
    }
}


