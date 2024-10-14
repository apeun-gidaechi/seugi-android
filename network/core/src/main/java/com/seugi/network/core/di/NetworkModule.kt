package com.seugi.network.core.di

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import com.seugi.common.exception.UnauthorizedException
import com.seugi.common.utiles.SeugiActivityStarter
import com.seugi.local.room.dao.TokenDao
import com.seugi.local.room.model.TokenEntity
import com.seugi.network.core.SeugiUrl
import com.seugi.network.core.response.BaseResponse
import com.seugi.network.core.response.safeResponse
import com.seugi.network.core.utiles.LocalDateTimeTypeAdapter
import com.seugi.network.core.utiles.removeBearer
import com.seugi.stompclient.Stomp
import com.seugi.stompclient.StompClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerAuthProvider
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.gson.gson
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(activityStarter: SeugiActivityStarter, tokenDao: TokenDao): HttpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            gson {
                registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeTypeAdapter())
                setPrettyPrinting()
                setLenient()
            }
        }
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.i("HttpClient", message)
                }
            }
            level = LogLevel.ALL
        }
        install(HttpTimeout) {
            requestTimeoutMillis = TIME_OUT
            connectTimeoutMillis = TIME_OUT
            socketTimeoutMillis = TIME_OUT
        }
        install(Auth) {
            val auth = this
            bearer {
                loadTokens {
                    try {
                        val token = tokenDao.getToken()
                        BearerTokens(removeBearer(token?.token ?: ""), removeBearer(token?.refreshToken ?: ""))
                    } catch (e: IndexOutOfBoundsException) {
                        throw UnauthorizedException("${e.message}")
                    }
                }
                refreshTokens {
                    try {
                        val refreshToken: String = tokenDao.getToken()?.refreshToken ?: ""
                        val accessToken = client.get(SeugiUrl.Member.REFRESH) {
                            markAsRefreshTokenRequest()
                            parameter("token", removeBearer(refreshToken))
                        }.body<BaseResponse<String>>().safeResponse()
                        tokenDao.insert(
                            TokenEntity(
                                token = accessToken,
                                refreshToken = refreshToken,
                            ),
                        )

                        BearerTokens(removeBearer(accessToken), "")
                    } catch (e: Exception) {
                        e.printStackTrace()
                        coroutineScope {
                            async {
                                tokenDao.deleteToken()
                            }.await()
                            auth.providers
                                .filterIsInstance<BearerAuthProvider>()
                                .singleOrNull()?.clearToken()
                            activityStarter.reStartActivity()
                            throw UnauthorizedException("")
                        }
                    }
                }
                sendWithoutRequest {
                    when (it.url.toString()) {
                        SeugiUrl.Auth.EMAIL_SIGN_UP -> false
                        SeugiUrl.Auth.EMAIL_SIGN_IN -> false
                        SeugiUrl.Auth.GET_CODE -> false
                        SeugiUrl.Oauth.GOOGLE_AUTHENTICATE -> false
                        else -> {
                            when (it.url.toString().split("?")[0]) {
                                SeugiUrl.Member.REFRESH -> false
                                else -> true
                            }
                        }
                    }
                }
            }
        }
        defaultRequest {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    @Provides
    @Singleton
    fun providesOkhttpClient(): OkHttpClient = OkHttpClient.Builder()
        .readTimeout(99999, TimeUnit.SECONDS)
        .writeTimeout(99999, TimeUnit.SECONDS)
        .connectTimeout(99999, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
        .build()

    @Provides
    @Singleton
    fun providesStompClient(okHttpClient: OkHttpClient): StompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, SeugiUrl.Message.HANDSHAKE, null, okHttpClient)
}

private tailrec fun Context?.activity(): Activity? = this as? Activity
    ?: (this as? ContextWrapper)?.baseContext?.activity()

private const val TIME_OUT = 60_000L
