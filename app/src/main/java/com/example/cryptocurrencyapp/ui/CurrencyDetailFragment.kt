package com.example.cryptocurrencyapp.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.text.style.StyleSpan
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.cryptocurrencyapp.R
import com.example.cryptocurrencyapp.databinding.FragmentCurrencyDetailBinding
import com.example.cryptocurrencyapp.getImageUrl
import com.example.cryptocurrencyapp.model.CryptoItem
import com.example.cryptocurrencyapp.setDeltaString
import com.example.cryptocurrencyapp.setStyleToSubstring
import com.example.cryptocurrencyapp.showPopupWindow
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString


class CurrencyDetailFragment : Fragment() {

    private lateinit var binding: FragmentCurrencyDetailBinding

    @SuppressLint("ClickableViewAccessibility")
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

        binding.priceCurrency.setCompoundDrawablesRelativeWithIntrinsicBounds(
            null,
            null,
            context?.let { ContextCompat.getDrawable(it, R.drawable.ic_info) },
            null
        )
        binding.priceCurrency.setOnTouchListener { _, event ->
            val drawableEnd = 2

            if (event.action == MotionEvent.ACTION_DOWN) {
                val bounds = binding.priceCurrency.compoundDrawablesRelative
                val drawable = bounds[drawableEnd]

                if (drawable != null && event.rawX >= (binding.priceCurrency.right - drawable.bounds.width())) {
                    showPopupWindow(requireContext(), binding.priceCurrency, R.string.price_info)
                    return@setOnTouchListener true
                }
            }

            return@setOnTouchListener false
        }

        binding.marketCapUsd.setCompoundDrawablesRelativeWithIntrinsicBounds(
            null,
            null,
            context?.let { ContextCompat.getDrawable(it, R.drawable.ic_info) },
            null
        )
        binding.marketCapUsd.setOnTouchListener { _, event ->
            val drawableEnd = 2

            if (event.action == MotionEvent.ACTION_DOWN) {
                val bounds = binding.marketCapUsd.compoundDrawablesRelative
                val drawable = bounds[drawableEnd]

                if (drawable != null && event.rawX >= (binding.marketCapUsd.right - drawable.bounds.width())) {
                    showPopupWindow(requireContext(), binding.marketCapUsd, R.string.market_cap_info)
                    return@setOnTouchListener true
                }
            }

            return@setOnTouchListener false
        }

        binding.percentChange1hr.setCompoundDrawablesRelativeWithIntrinsicBounds(
            null,
            null,
            context?.let { ContextCompat.getDrawable(it, R.drawable.ic_info) },
            null
        )
        binding.percentChange1hr.setOnTouchListener { _, event ->
            val drawableEnd = 2

            if (event.action == MotionEvent.ACTION_DOWN) {
                val bounds = binding.percentChange1hr.compoundDrawablesRelative
                val drawable = bounds[drawableEnd]

                if (drawable != null && event.rawX >= (binding.percentChange1hr.right - drawable.bounds.width())) {
                    showPopupWindow(requireContext(), binding.percentChange1hr, R.string.delta_hour_info)
                    return@setOnTouchListener true
                }
            }

            return@setOnTouchListener false
        }

        binding.percentChange24h.setCompoundDrawablesRelativeWithIntrinsicBounds(
            null,
            null,
            context?.let { ContextCompat.getDrawable(it, R.drawable.ic_info) },
            null
        )
        binding.percentChange24h.setOnTouchListener { _, event ->
            val drawableEnd = 2

            if (event.action == MotionEvent.ACTION_DOWN) {
                val bounds = binding.percentChange24h.compoundDrawablesRelative
                val drawable = bounds[drawableEnd]

                if (drawable != null && event.rawX >= (binding.percentChange24h.right - drawable.bounds.width())) {
                    showPopupWindow(requireContext(), binding.percentChange24h, R.string.delta_day_info)
                    return@setOnTouchListener true
                }
            }

            return@setOnTouchListener false
        }

        binding.percentChange7d.setCompoundDrawablesRelativeWithIntrinsicBounds(
            null,
            null,
            context?.let { ContextCompat.getDrawable(it, R.drawable.ic_info) },
            null
        )
        binding.percentChange7d.setOnTouchListener { _, event ->
            val drawableEnd = 2

            if (event.action == MotionEvent.ACTION_DOWN) {
                val bounds = binding.percentChange7d.compoundDrawablesRelative
                val drawable = bounds[drawableEnd]

                if (drawable != null && event.rawX >= (binding.percentChange7d.right - drawable.bounds.width())) {
                    showPopupWindow(requireContext(), binding.percentChange7d, R.string.delta_week_info)
                    return@setOnTouchListener true
                }
            }

            return@setOnTouchListener false
        }

        return binding.root
    }
}
