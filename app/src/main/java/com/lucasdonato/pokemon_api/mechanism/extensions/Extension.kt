package com.lucasdonato.pokemon_api.mechanism.extensions

import android.app.Activity
import android.graphics.Paint
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
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

fun Activity.toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()

fun View.toast(message: String) = Toast.makeText(context, message, Toast.LENGTH_LONG).show()

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

