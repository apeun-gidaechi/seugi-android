package com.seugi.stompclient.provider;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.seugi.stompclient.dto.LifecycleEvent;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public abstract class AbstractConnectionProvider implements ConnectionProvider {

    private static final String TAG = AbstractConnectionProvider.class.getSimpleName();

    @NonNull
    private final PublishSubject<LifecycleEvent> lifecycleStream;
    @NonNull
    private final PublishSubject<String> messagesStream;

    public AbstractConnectionProvider() {
        lifecycleStream = PublishSubject.create();
        messagesStream = PublishSubject.create();
    }

    @NonNull
    @Override
    public Observable<String> messages() {
        return messagesStream.startWith(initSocket().toObservable());
    }

    /**
     * Simply close socket.
     * <p>
     * For example:
     * <pre>
     * webSocket.close();
     * </pre>
     */
    protected abstract void rawDisconnect();

    @Override
    public Completable disconnect() {
        return Completable
                .fromAction(this::rawDisconnect);
    }

    private Completable initSocket() {
        return Completable
                .fromAction(this::createWebSocketConnection);
    }

    /**
     * Most important method: connects to websocket and notifies program of messages.
     * <p>
     * See implementations in OkHttpConnectionProvider and WebSocketsConnectionProvider.
     */
    protected abstract void createWebSocketConnection();

    @NonNull
    @Override
    public Completable send(String stompMessage) {
        return Completable.fromCallable(() -> {
            if (getSocket() == null) {

//                throw new IllegalStateException("Not connected");
                Log.d(TAG, "Not Connected");
                return null;
            } else {
                Log.d(TAG, "Send STOMP message: " + stompMessage);
                rawSend(stompMessage);
                return null;
            }
        });
    }

    /**
     * Just a simple message send.
     * <p>
     * For example:
     * <pre>
     * webSocket.send(stompMessage);
     * </pre>
     *
     * @param stompMessage message to send
     */
    protected abstract void rawSend(String stompMessage);

    /**
     * Get socket object.
     * Used for null checking; this object is expected to be null when the connection is not yet established.
     * <p>
     * For example:
     * <pre>
     * return webSocket;
     * </pre>
     */
    @Nullable
    public abstract Object getSocket();

    protected void emitLifecycleEvent(@NonNull LifecycleEvent lifecycleEvent) {
        Log.d(TAG, "Emit lifecycle event: " + lifecycleEvent.getType().name());
        if (lifecycleEvent.getType() == LifecycleEvent.Type.ERROR) {
//            lifecycleEvent.getException().printStackTrace();
            Log.d(TAG, "emitLifecycleEvent: error");
        }
        lifecycleStream.onNext(lifecycleEvent);
    }

    protected void emitMessage(String stompMessage) {
        Log.d(TAG, "Receive STOMP message: " + stompMessage);
        if (stompMessage.startsWith("CONNECTED")) {
            emitLifecycleEvent(new LifecycleEvent(LifecycleEvent.Type.CONNECTED, "CONNECTED STOMP"));
        }

        messagesStream.onNext(stompMessage);
    }

    @NonNull
    @Override
    public Observable<LifecycleEvent> lifecycle() {
        return lifecycleStream;
    }
}