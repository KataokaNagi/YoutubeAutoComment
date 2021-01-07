package com.github.kataokanagi.youtubeapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

// https://developers.google.com/youtube/v3/docs/comments/list?hl=ja
@JsonTypeInfo(use=JsonTypeInfo.Id.NONE)
@JsonIgnoreProperties(ignoreUnknown=true)
public class CommentListResponse {

    @JsonTypeInfo(use=JsonTypeInfo.Id.NONE)
    @JsonIgnoreProperties(ignoreUnknown=true)
    public static class PageInfo {
        public int totalResults;

        public int resultsPerPage;
    }

    public String kind;

    public String etag;

    public String nextPageToken;

    public PageInfo pageInfo;

    public Comment[] items;

}
