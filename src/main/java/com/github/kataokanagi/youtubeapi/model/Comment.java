package com.github.kataokanagi.youtubeapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Date;

// https://developers.google.com/youtube/v3/docs/comments#resource
@JsonTypeInfo(use=JsonTypeInfo.Id.NONE)
@JsonIgnoreProperties(ignoreUnknown=true)
public class Comment {

    @JsonTypeInfo(use=JsonTypeInfo.Id.NONE)
    @JsonIgnoreProperties(ignoreUnknown=true)
    static class CommentSnippet {
        public String videoId;

        public String textDisplay;

        public String textOriginal;

        public String authorDisplayName;

        public String authorChannelUrl;

        public boolean canRate;

        public int likeCount;

        public Date publishedAt;

        public Date updatedAt;
    }

    public String kind;

    public String etag;

    public String id;

    public CommentSnippet snippet;
}
