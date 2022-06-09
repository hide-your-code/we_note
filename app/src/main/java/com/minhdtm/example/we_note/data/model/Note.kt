package com.minhdtm.example.we_note.data.model

data class Note(
    val id: String? = "",
    val title: String? = "",
    val description: String? = "",
    val timeStamp: Long? = 0L,
) : Model()
