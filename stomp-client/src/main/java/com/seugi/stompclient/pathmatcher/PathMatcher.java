package com.seugi.stompclient.pathmatcher;

import com.seugi.stompclient.dto.StompMessage;

public interface PathMatcher {

    boolean matches(String path, StompMessage msg);
}