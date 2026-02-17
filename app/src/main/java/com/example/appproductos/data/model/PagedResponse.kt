package com.example.appproductos.data.model

import com.squareup.moshi.Json

data class PagedResponse<T>(
    val page: Int,
    @Json(name = "per_page")val perPage: Int,
    val total: Int,
    @Json(name = "total_pages")val totalPages: Int,
    val results: List<T>

)