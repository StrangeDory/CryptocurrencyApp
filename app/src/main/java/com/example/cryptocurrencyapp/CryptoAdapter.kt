package com.example.cryptocurrencyapp

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.text.style.StyleSpan
import android.util.JsonWriter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptocurrencyapp.model.CryptoItem
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.json.JSONObject
import org.json.JSONStringer
import java.io.Serializable


class CryptoAdapter : RecyclerView.Adapter<CryptoAdapter.CryptoViewHolder>(
) {
    private var list: List<CryptoItem> = ArrayList(0)
    private lateinit var navController: NavController

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(items: List<CryptoItem>?) {
        list = items ?: list
        notifyDataSetChanged()
    }

    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        holder.bindCryptoItem(list[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_crypto_cell, parent, false)

        return CryptoViewHolder(view)
    }


    inner class CryptoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val coin_iv: ImageView = itemView.findViewById(R.id.coin_iv)

        fun bindCryptoItem(cryptoItem: CryptoItem) {
            val ctx = itemView.context
            val symbol = cryptoItem.symbol

            val name = "$symbol | ${cryptoItem.name}".setStyleToSubstring(symbol, listOf(StyleSpan(Typeface.BOLD)))
            val dayDelta = setDeltaString(ctx, R.string.day_delta, cryptoItem.quote?.eur?.percentChange24h!!)
            val weekDelta = cryptoItem.quote.eur.percentChange7d?.let {
                setDeltaString(ctx, R.string.week_delta,
                    it
                )
            }

            val url = getImageUrl(ctx, symbol)
            if (url != null) {
                Glide.with(ctx).load(url).into(coin_iv)
            } else {
                Glide.with(ctx).clear(coin_iv)
                coin_iv.setImageResource(R.drawable.ic_coin_generic)
            }

            itemView.findViewById<TextView>(R.id.coin_name).text = name
            itemView.findViewById<TextView>(R.id.coin_price).text = ctx.getString(R.string.price_eur, cryptoItem.quote.eur.price)
            itemView.findViewById<TextView>(R.id.coin_day_delta).text = dayDelta
            itemView.findViewById<TextView>(R.id.coin_week_delta).text = weekDelta

            itemView.setOnClickListener { _ ->
                val args = Bundle()
                args.putString("item", Json.encodeToString(cryptoItem))
                navController.navigate(R.id.nav_currency_detail, args)
            }
        }
    }
}