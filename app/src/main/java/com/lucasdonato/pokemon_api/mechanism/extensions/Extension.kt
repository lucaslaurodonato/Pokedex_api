package com.lucasdonato.pokemon_api.mechanism.extensions

import android.app.Activity
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.view.View
import android.view.View.*
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import com.lucasdonato.pokemon_api.R
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

fun EditText.setDrawableEnd(drawable: Int) {
    this.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable, 0)
}

fun Date.toFormat(format: String) = SimpleDateFormat(format, Locale.getDefault()).format(this)
    .toString()

fun String.toDate(format: String): Date = SimpleDateFormat(format, Locale.getDefault()).parse(this)

fun TextView.setTextUnderline() {
    paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
}

fun Date.toImageName() = "${SimpleDateFormat("yyyyMMddHHmm", Locale.getDefault()).format(this)}.png"

fun EditText.get() = text.toString().trim()

fun EditText.validate() = text.toString().isNotEmpty()

fun View.gone(){
    visibility = GONE
}

fun View.visible(){
    visibility = VISIBLE
}

fun View.invisible(){
    visibility = INVISIBLE
}

fun Activity.toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()

fun View.toast(message: String) = Toast.makeText(context, message, Toast.LENGTH_LONG).show()

fun formattedNumber(number:Int?) : String {
    val formattedNumber = number.toString().padStart(3, '0')
    return "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/$formattedNumber.png"
}

fun convertValue(convert: Int?): String {
    convert?.let {
        return try {
            val value = ((it.toDouble()) / 10)
            val df = DecimalFormat("#.#")
            df.roundingMode = RoundingMode.CEILING
            df.format(value).replace(",", ".").toDouble().toString()
        } catch (e: Exception) {
            "0"
        }
    } ?: run {
        return "0"
    }
}

fun getTypePokemon(type: String) : Int {
    when (type){
        "normal" -> return R.drawable.type_normal
        "fighting" -> return R.drawable.type_fighting
        "flying" -> return R.drawable.type_flying
        "poison" -> return R.drawable.type_poison
        "ground" -> return R.drawable.type_ground
        "rock" ->  return R.drawable.type_rock
        "bug" -> return R.drawable.type_bug
        "ghost" -> return R.drawable.type_ghost
        "steel" -> return R.drawable.type_steel
        "fire" -> return R.drawable.type_fire
        "water" -> return R.drawable.type_water
        "grass" -> return R.drawable.type_grass
        "electric" -> return R.drawable.type_electric
        "psychic" -> return R.drawable.type_psychic
        "ice" -> return R.drawable.type_ice
        "dragon" -> return R.drawable.type_dragon
        "dark" -> return R.drawable.type_dark
        "fairy" -> return R.drawable.type_fairy
        "unknown" -> return R.drawable.type_normal
        "shadow" -> return R.drawable.type_ghost
        else -> return R.drawable.type_normal
    }
}

fun getTypeColor(type: String): Int {
    return when (type) {
        "fighting" -> R.color.fighting
        "flying" -> R.color.flying
        "poison" -> R.color.poison
        "ground" -> R.color.ground
        "rock" -> R.color.rock
        "bug" -> R.color.bug
        "ghost" -> R.color.ghost
        "steel" -> R.color.steel
        "fire" -> R.color.fire
        "water" -> R.color.water
        "grass" -> R.color.grass
        "electric" -> R.color.electric
        "psychic" -> R.color.psychic
        "ice" -> R.color.ice
        "dragon" -> R.color.dragon
        "fairy" -> R.color.fairy
        "dark" -> R.color.dark
        else -> R.color.gray_21
    }
}




