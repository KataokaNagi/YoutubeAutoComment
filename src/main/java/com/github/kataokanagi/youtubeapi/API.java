package com.github.kataokanagi.youtubeapi;

import com.github.kataokanagi.utils.Log;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.HashMap;

public class API {

    private static final String TAG = "API";

    private static final String YOUTUBE_V3_COMMENTTHREADS = "https://www.googleapis.com/youtube/v3/commentThreads";
    private static final String YOUTUBE_V3_COMMENTS = "https://www.googleapis.com/youtube/v3/comments";

    public static String commentThreadList(String videoId) {
        JSONHttpRequest jhr;

        if (Config.getHttpProxyEnabled()) {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(Config.getHttpProxyHost(), Config.getHttpProxyPort()));
            jhr = new JSONHttpRequest(proxy, "GET", YOUTUBE_V3_COMMENTTHREADS);
        } else {
            jhr = new JSONHttpRequest("GET", YOUTUBE_V3_COMMENTTHREADS);
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("key", Config.getApiKey());
        params.put("textFormat", "plainText");
        params.put("part", "snippet");
        params.put("videoId", videoId);
        params.put("maxResults", "100");

        String result = jhr.setParams(params)
                           .doRequest()
                           .getResponse();

        Log.v(TAG, result);
        return result;
    }

}
