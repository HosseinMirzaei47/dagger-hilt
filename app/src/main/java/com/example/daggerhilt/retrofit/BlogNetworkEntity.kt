package com.example.daggerhilt.retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BlogNetworkEntity(
    @SerializedName("pk")
    @Expose
    var id: Int,
    @Expose
    var title: String,
    @Expose
    var body: String,
    @Expose
    var image: String,
    @Expose
    var category: String
)