package com.github.kataokanagi.youtubeapi;

public class Config {

    /**************************************************/
    // Put API key by hand after making branch
    // and put away before PR
    private static final String API_KEY = "";
    /**************************************************/

    private static boolean enableHttpProxy = false;
    private static String httpProxyHost;
    private static int httpProxyPort;

    public static boolean getHttpProxyEnabled() {
        return enableHttpProxy;
    }

    public static void setHttpProxyEnabled(boolean enabled) {
        enableHttpProxy = enabled;
    }

    public static void setHttpProxyServer(String host, int port) {
        httpProxyHost = host;
        httpProxyPort = port;
    }

    public static String getHttpProxyHost() {
        return httpProxyHost;
    }

    public static int getHttpProxyPort() {
        return httpProxyPort;
    }

    public static String getApiKey() {
        return API_KEY;
    }

}
