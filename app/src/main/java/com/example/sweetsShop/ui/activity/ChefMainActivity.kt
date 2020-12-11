package com.example.sweetsShop.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.sweetsShop.R
import com.example.sweetsShop.extentions.str
import com.example.sweetsShop.model.entity.Order
import com.example.sweetsShop.model.entity.User
import com.example.sweetsShop.model.item.OrderStatus
import com.example.sweetsShop.model.item.exceptionHandler
import com.example.sweetsShop.model.repository.ChefsRepository
import com.example.sweetsShop.model.repository.OrdersRepository
import com.example.sweetsShop.ui.adapter.OrderAdapter
import kotlinx.android.synthetic.main.activity_chef_main.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class ChefMainActivity : AppCompatActivity(), OrderAdapter.OnItemClickListener, CoroutineScope {

    private var job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + exceptionHandler { job = Job() }

    override fun onItemClick(item: Order) {
        showStatusSelectionDialog(item.status) { newStatus ->
            launch {
                try {
                    item.status = newStatus
                    withContext(Dispatchers.IO) { OrdersRepository().updateOrder(item) }
                    updateOrders()
                } catch (e: Exception) {
                    Toast.makeText(this@ChefMainActivity, "Ошибка", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private val adapter = OrderAdapter(this)
    private var chef: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chef_main)
        title = "Список заказов"
        val copyriterId = intent.getLongExtra("chef_id", -1)
        launch {
            try {
                chef = withContext(Dispatchers.IO) { ChefsRepository().get(copyriterId) }
                recycler.adapter = adapter
                updateOrders()
            } catch (e: Exception) {
                Toast.makeText(this@ChefMainActivity, "Ошибка", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateOrders() {
        val copyriter = chef ?: return
        launch {
            try {
                val orders = withContext(Dispatchers.IO) {
                    OrdersRepository().getAllOrders().filter { it.status == OrderStatus.WAIT_WORKING || it.status == OrderStatus.IN_WORKING }
                }
                adapter.items.clear()
                adapter.items.addAll(orders)
                adapter.notifyDataSetChanged()
            } catch (e: Exception) {
                Toast.makeText(this@ChefMainActivity, "Ошибка", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showStatusSelectionDialog(currentStatus: OrderStatus, block: (newStatus: OrderStatus) -> Unit) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Изменение статуса заказа")

        val statuses = when (currentStatus) {
            OrderStatus.WAIT_WORKING -> arrayOf(OrderStatus.IN_WORKING)
            OrderStatus.IN_WORKING -> arrayOf(OrderStatus.COMPLETE)
            else -> {
                Toast.makeText(this, "Смена статуса недоступна", Toast.LENGTH_SHORT).show()
                return
            }
        }
        builder.setItems(statuses.map(OrderStatus::str).toTypedArray()) { _, which ->
            block(statuses[which])
        }

        val dialog = builder.create()
        dialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
