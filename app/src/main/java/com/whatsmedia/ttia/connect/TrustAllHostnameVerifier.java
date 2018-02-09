package com.whatsmedia.ttia.connect;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * Trust all hosts.
 */
public class TrustAllHostnameVerifier implements HostnameVerifier {
    @Override
    public boolean verify(String hostname, SSLSession session) {
        return (hostname.equals("59.127.195.228") || hostname.equals("210.241.14.99"));
    }
}
