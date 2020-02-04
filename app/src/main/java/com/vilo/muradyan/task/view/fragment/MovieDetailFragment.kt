package com.vilo.muradyan.task.view.fragment

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.transition.TransitionInflater
import com.vilo.muradyan.task.App
import com.vilo.muradyan.task.R
import com.vilo.muradyan.task.data.model.Results
import com.vilo.muradyan.task.utils.CustomToast
import com.vilo.muradyan.task.view.base.BaseFragment
import com.vilo.muradyan.task.viewModel.MovieDetailFragmentViewModel
import com.vilo.muradyan.task.viewModel.ViewModelFactory
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_movie_detail.*

class MovieDetailFragment:BaseFragment() {

    private lateinit var viewModel: MovieDetailFragmentViewModel
    private lateinit var result: Results

    companion object {


        fun newInstance(item: Results) = MovieDetailFragment().apply {
             arguments  = Bundle().apply {
                putParcelable("item", item)

            }
        }

    }
    override fun initBeforeView() {
        with(context!!.applicationContext as App){
            viewModel = androidx.lifecycle.ViewModelProviders.of(this@MovieDetailFragment , ViewModelFactory(this)).get(
                MovieDetailFragmentViewModel::class.java)
        }

        arguments?.let {
            result = it.getParcelable("item")!!
        }
    }

    override fun getContentViewId(): Int = R.layout.fragment_movie_detail

    override fun initViews(rootView: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        }


        val imageUrl = "https://image.tmdb.org/t/p/original"

        Glide.with(this).load(imageUrl + result.poster_path).centerCrop().into(movieImage)
        movieTitle.text = result.title
        movieType.text = result.popularity.toString()
        description.text = result.overview
        if (result.adult)
            addToList.setImageResource(R.drawable.ic_turned_in_black_24dp)


        addToList.setOnClickListener {
            if (!result.adult)
                addToList.setImageResource(R.drawable.ic_turned_in_black_24dp)
            else
                addToList.setImageResource(R.drawable.ic_turned_in_not_black_24dp)

            viewModel.isSavemovie(result.id.toString(),!result.adult)
        }

        viewModelObserver()
    }


    private fun viewModelObserver(){
        viewModel.isSaveResult().observe(this, Observer<Boolean>{
            result.adult = !result.adult
            CustomToast.makeText(context!!.applicationContext,"Saved",1)
        })

        viewModel.isSaveError().observe(this, Observer<String> {
            if (result.adult)
                addToList.setImageResource(R.drawable.ic_turned_in_black_24dp)
            else
                addToList.setImageResource(R.drawable.ic_turned_in_not_black_24dp)

            CustomToast.makeText(context!!.applicationContext,"Error",2)
        })
    }


    override fun onDestroy() {
        viewModel.disposeElements()
        super.onDestroy()
    }

}