package com.seugi.join.feature.model

data class SchoolCodeModel(
    val workspaceId: String = "",
    val workspaceName: String = "",
    val workspaceImageUrl: String = "",
    val studentCount: Int = 0,
    val teacherCount: Int = 0,
)

sealed class SchoolCodeSideEffect {
    data object SuccessSearchWorkspace : SchoolCodeSideEffect()
    data class FiledSearchWorkspace(val throwable: Throwable) : SchoolCodeSideEffect()
}
