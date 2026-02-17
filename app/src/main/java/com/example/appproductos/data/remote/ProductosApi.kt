package com.example.appproductos.data.remote

import com.example.appproductos.data.model.PagedResponse
import com.example.appproductos.data.model.ProductoDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductosApi {

    @GET("products")
    suspend fun getProducts(
        @Query("page") page: Int = 1
    ): PagedResponse<ProductoDto>
}