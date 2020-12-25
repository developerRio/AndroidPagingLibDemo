package com.originalstocks.paginglibdemo.data

import com.google.gson.annotations.SerializedName

data class Response(
        @SerializedName("articles") //
        val photosData: List<PhotosData>
)

data class PhotosData(
        val title: String,
        @SerializedName("urlToImage")
        val image: String?
)