package com.originalstocks.paginglibdemo.data

import com.originalstocks.paginglibdemo.activity.MainActivity
import io.reactivex.Single
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://newsapi.org/v2/" //

interface NetworkService {

    @GET("top-headlines?country=in&apiKey=aa67d8d98c8e4ad1b4f16dbd5f3be348") //
    fun getNews(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): Single<Response>

    companion object {
        //defining cache size
        val cacheSize = (5 * 1024 * 1024).toLong()

        // creating cache instance
        val myCache = Cache(MainActivity.instanceMain.cacheDir, cacheSize)

        var client: OkHttpClient = OkHttpClient.Builder()
            .cache(myCache)
            .addInterceptor { chain ->
                var request = chain.request()
                request = if (isNetworkAvailable())
                    request
                        .newBuilder()
                        .addHeader("Accept-Version", "v1")
                        .header("Cache-Control", "public, max-age=" + 5).build()
                else
                    request
                        .newBuilder()
                        .addHeader("Accept-Version", "v1")
                        .header(
                            "Cache-Control",
                            "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                        ).build()
                chain.proceed(request)
            }.build()

        fun getService(): NetworkService {
            val retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(NetworkService::class.java)
        }
    }

}