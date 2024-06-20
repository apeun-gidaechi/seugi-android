package com.apeun.gidaechi.model

data class SchoolCodeModel(
    val workspaceId: String,
    val workspaceName: String,
    val workspaceImageUrl: String,
    val studentCount: Int,
    val teacherCount: Int
)

sealed class SchoolCodeSideEffect{
    data object SuccessSearchWorkspace: SchoolCodeSideEffect()
    data class FiledSearchWorkspace(val throwable: Throwable): SchoolCodeSideEffect()

}
