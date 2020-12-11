package com.example.sweetsShop.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sweetsShop.R
import com.example.sweetsShop.model.Preferences
import com.example.sweetsShop.model.entity.User
import com.example.sweetsShop.model.item.UserType
import com.example.sweetsShop.model.item.exceptionHandler
import com.example.sweetsShop.model.repository.AuthRepository
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class LoginActivity : AppCompatActivity(), CoroutineScope {

    private var job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + exceptionHandler { job = Job() }


    private lateinit var tag: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        tag = intent.getStringExtra("tag") ?: ""
        button.setOnClickListener {
            launch {
                try {
                    val login = login_edit.text.toString()
                    val password = password_edit.text.toString()
                    val userType = when (tag) {
                        "client" -> UserType.CLIENT
                        "operator" -> UserType.OPERATOR
                        "chef" -> UserType.CHEF
                        else -> throw IllegalArgumentException()
                    }
                    val result = withContext(Dispatchers.IO) { AuthRepository().loginUser(userType, login, password) }
                    Preferences.user = result
                    Preferences.userType = userType
                    startMainActivity(userType, result)
                } catch (e: Exception) {
                    Toast.makeText(this@LoginActivity, "Что-то не так", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun startMainActivity(type: UserType, user: User) {
        when (type) {
            UserType.CLIENT -> startActivity(Intent(this, ClientMainActivity::class.java))
            UserType.OPERATOR -> startActivity(Intent(this, OperatorMainActivity::class.java))
            UserType.CHEF -> startActivity(Intent(this,  ChefMainActivity::class.java).putExtra("chef_id", user.id))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
