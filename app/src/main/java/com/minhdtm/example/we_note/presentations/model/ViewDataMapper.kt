package com.minhdtm.example.we_note.presentations.model

import com.minhdtm.example.we_note.data.model.Model

interface ViewDataMapper<M : Model, VD : ViewData> {
    fun mapToModel(viewData: VD): M

    fun mapToViewData(model: M): VD
}
