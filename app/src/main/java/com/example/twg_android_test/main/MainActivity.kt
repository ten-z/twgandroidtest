package com.example.twg_android_test.main

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.twg_android_test.R
import com.example.twg_android_test.search.ui.SearchActivity
import com.example.twg_android_test.tools.NetworkModule
import com.example.twg_android_test.tools.PreferenceUtil
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var tvSearch: TextView

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvSearch = findViewById(R.id.tv_search)

        tvSearch.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()

        lifecycleScope.launch {
            val tokenStored = PreferenceUtil.getToken(this@MainActivity).first()
            if (tokenStored == null) {
                loginViewModel.login()
            } else {
                NetworkModule.setXTwgToken(tokenStored)
                Toast.makeText(this@MainActivity, "Token already exists.", Toast.LENGTH_SHORT).show()
            }
        }

        lifecycleScope.launch {
            loginViewModel.tokenState.drop(1).collect { token ->
                if (token != null) {
                    PreferenceUtil.putToken(this@MainActivity, token)
                    NetworkModule.setXTwgToken(token)
                    Toast.makeText(this@MainActivity, "Login successful!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "Login failed!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
