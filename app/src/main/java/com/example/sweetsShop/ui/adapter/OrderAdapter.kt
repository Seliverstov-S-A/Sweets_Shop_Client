package com.example.sweetsShop.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sweetsShop.R
import com.example.sweetsShop.model.entity.Order
import com.example.sweetsShop.ui.view.OrderViewHolder

class OrderAdapter(private val listener: OnItemClickListener) : RecyclerView.Adapter<OrderViewHolder>() {
    val items = mutableListOf<Order>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return OrderViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener { listener.onItemClick(items[position]) }
    }

    interface OnItemClickListener {
        fun onItemClick(item: Order)
    }
}