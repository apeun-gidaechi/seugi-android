package com.seugi.workspacedetail.feature.invitemember.model

data class DialogModel(
    val title: String = "",
    val content: String? = null,
    val lText: String = "",
    val rText: String = "",
    val icon: Int? = null,
    val onClick: () -> Unit = {},
)
