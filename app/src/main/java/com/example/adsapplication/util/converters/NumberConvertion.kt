package com.example.adsapplication.util.converters

import android.icu.text.DecimalFormat
import android.icu.text.DecimalFormatSymbols

fun Int.toReadable(): String {
    val symbols = DecimalFormatSymbols()

    symbols.groupingSeparator = ' '
    symbols.decimalSeparator = '.'
    val format = DecimalFormat("##,###.##", symbols)

    return format.format(this)
}