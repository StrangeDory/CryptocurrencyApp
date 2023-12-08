package com.example.cryptocurrencyapp.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.cryptocurrencyapp.R
import com.example.cryptocurrencyapp.databinding.GlobalStatsViewBinding
import com.example.cryptocurrencyapp.model.GlobalStatsResponse
import com.example.cryptocurrencyapp.remote.APIUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GlobalStatsFragment : AppCompatActivity() {

    private lateinit var binding: GlobalStatsViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = GlobalStatsViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.globalToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val cryptoService = APIUtils.cryptoService

        cryptoService.getGlobalStats().enqueue(object : Callback<GlobalStatsResponse> {

            override fun onResponse(call: Call<GlobalStatsResponse>, response: Response<GlobalStatsResponse>) {
                Log.i("globalStats", response.body().toString())
                if (response.isSuccessful) {
                    val global = response.body()
                    if (global != null) {
                        binding.totalMarketCap.text = getString(R.string.price, global.totalMarketCap)
                        binding.totalDayVolume.text = getString(R.string.price, global.totalDayVolume)
                        binding.percentageOfMarketCap.text = "${global.bitcoinMarketCap}%"
                        binding.activeCurrencies.text = global.activeCurrencies.toString()
                        binding.activeAssets.text = global.activeAssets.toString()
                        binding.activeMarkets.text = global.activeMarkets.toString()
                    }
                }
            }

            override fun onFailure(call: Call<GlobalStatsResponse>, t: Throwable) {
                Log.i("globalStats", t.toString())
            }
        })
    }

}