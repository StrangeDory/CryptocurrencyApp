package com.example.cryptocurrencyapp

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.text.SpannableString
import android.text.style.CharacterStyle
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun getImageUrl(context: Context, symbol: String): String? {
    val gson = Gson()
    val images = context.assets.open("coin_images.json").reader().readText()
    val typeToken = object : TypeToken<Map<String, String>>() {}.type
    val map = gson.fromJson<Map<String, String>>(images, typeToken) // gson struggles with maps

    return map[symbol]
}

/**
 * Modifies the substring inside the string that calls the method with the given styles.
 * Iff the string contains the substring
 * */
fun String.setStyleToSubstring(substring: String, styles: List<CharacterStyle>): SpannableString {
    val spannable = SpannableString(this)
    if (this.contains(substring)) {

        for (style in styles) {
            spannable.setSpan(
                    style,
                    this.indexOf(substring),
                    this.length,
                    0)
        }
    }
    return spannable
}


/**
 * Used to modify the text color of the delta strings within a string.
 * */
@SuppressLint("ResourceAsColor")
fun setDeltaString(context: Context, string: String, delta: Double): SpannableString {
    val value = delta.toString()

    var color: Int
    val plus = R.color.plus_cyan
    val minus = R.color.minus_red
    val default = R.color.text_color

    color = if (delta > 0) plus else if (delta < 0) minus else default
    color = ContextCompat.getColor(context, color)
    val styles = listOf<CharacterStyle>(ForegroundColorSpan(color), StyleSpan(Typeface.BOLD))

    return string.setStyleToSubstring(value, styles)
}

fun setDeltaString(context: Context, @StringRes sourceString: Int, delta: Double): SpannableString {
    return setDeltaString(context, context.getString(sourceString, delta), delta)
}

@SuppressLint("ClickableViewAccessibility", "InflateParams")
fun showPopupWindow(fragmentContext: Context, anchorView: View, textResId: Int) {
    val inflater = fragmentContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val popupView = inflater.inflate(R.layout.popup_layout, null)

    popupView.findViewById<TextView>(R.id.infoTextView).text = fragmentContext.getString(textResId)

    val popupWindow = PopupWindow(
        popupView,
        ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT,
        true
    )

    val location = IntArray(2)
    anchorView.getLocationOnScreen(location)

    popupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY, location[0], location[1])

    val container = popupWindow.contentView.rootView
    val context = popupWindow.contentView.context
    val resources = context.resources
    val width = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        200f,
        resources.displayMetrics
    ).toInt()
    val height = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        150f,
        resources.displayMetrics
    ).toInt()
    popupWindow.width = width
    popupWindow.height = height
    container.setOnTouchListener { _, event ->
        if (event.action == MotionEvent.ACTION_DOWN) {
            popupWindow.dismiss()
        }
        true
    }
}