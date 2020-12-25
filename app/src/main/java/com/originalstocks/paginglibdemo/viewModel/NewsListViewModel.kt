package com.originalstocks.paginglibdemo.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.originalstocks.paginglibdemo.data.*
import io.reactivex.disposables.CompositeDisposable

class NewsListViewModel : ViewModel() {
    private val networkService = NetworkService.getService()
    var photosDataList: LiveData<PagedList<PhotosData>>
    private val compositeDisposable = CompositeDisposable()
    private val pageSize = 5
    private val newsDataSourceFactory: NewsDataSourceFactory

    init {
        newsDataSourceFactory = NewsDataSourceFactory(compositeDisposable, networkService)
        val config = PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setInitialLoadSizeHint(pageSize * 2)
                .setEnablePlaceholders(false)
                .build()
        photosDataList = LivePagedListBuilder(newsDataSourceFactory, config).build()
    }


    fun getState(): LiveData<State> = Transformations.switchMap(
            newsDataSourceFactory.newsDataSourceLiveData,
            NewsDataSource::state
    )

    fun retry() {
        newsDataSourceFactory.newsDataSourceLiveData.value?.retry()
    }

    fun listIsEmpty(): Boolean {
        return photosDataList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}