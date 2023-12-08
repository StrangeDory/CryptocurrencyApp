package com.example.cryptocurrencyapp.ui

import android.graphics.Typeface
import android.os.Bundle
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.cryptocurrencyapp.R
import com.example.cryptocurrencyapp.databinding.FragmentCurrencyDetailBinding
import com.example.cryptocurrencyapp.getImageUrl
import com.example.cryptocurrencyapp.model.CryptoItem
import com.example.cryptocurrencyapp.setDeltaString
import com.example.cryptocurrencyapp.setStyleToSubstring
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString


class CurrencyDetailFragment : Fragment() {

    private lateinit var binding: FragmentCurrencyDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrencyDetailBinding.inflate(inflater, container, false)

        var cryptoItem = CryptoItem()
        if (arguments != null && requireArguments().containsKey("item")) {
            cryptoItem = Json.decodeFromString<CryptoItem>(requireArguments().getString("item")!!)
        }

        val url = context?.let { getImageUrl(it, cryptoItem.symbol) }
        if (url != null) {
            Glide.with(this).load(url).into(binding.imageView)
        } else {
            Glide.with(binding.imageView).clear(binding.imageView)
            binding.imageView.setImageResource(R.drawable.ic_coin_generic)
        }

        val styles = listOf(StyleSpan(Typeface.BOLD))

        binding.coinTitle.text = (cryptoItem.name + " | " + cryptoItem.name).setStyleToSubstring(cryptoItem.symbol, styles)

        binding.priceCurrency.text = getString(R.string.price, cryptoItem.quote?.eur?.price)
        binding.marketCapUsd.text = getString(R.string.market_cap, cryptoItem.quote?.eur?.marketCap)
        binding.percentChange1hr.text = context?.let { setDeltaString(it, "Delta (last hour): ${cryptoItem.quote?.eur?.percentChange1h}%", cryptoItem.quote?.eur?.percentChange1h!!) }
        binding.percentChange24h.text = context?.let { setDeltaString(it,"Delta (last day): ${cryptoItem.quote?.eur?.percentChange24h}%", cryptoItem.quote?.eur?.percentChange24h!!) }
        binding.percentChange7d.text = context?.let { setDeltaString(it,"Delta (last week): ${cryptoItem.quote?.eur?.percentChange7d}%", cryptoItem.quote?.eur?.percentChange7d!!) }

        binding.buttonYes.setOnClickListener { _ ->

        }

        return binding.root
    }
}
