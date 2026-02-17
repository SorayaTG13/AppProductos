package com.example.appproductos.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appproductos.data.model.ProductoDto
import com.example.appproductos.data.remote.ProductosNetwork
import com.example.appproductos.data.repository.ProductosRepository
import kotlinx.coroutines.launch


class ProductosViewModel : ViewModel() {

    private val repository = ProductosRepository(ProductosNetwork.api)



    var state by mutableStateOf<UiState<List<ProductoDto>>>(UiState.Loading)
        private set

    init {
        loadProducts()
    }

    fun loadProducts(page: Int = 1) {
        viewModelScope.launch {
            state = UiState.Loading
            state = try {
                UiState.Success(repository.getProducts(page))
            } catch (e: Exception) {
                UiState.Error(e.message ?: "Error cargando productos")
            }
        }
    }

}