package com.seugi.network.core.di

import android.util.Log
import com.seugi.common.exception.UnauthorizedException
import com.seugi.common.model.Result
import com.seugi.data.token.TokenRepository
import com.seugi.network.core.SeugiUrl
import com.seugi.network.core.utiles.LocalDateTimeTypeAdapter
import com.seugi.network.core.utiles.removeBearer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.gson.gson
import kotlinx.coroutines.flow.collectLatest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(tokenRepository: TokenRepository): HttpClient = HttpClient(CIO) {
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
            bearer {
                loadTokens {
                    try {
                        var accessToken: String = ""
                        tokenRepository.getToken().collectLatest {
                            when (it) {
                                is Result.Success -> {
                                    accessToken = removeBearer(it.data.accessToken.toString())
                                }
                                is Result.Error -> {}
                                is Result.Loading -> {}
                            }
                        }
                        BearerTokens(removeBearer(accessToken), "")
                    } catch (e: IndexOutOfBoundsException) {
                        throw UnauthorizedException("${e.message}")
                    }
                }
                sendWithoutRequest {
                    when (it.url.toString()) {
                        SeugiUrl.Auth.EMAIL_SIGN_UP -> false
                        SeugiUrl.Auth.EMAIL_SIGN_IN -> false
                        SeugiUrl.Auth.GET_CODE -> false
                        else -> true
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
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
        .build()

    @Provides
    fun providesStompClient(okHttpClient: OkHttpClient): StompClient =
        Stomp.over(Stomp.ConnectionProvider.OKHTTP, SeugiUrl.Message.HANDSHAKE, null, okHttpClient)
}

private const val TIME_OUT = 60_000L
