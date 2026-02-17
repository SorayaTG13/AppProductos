package com.example.appproductos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appproductos.presentation.viewmodel.ProductosViewModel
import com.example.appproductos.ui.screens.ProductosScreen
import com.example.appproductos.ui.theme.AppProductosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppProductosTheme() {
                Surface() {
                    val vm: ProductosViewModel = viewModel()
                    ProductosScreen(vm)
                }
            }
        }
    }
}