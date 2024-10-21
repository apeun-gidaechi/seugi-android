package com.seugi.data.workspace.mapper

import com.seugi.data.workspace.model.RetrieveMemberModel
import com.seugi.network.workspace.response.RetrieveMemberResponse

fun List<RetrieveMemberResponse>.toModels() = this.map {
    it.toModel()
}

fun RetrieveMemberResponse.toModel(): RetrieveMemberModel = RetrieveMemberModel(
    id = id,
    email = email,
    name = name,
    birth = birth,
    picture = picture,
)
