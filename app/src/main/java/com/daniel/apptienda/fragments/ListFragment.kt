package com.daniel.apptienda.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.daniel.apptienda.databinding.FragmentListScreenBinding
import com.daniel.apptienda.recycler.Producto
import com.daniel.apptienda.recycler.ProductoAdapter

class ListFragment : Fragment() {

    private var _binding: FragmentListScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ProductoAdapter
    private val fullList = listOf(
        Producto(1, "Camiseta Básica", "Camiseta de algodón 100% color blanco", 19.99, false),
        Producto(2, "Pantalón Vaquero", "Pantalón denim corte clásico", 49.99, false),
        Producto(3, "Zapatillas Deportivas", "Zapatillas cómodas para running", 79.99, false),
        Producto(4, "Chaqueta de Cuero", "Chaqueta estilo biker premium", 129.99, false),
        Producto(5, "Sudadera con Capucha", "Sudadera afelpada color gris", 39.99, false),
        Producto(6, "Gorra Casual", "Gorra ajustable estilo urbano", 14.99, false)
    )
    private var currentList = fullList.toMutableList()
    private var isAscending = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ProductoAdapter(currentList)
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        binding.rv.adapter = adapter
    }

    fun filterProducts(query: String?) {
        val filtered = if (query.isNullOrBlank()) {
            fullList
        } else {
            fullList.filter { it.nombre.contains(query, ignoreCase = true) }
        }
        currentList = filtered.toMutableList()
        adapter.updateList(currentList)
    }

    fun toggleSort() {
        isAscending = !isAscending
        currentList = if (isAscending) {
            currentList.sortedBy { it.precio }.toMutableList()
        } else {
            currentList.sortedByDescending { it.precio }.toMutableList()
        }
        adapter.updateList(currentList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
