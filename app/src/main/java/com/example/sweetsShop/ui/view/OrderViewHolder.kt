package com.example.sweetsShop.ui.view

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sweetsShop.extentions.str
import com.example.sweetsShop.model.entity.Order
import kotlinx.android.synthetic.main.item_order.view.*

class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(order: Order) {
        itemView.product_list.removeAllViewsInLayout()

        order.products.forEach {
            itemView.product_list.addView(TextView(itemView.context).apply { text = it })
        }
        itemView.status_text.text = order.status.str
    }
}