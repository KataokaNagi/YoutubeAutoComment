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
    public static class CommentSnippet {
        public String videoId;

        public String parentId;

        public String textDisplay;

        public String textOriginal;

        public String authorDisplayName;

        public String authorChannelUrl;

        public Date publishedAt;

        public Date updatedAt;
    }

    public Comment() {
        this.kind = "youtube#comment";
        this.snippet = new CommentSnippet();
    }

    public String kind;

    public String etag;

    public String id;

    public CommentSnippet snippet;
}
