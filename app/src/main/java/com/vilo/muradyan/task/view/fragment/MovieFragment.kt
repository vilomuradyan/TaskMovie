package com.vilo.muradyan.task.view.fragment

import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vilo.muradyan.task.App
import com.vilo.muradyan.task.R
import com.vilo.muradyan.task.data.model.Results
import com.vilo.muradyan.task.utils.Constants
import com.vilo.muradyan.task.utils.InfiniteScrollListener
import com.vilo.muradyan.task.view.adapter.MovieAdapter
import com.vilo.muradyan.task.view.adapter.MovieSavedAdapter
import com.vilo.muradyan.task.view.base.BaseFragment
import com.vilo.muradyan.task.viewModel.MovieFragmentViewModel
import com.vilo.muradyan.task.viewModel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_movie.*

class MovieFragment:BaseFragment() {

    private lateinit var viewModel: MovieFragmentViewModel
    private var movieSaevdAdapter: MovieSavedAdapter? = MovieSavedAdapter(ArrayList(),callbackMovieSaved!!)
    private var movieAdapter: MovieAdapter? = MovieAdapter(ArrayList(),callbackMovie!!)
    private var currentPageMovie = 0
    private var currentPageMovieSaved = 0
    private var txtSaved: TextView? = null


    companion object {
        var callbackMovie: MovieAdapter.Callback? = null
        var callbackMovieSaved: MovieSavedAdapter.Callback? = null
        fun newInstance(callbackMovie: MovieAdapter.Callback, callbackMovieSaved: MovieSavedAdapter.Callback): MovieFragment {
            this.callbackMovie = callbackMovie
            this.callbackMovieSaved = callbackMovieSaved
            return MovieFragment()
        }
    }

    override fun initBeforeView() {
        with(context!!.applicationContext as App){
            viewModel = androidx.lifecycle.ViewModelProviders.of(this@MovieFragment , ViewModelFactory(this)).get(
                MovieFragmentViewModel::class.java)

        }

    }

    override fun getContentViewId(): Int = R.layout.fragment_movie

    override fun initViews(rootView: View) {
        initMovieRecyclerView()
        txtSaved = rootView.findViewById(R.id.txtSave) as TextView
        progressBar.visibility = View.VISIBLE
        initMovieSavedRecyclerView()
        viewModelObserver()
        loadMovieSaved()
        loadMovie()
        viewModel.loadDataFromWorker()
    }

    private fun initMovieRecyclerView(){

        movieRecyclerView!!.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        movieRecyclerView.apply {
            setHasFixedSize(true)
            addOnScrollListener(
                InfiniteScrollListener({ loadMovie() },
                movieRecyclerView!!.layoutManager as LinearLayoutManager
            )
            )
        }

    }


    fun initMovieSavedRecyclerView(){
        saveRecyclerView!!.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)

        saveRecyclerView.apply {
            setHasFixedSize(true)
            addOnScrollListener(
                InfiniteScrollListener({ loadMovieSaved() },
                    saveRecyclerView!!.layoutManager as LinearLayoutManager
                )
            )
        }
    }
    private fun loadMovie() {
        viewModel.loadMovie(Constants.LIMIT,currentPageMovie * Constants.OFFSET)
        currentPageMovie++
    }

    private fun loadMovieSaved(){
        viewModel.getMovieSaved(Constants.LIMIT,currentPageMovieSaved * Constants.OFFSET)
    }

    private fun viewModelObserver(){

        viewModel.movieResult().observe(this, Observer<List<Results>> {
            if (it != null) {
                val position = movieAdapter?.itemCount
                movieAdapter?.addmovie(it)
                movieRecyclerView.adapter = movieAdapter
                if (position != null) {
                    movieRecyclerView.scrollToPosition(position - Constants.LIST_SCROLLING)
                }
            }
        })

        viewModel.movieLoader().observe(this, Observer<Boolean> {
            if (it == false) progressBar.visibility = View.GONE
        })

        viewModel.movieError().observe(this, Observer<String> {
            if (it != null) {
            }
        })


        viewModel.movieResultSaved().observe(this, Observer<List<Results>> {
            if (it != null && it.isNotEmpty()) {
                txtSaved?.visibility = View.VISIBLE
                saveRecyclerView.visibility = View.VISIBLE
                val position = movieSaevdAdapter?.itemCount
                movieSaevdAdapter?.addMovie(it)
                saveRecyclerView.adapter = movieSaevdAdapter
                if (position != null) {
                    saveRecyclerView.scrollToPosition(position - Constants.LIST_SCROLLING)
                }
            }

        })



        viewModel.movieSavedError().observe(this, Observer<String> {
            if (it != null) {
                Toast.makeText(activity,"error",Toast.LENGTH_LONG).show()
            }
        })


    }



    override fun onDestroy() {
        viewModel.disposeElements()
        super.onDestroy()
    }

}