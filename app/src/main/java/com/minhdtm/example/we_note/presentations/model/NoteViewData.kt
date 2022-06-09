package com.minhdtm.example.we_note.presentations.model

import com.minhdtm.example.we_note.data.model.Note
import javax.inject.Inject

data class NoteViewData(
    val id: String,
    val title: String = "",
    val description: String = "",
    val timeStamp: Long = 0L,
) : ViewData()

class NoteViewDataMapper @Inject constructor() : ViewDataMapper<Note, NoteViewData> {
    override fun mapToModel(viewData: NoteViewData): Note = Note(
        title = viewData.title,
        description = viewData.description,
        timeStamp = viewData.timeStamp,
    )

    override fun mapToViewData(model: Note): NoteViewData = NoteViewData(
        id = model.id ?: "",
        title = model.title ?: "",
        description = model.description ?: "",
        timeStamp = model.timeStamp ?: 0L,
    )
}
