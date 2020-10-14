package com.example.daggerhilt.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.daggerhilt.R
import com.example.daggerhilt.model.BlogView
import com.example.daggerhilt.util.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class MainFragment constructor(
    private val someString: String
) : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeObservers()
        viewModel.setStateEvent(MainStateEvent.GetBlogsEvent)
    }

    private fun subscribeObservers() =
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
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