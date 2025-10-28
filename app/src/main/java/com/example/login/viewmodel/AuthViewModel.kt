package com.example.login.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.login.model.FakeDatabase
import com.example.login.model.Usuario

class AuthViewModel : ViewModel() {

    var mensaje = mutableStateOf("")
    var usuarioActual = mutableStateOf<String?>(null)

    fun registrar(nombre: String, email: String, password: String) {
        if (nombre.isBlank() || email.isBlank() || password.isBlank()) {
            mensaje.value = "Todos los campos son obligatorios"
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mensaje.value = "Correo inválido ❌"
            return
        }
        if (password.length < 6 ) {
            mensaje.value = "La contraseña debe tener mínimo 6 carácteres"
            return
        }
        val nuevo = Usuario(email, nombre,password)
        if (FakeDatabase.registrar(nuevo)) {
            mensaje.value = "Registro exitoso ✅"
        } else {
            mensaje.value = "El usuario ya existe ❌"
        }
    }

    fun login(email: String, password: String): Boolean {
        val usuario = FakeDatabase.login(email, password)
        return if (usuario != null) {
            usuarioActual.value = usuario.email
            mensaje.value = "Inicio de sesión exitoso 🎉"
            true
        } else {
            mensaje.value = "Credenciales inválidas ❌"
            false
        }
    }
}