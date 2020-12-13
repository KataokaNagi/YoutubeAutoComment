package com.github.kataokanagi.youtubeapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

// https://developers.google.com/youtube/v3/docs/commentThreads/list#response
@JsonTypeInfo(use=JsonTypeInfo.Id.NONE)
@JsonIgnoreProperties(ignoreUnknown=true)
public class CommentThreadListResponse {
    public String kind;

    public String etag;

    public CommentThread[] items;
}
