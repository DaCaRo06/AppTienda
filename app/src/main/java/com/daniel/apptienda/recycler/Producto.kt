package com.daniel.apptienda.recycler

data class Producto(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    var isFav: Boolean = false
)