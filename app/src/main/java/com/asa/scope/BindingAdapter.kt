package com.asa.scope

import android.content.Context
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter

@BindingAdapter("app:popularityColor")
fun popularityColor(view: TextView, popularity: MainViewModel.Popularity) {
    val color = getColorPopularity(view.context,popularity)
    view.setTextColor(color)
}

@BindingAdapter("app:popularityText")
fun popularityText(view: TextView, popularity: MainViewModel.Popularity) {
    view.text = getTextPopularity(popularity)
}

fun getTextPopularity(popularity: MainViewModel.Popularity): String? {
    return when (popularity) {
        MainViewModel.Popularity.NORMAL -> "😀"
        MainViewModel.Popularity.POPULAR -> "🔥"
        MainViewModel.Popularity.STAR -> "👑"
    }
}

fun getColorPopularity(context: Context, popularity: MainViewModel.Popularity): Int {
    return when (popularity) {
        MainViewModel.Popularity.NORMAL -> ContextCompat.getColor(context, R.color.normal)
        MainViewModel.Popularity.POPULAR -> ContextCompat.getColor(context, R.color.popular)
        MainViewModel.Popularity.STAR -> ContextCompat.getColor(context, R.color.star)
    }
}
