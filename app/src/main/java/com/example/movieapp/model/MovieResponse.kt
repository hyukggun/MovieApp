package com.example.movieapp.model

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    val dates: Map<String, String>,
    val page: Int,
    val results: List<Movie>,
    @SerializedName("total_pages") val totalPages: Int,
)