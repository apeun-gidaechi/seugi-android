package com.seugi.workspacedetail.feature.workspacemember.model

sealed interface WorkspaceMemberSideEffect {
    data class SuccessCreateRoom(val roomUid: String): WorkspaceMemberSideEffect
    data object FailedCreateRoom: WorkspaceMemberSideEffect
}