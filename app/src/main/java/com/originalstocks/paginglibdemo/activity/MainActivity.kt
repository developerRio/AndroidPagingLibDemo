package com.originalstocks.paginglibdemo.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.originalstocks.paginglibdemo.adapter.NewsListAdapter
import com.originalstocks.paginglibdemo.data.State
import com.originalstocks.paginglibdemo.databinding.ActivityMainBinding
import com.originalstocks.paginglibdemo.viewModel.NewsListViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: NewsListViewModel
    private lateinit var newsListAdapter: NewsListAdapter

    companion object {
        var instanceMain = MainActivity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        instanceMain = this


        viewModel = ViewModelProvider(this).get(NewsListViewModel::class.java)
        initAdapter()
        initState()
    }

    private fun initAdapter() {
        newsListAdapter = NewsListAdapter { viewModel.retry() }
        binding.recyclerView.adapter = newsListAdapter
        viewModel.photosDataList.observe(this,
            Observer {
                newsListAdapter.submitList(it)
            })
    }

    private fun initState() {
        binding.txtError.setOnClickListener { viewModel.retry() }
        viewModel.getState().observe(this, Observer { state ->
            binding.progressBar.visibility =
                if (viewModel.listIsEmpty() && state == State.LOADING) View.VISIBLE else View.GONE
            binding.txtError.visibility =
                if (viewModel.listIsEmpty() && state == State.ERROR) View.VISIBLE else View.GONE
            if (!viewModel.listIsEmpty()) {
                newsListAdapter.setState(state ?: State.DONE)
            }
        })
    }
}