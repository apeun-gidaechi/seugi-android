package com.seugi.stompclient.provider;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.seugi.stompclient.StompException;
import com.seugi.stompclient.dto.LifecycleEvent;

import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class OkHttpConnectionProvider extends AbstractConnectionProvider {

    public static final String TAG = "OkHttpConnProvider";

    private final String mUri;
    @NonNull
    private final Map<String, String> mConnectHttpHeaders;
    private final OkHttpClient mOkHttpClient;

    @Nullable
    private WebSocket openSocket;

    public OkHttpConnectionProvider(String uri, @Nullable Map<String, String> connectHttpHeaders, OkHttpClient okHttpClient) {
        super();
        mUri = uri;
        mConnectHttpHeaders = connectHttpHeaders != null ? connectHttpHeaders : new HashMap<>();
        mOkHttpClient = okHttpClient;
    }

    @Override
    public void rawDisconnect() {
        if (openSocket != null) {
            openSocket.close(1000, "");
        }
    }

    @Override
    protected void createWebSocketConnection() {
        Request.Builder requestBuilder = new Request.Builder()
                .url(mUri);

        addConnectionHeadersToBuilder(requestBuilder, mConnectHttpHeaders);

        openSocket = mOkHttpClient.newWebSocket(requestBuilder.build(),
                new WebSocketListener() {
                    @Override
                    public void onOpen(WebSocket webSocket, @NonNull Response response) {
                        LifecycleEvent openEvent = new LifecycleEvent(LifecycleEvent.Type.OPENED);

                        TreeMap<String, String> headersAsMap = headersAsMap(response);

                        openEvent.setHandshakeResponseHeaders(headersAsMap);
                        emitLifecycleEvent(openEvent);
                    }

                    @Override
                    public void onMessage(WebSocket webSocket, String text) {
                        Log.d(TAG, "onMessage: " + text);
                        emitMessage(text);
                    }

                    @Override
                    public void onMessage(WebSocket webSocket, @NonNull ByteString bytes) {
                        Log.d(TAG, "onMessage: " + bytes.utf8());
                        emitMessage(bytes.utf8());
                    }

                    @Override
                    public void onClosed(WebSocket webSocket, int code, String reason) {
                        Log.d(TAG, "onClosed: " + reason);
                        openSocket = null;
                        emitLifecycleEvent(new LifecycleEvent(LifecycleEvent.Type.CLOSED));
                    }

                    @Override
                    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                        // in OkHttp, a Failure is equivalent to a JWS-Error *and* a JWS-Close
                        Log.e(TAG, "onFailure: " + (t instanceof SocketException));
                        if (t instanceof SocketException) {
                            emitLifecycleEvent(new LifecycleEvent(LifecycleEvent.Type.ERROR, new SocketException(t.getMessage())));
                        } else {
                            emitLifecycleEvent(new LifecycleEvent(LifecycleEvent.Type.ERROR, new StompException(t.getMessage() == null? "": t.getMessage())));
                        }
                        openSocket = null;
                        emitLifecycleEvent(new LifecycleEvent(LifecycleEvent.Type.CLOSED));
                    }

                    @Override
                    public void onClosing(final WebSocket webSocket, final int code, final String reason) {
                        webSocket.close(code, reason);
                    }
                }

        );
    }

    @Override
    protected void rawSend(String stompMessage) {
        openSocket.send(stompMessage);
    }

    @Nullable
    @Override
    public Object getSocket() {
        return openSocket;
    }

    @NonNull
    private TreeMap<String, String> headersAsMap(@NonNull Response response) {
        TreeMap<String, String> headersAsMap = new TreeMap<>();
        Headers headers = response.headers();
        for (String key : headers.names()) {
            headersAsMap.put(key, headers.get(key));
        }
        return headersAsMap;
    }

    private void addConnectionHeadersToBuilder(@NonNull Request.Builder requestBuilder, @NonNull Map<String, String> mConnectHttpHeaders) {
        for (Map.Entry<String, String> headerEntry : mConnectHttpHeaders.entrySet()) {
            requestBuilder.addHeader(headerEntry.getKey(), headerEntry.getValue());
        }
    }
}