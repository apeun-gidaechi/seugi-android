package com.seugi.stompclient.pathmatcher;

import com.seugi.stompclient.dto.StompHeader;
import com.seugi.stompclient.dto.StompMessage;

public class SimplePathMatcher implements PathMatcher {

    @Override
    public boolean matches(String path, StompMessage msg) {
        String dest = msg.findHeader(StompHeader.DESTINATION);
        if (dest == null) return false;
        else return path.equals(dest);
    }
}