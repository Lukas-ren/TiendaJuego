package com.example.login.model

object FakeDatabase {
    private val usuarios = mutableListOf(
        Usuario(
            nombre = "Admin",
            email = "admin@login.com",
            password = "admin"
        )
    )

    fun registrar(usuario: Usuario): Boolean {
        if (usuarios.any { it.email == usuario.email }) return false
        usuarios.add(usuario)
        return true
    }

    fun login(email: String, password: String): Usuario? {
        return usuarios.find { it.email == email && it.password == password }
    }
}