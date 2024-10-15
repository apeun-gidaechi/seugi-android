package com.seugi.data.core.model

data class ProfileModel(
    val status: String,
    val member: UserModel,
    val workspaceId: String,
    val nick: String,
    val spot: String,
    val belong: String,
    val phone: String,
    val wire: String,
    val location: String,
    val permission: WorkspacePermissionModel,
    val schGrade: Int,
    val schClass: Int,
    val schNumber: Int,
) {
    val nameAndNick
        get() =
            if (nick.isEmpty()) {
                member.name
            } else {
                "${member.name} ($nick)"
            }
}
