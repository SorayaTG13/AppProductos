package com.example.appproductos.data.repository

import com.example.appproductos.data.model.ProductoDto
import com.example.appproductos.data.remote.ProductosApi

class ProductosRepository(private val api: ProductosApi) {

    suspend fun getProducts(page: Int = 1): List<ProductoDto> =
        api.getProducts(page).results
}