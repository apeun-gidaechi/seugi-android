package com.seugi.network.message.response

sealed class CatSeugiResponse(
    @Transient open val type: String,
){
    data class Meal(
        override val type: String,
        val id: Long,
        val workspaceId: String,
        val mealType: String,
        val menu: List<String>,
        val calorie: String,
        val mealInfo: List<String>,
        val mealDate: List<String>
    ): CatSeugiResponse(type)


    data class Timetable(
        override val type: String,
        val id: Long,
        val workspaceId: String,
        val grade: String,
        val className: String,
        val time: String,
        val subject: String,
        val date: String
    ):CatSeugiResponse(type)

    data class ETC(
        override val type: String,
        val data: String
    ): CatSeugiResponse(type)
}
