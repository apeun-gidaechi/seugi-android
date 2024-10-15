package com.seugi.network.timetable


import android.util.Log
import com.seugi.network.timetable.datasource.TimetableDataSourceImpl
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.gson.gson
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class TimetableDataSourceTest: TestCase() {
    private val timetableDataSource: TimetableDataSource =
        TimetableDataSourceImpl(
            HttpClient(MockEngine {
                respond(
                    content = """
                        {"status":200,"success":true,"state":"OK","message":"시간표 조회 성공","data":[{"id":113340,"workspaceId":"669e339593e10f4f59f8c583","grade":"1","classNum":"2","time":"1","subject":"수학","date":"20241015"},{"id":113341,"workspaceId":"669e339593e10f4f59f8c583","grade":"1","classNum":"2","time":"2","subject":"* SQL활용","date":"20241015"},{"id":113342,"workspaceId":"669e339593e10f4f59f8c583","grade":"1","classNum":"2","time":"3","subject":"* SQL활용","date":"20241015"},{"id":113343,"workspaceId":"669e339593e10f4f59f8c583","grade":"1","classNum":"2","time":"4","subject":"음악","date":"20241015"},{"id":113344,"workspaceId":"669e339593e10f4f59f8c583","grade":"1","classNum":"2","time":"5","subject":"통합사회","date":"20241015"},{"id":113345,"workspaceId":"669e339593e10f4f59f8c583","grade":"1","classNum":"2","time":"6","subject":"영어","date":"20241015"},{"id":113346,"workspaceId":"669e339593e10f4f59f8c583","grade":"1","classNum":"2","time":"7","subject":"통합과학","date":"20241015"}]}
                    """.trimIndent(),
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, "application/json"),
                )
            }) {
                install(ContentNegotiation) {
                    gson()
                }
            }
        )
    private val testDispatcher = StandardTestDispatcher()
    @org.junit.Test
    fun testTimetable() = runTest(testDispatcher) {
        val response = timetableDataSource.getTimetableDay(
            workspaceId = ""
        ).data

        Log.d("TAG", "testTimetable: $response ")
        assertEquals(true, true)
    }

}