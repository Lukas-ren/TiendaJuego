package com.example.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*

import com.example.login.viewmodel.AuthViewModel
import com.example.login.viewmodel.CatalogoViewModel
import com.example.login.viewmodel.CompraExitosaViewModel
import com.example.login.viewmodel.CompraRechazadaViewModel
import com.example.login.viewmodel.CarritoViewModel
import com.example.login.views.AgregarProductoScreen
import com.example.login.views.BackOfficeScreen

import com.example.login.views.CatalogoScreen
import com.example.login.views.DetalleVideojuegoScreen
import com.example.login.views.CarritoScreen
import com.example.login.views.CompraExitosaScreen
import com.example.login.views.CompraRechazadaScreen
import com.example.login.views.LoginScreen
import com.example.login.views.RegisterScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                val navController = rememberNavController()
                val authViewModel: AuthViewModel = viewModel()
                val catalogoViewModel: CatalogoViewModel = viewModel()
                val carritoViewModel: CarritoViewModel = viewModel()
                val compraExitosaViewModel: CompraExitosaViewModel = viewModel()
                val compraRechazadaViewModel: CompraRechazadaViewModel = viewModel()
                NavHost(
                    navController = navController,
                    startDestination = "register"
                ) {
                    composable("register") {
                        RegisterScreen(
                            navController = navController,
                            viewModel = authViewModel
                        )
                    }

                    composable("login") {
                        LoginScreen(
                            navController = navController,
                            viewModel = authViewModel
                        )
                    }

                    composable("catalogo") {
                        CatalogoScreen(
                            navController = navController,
                            viewModel = catalogoViewModel
                        )
                    }
                    composable("carrito") {
                        CarritoScreen(
                            navController = navController,
                            carritoViewModel = carritoViewModel,
                            compraExitosaViewModel = compraExitosaViewModel
                        )
                    }
                    composable(route = "compraExitosa") {
                        CompraExitosaScreen(
                            navController = navController,
                            viewModel = compraExitosaViewModel
                        )
                    }
                    composable(route = "compraRechazada") {
                        CompraRechazadaScreen(
                            navController = navController,
                            viewModel = compraRechazadaViewModel
                        )
                    }
                    composable("backoffice") {
                        BackOfficeScreen(
                            navController = navController,
                        )
                    }
                    composable(route = "agregarProducto") {
                        AgregarProductoScreen(navController = navController)
                    }

                    composable("detalle/{videojuegoId}") { backStack ->
                        val idStr = backStack.arguments?.getString("videojuegoId")
                        val id = idStr?.toIntOrNull() ?: -1

                        DetalleVideojuegoScreen(
                            videojuegoId = id,
                            viewModel = catalogoViewModel,
                            navController = navController,
                            carritoViewModel
                        )
                    }
                }
            }
        }
    }
