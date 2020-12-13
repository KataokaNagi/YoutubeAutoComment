package com.github.kataokanagi.youtubeapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

// https://developers.google.com/youtube/v3/docs/commentThreads#resource
@JsonTypeInfo(use=JsonTypeInfo.Id.NONE)
@JsonIgnoreProperties(ignoreUnknown=true)
public class CommentThread {

    @JsonTypeInfo(use=JsonTypeInfo.Id.NONE)
    @JsonIgnoreProperties(ignoreUnknown=true)
    static class CommentThreadSnippet {
        public String videoId;

        public Comment topLevelComment;

        public boolean canReply;

        public int totalReplyCount;

        public boolean isPublic;
    }

    public String kind;

    public String etag;

    public String id;

    public CommentThreadSnippet snippet;

}
