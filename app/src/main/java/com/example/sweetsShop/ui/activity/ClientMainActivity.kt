package com.example.sweetsShop.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sweetsShop.R
import com.example.sweetsShop.model.Preferences
import com.example.sweetsShop.model.entity.Order
import com.example.sweetsShop.model.item.exceptionHandler
import com.example.sweetsShop.model.repository.OrdersRepository
import kotlinx.android.synthetic.main.activity_client_main.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random

class ClientMainActivity : AppCompatActivity(), CoroutineScope {

    private var job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + exceptionHandler { job = Job() }

    private var products = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_main)
        title = "Создание заказа"
        generate_button.setOnClickListener {
            products = ArrayList(Menu.generateOrder())
            order_text.text = products.joinToString(separator = "\n")
        }
        create_button.setOnClickListener {
            launch {
                try {
                    withContext(Dispatchers.IO) {
                        OrdersRepository().createOrder(Order(
                                products = this@ClientMainActivity.products,
                                clientId = Preferences.user?.id ?: throw IllegalStateException()
                        ))
                    }
                    Toast.makeText(this@ClientMainActivity, "Заказ оформлен", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(this@ClientMainActivity, "Ошибка", Toast.LENGTH_SHORT).show()
                }
            }
        }

        show_button.setOnClickListener {
            startActivity(Intent(this, ClientOrdersActivity::class.java))
        }
    }

    object Menu {
        val items = mutableListOf<String>()

        init {
            create("Безе", 90)
            create("Пирожное Тирамису", 150)
            create("Пирожное Картошка", 50)
            create("Пирожное Эклер", 100)
            create("Круассан", 70)
            create("Макарони", 170)
            create("Пирожное Птичье молоко", 30)
            create("Трубочки вафельные с кремом", 100)
        }

        private fun create(name: String, price: Int) = items.add("$name - $price")

        fun generateOrder(): MutableList<String> {
            val count = Random.nextInt(2) + 2
            return MutableList(count) { items.random() }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
