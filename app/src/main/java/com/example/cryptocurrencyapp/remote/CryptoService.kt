package com.example.cryptocurrencyapp.remote

import com.example.cryptocurrencyapp.model.CryptoResponse
import com.example.cryptocurrencyapp.model.GlobalStatsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CryptoService {

    /** Parameters of the interface methods can have the following annotations:
     * @Path - variable substitution for the API endpoint
     * @Query - specifies the query key name with the value of the annotated param
     * @Body - payload for the POST call
     * @Header - specify the header with the value of the annotated param
     * */


    @GET("cryptocurrency/listings/latest?")
    fun getCurrencies(@Header("X-CMC_PRO_API_KEY") header: String = "621bef4a-e82c-41ba-8d47-4dbb79481772",
        @Query("start") start: Int = 1,
                   @Query("limit") limit: Int = 100,
                   @Query("convert") convert: String = "EUR")
            : Call<CryptoResponse>

    @GET("global/")
    fun getGlobalStats(@Query("convert") convert: String = "EUR") : Call<GlobalStatsResponse>

}