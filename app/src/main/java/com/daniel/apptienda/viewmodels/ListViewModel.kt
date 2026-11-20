package com.daniel.apptienda.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daniel.apptienda.recycler.Producto

class ListViewModel : ViewModel() {

    private val productos = mutableListOf(
        Producto(1, "Camiseta Básica", "Camiseta de algodón 100% color blanco", 19.99, false),
        Producto(2, "Pantalón Vaquero", "Pantalón denim corte clásico", 49.99, false),
        Producto(3, "Zapatillas Deportivas", "Zapatillas cómodas para running", 79.99, false),
        Producto(4, "Chaqueta de Cuero", "Chaqueta estilo biker premium", 129.99, false),
        Producto(5, "Sudadera con Capucha", "Sudadera afelpada color gris", 39.99, false),
        Producto(6, "Gorra Casual", "Gorra ajustable estilo urbano", 14.99, false)
    )

    private var searchText = ""

    // null = sin ordenar; true = ascendente; false = descendente
    private var ascending: Boolean? = null

    private val _filteredProducts = MutableLiveData<List<Producto>>(productos)
    val filteredProducts: LiveData<List<Producto>> get() = _filteredProducts

    fun setSearchText(text: String) {
        searchText = text
        update()
    }

    fun toggleSort() {
        ascending = !(ascending ?: false)
        update()
    }

    private fun update() {
        // Se filtra siempre sobre la lista completa
        var result: List<Producto> = if (searchText.isBlank()) {
            productos
        } else {
            productos.filter { it.nombre.contains(searchText, ignoreCase = true) }
        }

        result = when (ascending) {
            true -> result.sortedBy { it.precio }
            false -> result.sortedByDescending { it.precio }
            null -> result
        }

        _filteredProducts.value = result
    }
}
