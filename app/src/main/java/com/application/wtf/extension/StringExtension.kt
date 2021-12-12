package com.application.wtf.extension

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.formatDate(): String {
    return SimpleDateFormat("MMM dd, yyyy-HH:mm", Locale.US).format(this)
}

fun String.convertDate(): Date? {
    return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US).parse(this)
}