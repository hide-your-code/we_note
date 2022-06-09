package com.minhdtm.example.we_note.presentations.utils

import android.text.format.DateUtils

fun Long.toTimeAgo(): String =
    DateUtils.getRelativeTimeSpanString(this, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS).toString()
