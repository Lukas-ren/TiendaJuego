package com.example.login.viewmodel

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class AuthViewModelTest {

    private lateinit var viewModel: AuthViewModel

    @Before
    fun setup() {
        viewModel = AuthViewModel()
        viewModel.resetLogin()
    }

    @Test
    fun login_camposVacios_devuelveFalse_y_mensaje() {
        val result = viewModel.login("", "")
        assertFalse(result)
        assertEquals("Debes completar todos los campos.", viewModel.mensaje.value)
        assertNull(viewModel.usuarioActual.value)
    }

    @Test
    fun login_credencialesInvalidas_devuelveFalse_y_mensaje() {
        val result = viewModel.login("noexiste@example.com", "contraseñaincorrecta")
        assertFalse(result)
        assertEquals("Credenciales inválidas ❌", viewModel.mensaje.value)
        assertNull(viewModel.usuarioActual.value)
    }

    @Test
    fun login_credencialesValidas_admin_devuelveTrue_y_usuarioActual() {
        // El FakeDatabase contiene por defecto al admin: admin@login.com / admin
        val result = viewModel.login("admin@login.com", "admin")
        assertTrue(result)
        assertEquals("Inicio de sesión exitoso 🎉", viewModel.mensaje.value)
        assertEquals("admin@login.com", viewModel.usuarioActual.value)
    }

    @Test
    fun resetLogin_limpiaUsuarioYMensaje() {
        // Loguear primero
        viewModel.login("admin@login.com", "admin")
        assertNotNull(viewModel.usuarioActual.value)
        // Reset
        viewModel.resetLogin()
        assertNull(viewModel.usuarioActual.value)
        assertEquals("", viewModel.mensaje.value)
    }
}

