package com.example.appproductos.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.appproductos.data.model.ProductoDto
import com.example.appproductos.presentation.viewmodel.ProductosViewModel
import com.example.appproductos.presentation.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductosScreen(vm: ProductosViewModel) {
    // Escuchamos el estado del ViewModel
    val state = vm.state

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Lista de Productos") })
        }
    ) { paddingValues ->

        Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            when (state) {
                is UiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is UiState.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Ha ocurrido un error", color = Color.Red)
                            Text(text = state.msg, style = MaterialTheme.typography.bodySmall)
                            Spacer(Modifier.height(16.dp))
                            // Asumiendo que tu ViewModel tiene una función loadProductos()
                            Button(onClick = { vm.loadProducts() }) {
                                Text("Reintentar")
                            }
                        }
                    }
                }

                is UiState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(state.data) { producto ->
                            ProductoItem(producto)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProductoItem(producto: ProductoDto) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // IMAGEN DEL PRODUCTO
            val painter = rememberAsyncImagePainter(model = producto.image)

            Box(
                modifier = Modifier
                    .size(100.dp) // Un poco más grande para ver bien el producto
                    .clip(RoundedCornerShape(8.dp)) // Cuadrada con bordes suaves
                    .background(Color.White), // Fondo blanco por si la imagen es PNG transparente
                contentAlignment = Alignment.Center
            ) {
                if (painter.state is AsyncImagePainter.State.Loading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
                }

                Image(
                    painter = painter,
                    contentDescription = "Imagen de ${producto.name}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // DATOS DEL PRODUCTO
            Column(modifier = Modifier.weight(1f)) {
                // Categoría (pequeña arriba)
                Text(
                    text = producto.category.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )

                // Nombre
                Text(
                    text = producto.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                // Descripción (limitada a 2 líneas)
                Text(
                    text = producto.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Precio
                Text(
                    text = "${producto.price} €",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}