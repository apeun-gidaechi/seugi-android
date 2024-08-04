package com.seugi.stompclient;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.seugi.stompclient.provider.OkHttpConnectionProvider;
import com.seugi.stompclient.provider.WebSocketsConnectionProvider;

import java.util.Map;

import okhttp3.OkHttpClient;

public class Stomp {

    public static StompClient over(@NonNull ConnectionProvider connectionProvider, String uri) {
        return over(connectionProvider, uri, null, null);
    }

    /**
     * @param connectionProvider connectionProvider method
     * @param uri                URI to connect
     * @param connectHttpHeaders HTTP headers, will be passed with handshake query, may be null
     * @return StompClient for receiving and sending messages. Call #StompClient.connect
     */
    public static StompClient over(@NonNull ConnectionProvider connectionProvider, String uri, Map<String, String> connectHttpHeaders) {
        return over(connectionProvider, uri, connectHttpHeaders, null);
    }

    /**
     * {@code webSocketClient} can accept the following type of clients:
     * <ul>
     * <li>{@code org.java_websocket.WebSocket}: cannot accept an existing client</li>
     * <li>{@code okhttp3.WebSocket}: can accept a non-null instance of {@code okhttp3.OkHttpClient}</li>
     * </ul>
     *
     * @param connectionProvider connectionProvider method
     * @param uri                URI to connect
     * @param connectHttpHeaders HTTP headers, will be passed with handshake query, may be null
     * @param okHttpClient       Existing client that will be used to open the WebSocket connection, may be null to use default client
     * @return StompClient for receiving and sending messages. Call #StompClient.connect
     */
    public static StompClient over(@NonNull ConnectionProvider connectionProvider, String uri, @Nullable Map<String, String> connectHttpHeaders, @Nullable OkHttpClient okHttpClient) {
        if (connectionProvider == ConnectionProvider.JWS) {
            if (okHttpClient != null) {
                throw new IllegalArgumentException("You cannot pass an OkHttpClient when using JWS. Use null instead.");
            }
            return createStompClient(new WebSocketsConnectionProvider(uri, connectHttpHeaders));
        }

        if (connectionProvider == ConnectionProvider.OKHTTP) {
            return createStompClient(new OkHttpConnectionProvider(uri, connectHttpHeaders, (okHttpClient == null) ? new OkHttpClient() : okHttpClient));
        }

        throw new IllegalArgumentException("ConnectionProvider type not supported: " + connectionProvider.toString());
    }

    private static StompClient createStompClient(com.seugi.stompclient.provider.ConnectionProvider connectionProvider) {
        return new StompClient(connectionProvider);
    }

    public enum ConnectionProvider {
        OKHTTP, JWS
    }
}