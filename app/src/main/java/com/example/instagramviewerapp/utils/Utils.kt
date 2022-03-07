package com.example.instagramviewerapp.utils

import com.example.instagramviewerapp.Constants
import com.google.gson.internal.bind.util.ISO8601Utils
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun formatDateFromISO8601(isO8601String: String): String {
        val parsedDate = ISO8601Utils.parse(isO8601String, ParsePosition(0))
        val dateFormat = SimpleDateFormat(Constants.FORMAT_OF_DATE, Locale.getDefault())
        return dateFormat.format(parsedDate)
    }
}