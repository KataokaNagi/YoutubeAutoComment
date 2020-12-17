package com.github.kataokanagi.youtubeapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kataokanagi.utils.Log;
import com.github.kataokanagi.youtubeapi.model.Comment;
import com.github.kataokanagi.youtubeapi.model.CommentThreadListResponse;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.HashMap;

public class API {

    private static final String TAG = "API";

    private static final String YOUTUBE_V3_COMMENTTHREADS = "https://www.googleapis.com/youtube/v3/commentThreads";
    private static final String YOUTUBE_V3_COMMENTS = "https://www.googleapis.com/youtube/v3/comments";

    // Youtube API: Get a youtube comment thread which contains comment list
    public static CommentThreadListResponse commentThreadList(String videoId) throws IOException {
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

        // doRequest & getResponse could trigger IOException if network error happened
        String response = jhr.setParams(params)
                             .doRequest()
                             .getResponse();

        // JSON deserialize
        ObjectMapper mapper = new ObjectMapper();
        CommentThreadListResponse listResponse = null;

        try {
            listResponse = mapper.readValue(response, CommentThreadListResponse.class);
        } catch (JsonProcessingException e) {
            // If data is corrupted
            throw new IOException(e.getMessage());
        }

        return listResponse;
    }

    // Youtube API: Reply to a comment
    public static Comment commentsInsert(String parentId, String commentText) throws IOException {
        // TODO: Implement
        return null;
    }

}
