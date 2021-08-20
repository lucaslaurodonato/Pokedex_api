package com.lucasdonato.pokemon_api.mechanism.utils

import android.graphics.Color
import android.graphics.drawable.GradientDrawable

class GradientUtil {
    companion object {

        fun setBackgroundGradient(colors: List<String>): GradientDrawable {

            return GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                intArrayOf(Color.parseColor(colors[0]), Color.parseColor(colors[1]))
            )

        }

        fun getGradientColor(type: String): String {
            return when (type) {
                "fighting" -> "#9F422A"
                "flying" -> "#90B1C5"
                "poison" -> "#642785"
                "ground" -> "#AD7235"
                "rock" -> "#4B190E"
                "bug" -> "#179A55"
                "ghost" -> "#363069"
                "steel" -> "#5C756D"
                "fire" -> "#B22328"
                "water" -> "#2648DC"
                "grass" -> "#007C42"
                "electric" -> "#E0E64B"
                "psychic" -> "#AC296B"
                "ice" -> "#7ECFF2"
                "dragon" -> "#378A94"
                "fairy" -> "#9E1A44"
                "dark" -> "#040706"
                else -> "#FFFFFF"
            }
        }

    }
}