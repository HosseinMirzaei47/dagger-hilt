package com.example.daggerhilt.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.daggerhilt.R
import com.example.daggerhilt.model.BlogView
import com.example.daggerhilt.util.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        subscribeObservers()
        viewModel.setStateEvent(MainStateEvent.GetBlogsEvent)

    }

    private fun subscribeObservers() {

        viewModel.dataState.observe(this, Observer { dataState ->
            when (dataState) {
                is DataState.Success<List<BlogView>> -> {
                    displayProgressBar(false)
                    appendBlogTitles(dataState.data)
                }
                is DataState.Loading -> {
                    displayProgressBar(true)
                }
                is DataState.Error -> {
                    displayProgressBar(true)
                    displayError(dataState.exception.message)
                }
            }
        })

    }

    private fun displayError(message: String?) {

        if (message != null) {
            myTextView.text = message
        } else {
            myTextView.text = getString(R.string.unknown_error)
        }

    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        myProgress.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }

    private fun appendBlogTitles(blogs: List<BlogView>) {

        val sb = StringBuilder()
        blogs.forEach { blog ->
            sb.append(blog.title + "\n")
        }
        myTextView.text = sb.toString()

    }

}