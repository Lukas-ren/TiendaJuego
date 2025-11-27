package com.example.login.viewmodel

import com.example.login.model.Carrito
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CarritoViewModelTest {

    private lateinit var viewModel: CarritoViewModel

    @Before
    fun setup() {
        viewModel = CarritoViewModel()
    }

    @Test
    fun agregarProducto_nuevo_agregaItem() {
        val item = Carrito(id = 1, nombre = "Juego A", precio = 10.0)
        viewModel.agregarProducto(item)

        val carrito = viewModel.carrito.value
        assertEquals(1, carrito.size)
        assertEquals(1, carrito.first().cantidad)
        assertEquals(10.0, viewModel.total, 0.001)
    }

    @Test
    fun agregarProducto_existente_incrementaCantidad() {
        val item = Carrito(id = 2, nombre = "Juego B", precio = 5.0)
        viewModel.agregarProducto(item)
        viewModel.agregarProducto(item)

        val carrito = viewModel.carrito.value
        assertEquals(1, carrito.size)
        assertEquals(2, carrito.first().cantidad)
        assertEquals(10.0, viewModel.total, 0.001)
    }

    @Test
    fun agregarSiNoExiste_noDuplica() {
        val item = Carrito(id = 3, nombre = "Juego C", precio = 7.5)
        viewModel.agregarSiNoExiste(item)
        viewModel.agregarSiNoExiste(item) // segundo intento no debe agregar

        val carrito = viewModel.carrito.value
        assertEquals(1, carrito.size)
        assertEquals(1, carrito.first().cantidad)
    }

    @Test
    fun disminuirCantidad_decrementa_y_elimina_si_llega_a_cero() {
        val item = Carrito(id = 4, nombre = "Juego D", precio = 12.0)
        // agregar dos veces → cantidad 2
        viewModel.agregarProducto(item)
        viewModel.agregarProducto(item)

        // disminuir una vez → cantidad 1
        viewModel.disminuirCantidad(4)
        var carrito = viewModel.carrito.value
        assertEquals(1, carrito.size)
        assertEquals(1, carrito.first().cantidad)

        // disminuir otra vez → debe eliminarse
        viewModel.disminuirCantidad(4)
        carrito = viewModel.carrito.value
        assertTrue(carrito.isEmpty())
    }

    @Test
    fun quitarYvaciar_actualizaTotalCorrectamente() {
        val a = Carrito(id = 5, nombre = "A", precio = 3.0)
        val b = Carrito(id = 6, nombre = "B", precio = 4.0)
        viewModel.agregarProducto(a)
        viewModel.agregarProducto(b)
        // total = 7.0
        assertEquals(7.0, viewModel.total, 0.001)

        // quitar producto A
        viewModel.quitarProducto(5)
        assertEquals(1, viewModel.carrito.value.size)
        assertEquals(4.0, viewModel.total, 0.001)

        // vaciar
        viewModel.vaciarCarrito()
        assertTrue(viewModel.carrito.value.isEmpty())
        assertEquals(0.0, viewModel.total, 0.001)
    }
}

