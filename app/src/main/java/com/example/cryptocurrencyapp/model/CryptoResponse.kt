package com.example.cryptocurrencyapp.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

data class CryptoResponse(
    val data: List<CryptoItem>,
    val status: Status
)

@Serializable
data class Status(
    val timestamp: String,
    @SerializedName("error_code") val errorCode: Int,
    @SerializedName("error_message") val errorMessage: String,
    val elapsed: Int,
    @SerializedName("credit_count") val creditCount: Int
): java.io.Serializable

@Serializable
data class CryptoItem(val id: Int = 0,
                      @SerializedName("name") val name: String = "",
                      @SerializedName("symbol")val symbol: String = "",
                      @SerializedName("slug")val slug: String = "",
                      @SerializedName("cmcRank")val cmcRank: Int? = null,
                      @SerializedName("numMarketPairs")val numMarketPairs: Int? = null,
                      @SerializedName("circulatingSupply")val circulatingSupply: Double? = null,
                      @SerializedName("totalSupply")val totalSupply: Double? = null,
                      @SerializedName("maxSupply")val maxSupply: Double? = null,
                      @SerializedName("infiniteSupply")val infiniteSupply: Boolean = false,
                      @SerializedName("lastUpdated")val lastUpdated: String? = null,
                      @SerializedName("dateAdded")val dateAdded: String? = null,
                      @SerializedName("tags")val tags: List<String> = emptyList(),
                      @SerializedName("platform") val platform: Platform? = null,
                      @SerializedName("selfReportedCirculatingSupply") val selfReportedCirculatingSupply: Int? = null,
                      @SerializedName("selfReportedMarketCap") val selfReportedMarketCap: Double? = null,
                      @SerializedName("quote")val quote: Quote? = null
): java.io.Serializable

@Serializable
data class Platform(
    val id: Int,
    val name: String,
    val symbol: String,
    val slug: String,
    @SerializedName("token_address") val tokenAddress: String
): java.io.Serializable

@Serializable
data class Quote(
    @SerializedName("EUR") val eur: QuoteDetails? = null,
    @SerializedName("BTC") val btc: QuoteDetails? = null
) : java.io.Serializable

@Serializable
data class QuoteDetails(
    @SerializedName("price")val price: Double? = null,
    @SerializedName("volume24h")val volume24h: Double? = null,
    @SerializedName("volume_change_24h") val volumeChange24h: Double? = null,
    @SerializedName("percent_change_1h") val percentChange1h: Double? = null,
    @SerializedName("percent_change_24h") val percentChange24h: Double? = null,
    @SerializedName("percent_change_7d") val percentChange7d: Double? = null,
    @SerializedName("market_cap") val marketCap: Double? = null,
    @SerializedName("market_cap_dominance") val marketCapDominance: Double? = null,
    @SerializedName("fully_diluted_market_cap") val fullyDilutedMarketCap: Double? = null,
    @SerializedName("last_updated") val lastUpdated: String? = null
): java.io.Serializable

data class GlobalStatsResponse(@SerializedName("total_market_cap_usd") val totalMarketCap: Float,
                               @SerializedName("total_24h_volume_usd") val totalDayVolume: Float,
                               @SerializedName("bitcoin_percentage_of_market_cap") val bitcoinMarketCap: Float,
                               @SerializedName("active_currencies") val activeCurrencies: Int,
                               @SerializedName("active_assets") val activeAssets: Int,
                               @SerializedName("active_markets") val activeMarkets: Int,
                               @SerializedName("last_updated") val lastUpdated: Long )

