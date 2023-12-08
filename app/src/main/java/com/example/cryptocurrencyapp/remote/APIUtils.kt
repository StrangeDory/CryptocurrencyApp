package com.example.cryptocurrencyapp.remote

object APIUtils {
    private const val BASE_URL: String = "https://pro-api.coinmarketcap.com/v1/"
    val cryptoService: CryptoService = RetrofitClient.getClient(BASE_URL).create(CryptoService::class.java)
}