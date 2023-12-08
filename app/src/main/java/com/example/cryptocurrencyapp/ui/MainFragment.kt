package com.example.cryptocurrencyapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.cryptocurrencyapp.CryptoAdapter
import com.example.cryptocurrencyapp.databinding.MainFragmentBinding
import com.example.cryptocurrencyapp.model.CryptoItem
import com.example.cryptocurrencyapp.model.CryptoResponse
import com.example.cryptocurrencyapp.remote.APIUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragment: Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding: MainFragmentBinding
    private val adapter = CryptoAdapter()
    private val cryptoService = APIUtils.cryptoService
    val cryptoList = mutableListOf<CryptoItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        with(binding){
            cryptocurrencyRecyclerView.adapter = adapter
            cryptocurrencyRecyclerView.layoutManager = LinearLayoutManager(context)
            cryptocurrencyRecyclerView.setHasFixedSize(true)
            cryptocurrencyRecyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

            swipeLayout.setOnRefreshListener(this@MainFragment)

            currencySearchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText != null) {
                        filter(newText)
                    }
                    return false
                }

            })
        }
        adapter.setNavController(findNavController())
        loadCurrencies()
        return binding.root
    }

    private fun filter(text: String) {
        val filteredList = mutableListOf<CryptoItem>()

        for (item in cryptoList) {
            if (item.name.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item)
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(context, "No Data Found...", Toast.LENGTH_SHORT).show()
        }
        adapter.updateList(filteredList)
    }

    private fun loadCurrencies() {

        cryptoService.getCurrencies().enqueue(object : Callback<CryptoResponse> {

            override fun onResponse(
                call: Call<CryptoResponse>,
                response: Response<CryptoResponse>
            ) {
                if (response.isSuccessful) {
                        cryptoList.clear()
                        response.body()?.let { cryptoList.addAll(it.data) }
                        adapter.updateList(cryptoList)
                    Log.i("onResponse", response.body().toString())
                } else {
                    Log.i("StatusCode", "${response.code()}")
                }
                binding.swipeLayout.isRefreshing = false
            }

            override fun onFailure(call: Call<CryptoResponse>, t: Throwable) {
                Log.i("error get currencies", "${call}, ${t}")
                binding.swipeLayout.isRefreshing = false
            }

        })
    }

    override fun onRefresh() {
        loadCurrencies()
    }
}