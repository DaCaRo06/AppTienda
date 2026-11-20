package com.daniel.apptienda.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.daniel.apptienda.databinding.FragmentListScreenBinding
import com.daniel.apptienda.recycler.ProductoAdapter
import com.daniel.apptienda.viewmodels.ListViewModel

class ListFragment : Fragment() {

    interface OnListVisibilityListener {
        fun onListVisibilityChanged(visible: Boolean)
    }

    private var listener: OnListVisibilityListener? = null

    private var _binding: FragmentListScreenBinding? = null
    private val binding get() = _binding!!

    // Mismo ViewModel que MainActivity (lupa y ordenar)
    private val viewModel: ListViewModel by activityViewModels()

    private lateinit var adapter: ProductoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listener = activity as OnListVisibilityListener

        adapter = ProductoAdapter(emptyList())
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        binding.rv.adapter = adapter

        viewModel.filteredProducts.observe(viewLifecycleOwner) { productos ->
            adapter.updateList(productos)
        }
    }

    override fun onResume() {
        super.onResume()
        listener?.onListVisibilityChanged(true)
    }

    override fun onPause() {
        super.onPause()
        listener?.onListVisibilityChanged(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        listener = null
        _binding = null
    }
}
