package com.minhdtm.example.we_note.presentations.model

import com.minhdtm.example.we_note.data.model.User
import javax.inject.Inject

data class UserViewData(
    val name: String,
) : ViewData()

class UserViewDataMapper @Inject constructor() : ViewDataMapper<User, UserViewData> {
    override fun mapToModel(viewData: UserViewData): User = User(
        name = viewData.name
    )

    override fun mapToViewData(model: User): UserViewData = UserViewData(
        name = model.name ?: ""
    )
}
