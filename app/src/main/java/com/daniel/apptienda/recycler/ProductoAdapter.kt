package com.daniel.apptienda.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daniel.apptienda.R
import com.daniel.apptienda.databinding.ItemProductoBinding

class ProductoAdapter(private var items: List<Producto>) :
    RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val binding = ItemProductoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateList(newItems: List<Producto>) {
        items = newItems
        notifyDataSetChanged()
    }

    class ProductoViewHolder(private val binding: ItemProductoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Producto) {
            binding.tvNombre.text = data.nombre
            binding.tvDescripcion.text = data.descripcion
            binding.tvPrecio.text = binding.root.context.getString(R.string.price_format, data.precio.toString())

            if (data.isFav) {
                binding.ivFav.setImageResource(R.drawable.ic_favorite)
            } else {
                binding.ivFav.setImageResource(R.drawable.ic_favorite_border)
            }

            binding.ivFav.setOnClickListener {
                if (data.isFav) {
                    binding.ivFav.setImageResource(R.drawable.ic_favorite_border)
                } else {
                    binding.ivFav.setImageResource(R.drawable.ic_favorite)
                }
                data.isFav = !data.isFav
            }
        }
    }
}
