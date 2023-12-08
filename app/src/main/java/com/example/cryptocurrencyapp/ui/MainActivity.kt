package com.example.cryptocurrencyapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptocurrencyapp.CryptoAdapter
import com.example.cryptocurrencyapp.R
import com.example.cryptocurrencyapp.databinding.ActivityMainBinding
import com.example.cryptocurrencyapp.model.CryptoItem
import com.example.cryptocurrencyapp.model.CryptoResponse
import com.example.cryptocurrencyapp.remote.APIUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.cryptoToolbar)

        val navController = findNavController(R.id.rootContainer)
        navController.graph = navController.navInflater.inflate(R.navigation.navigation)
        navController.graph.setStartDestination(R.id.nav_main)
        navController.navigate(R.id.nav_main)
    }
}
