package com.github.kataokanagi;

import com.github.kataokanagi.utils.Log;
import com.github.kataokanagi.youtubeapi.API;
import com.github.kataokanagi.youtubeapi.OAuthHelper;
import com.github.kataokanagi.youtubeapi.model.Comment;
import com.github.kataokanagi.youtubeapi.model.CommentListResponse;
import com.github.kataokanagi.youtubeapi.model.CommentThread;
import com.github.kataokanagi.youtubeapi.model.CommentThreadListResponse;
import com.google.api.client.auth.oauth2.Credential;

import java.io.IOException;
import java.util.*;

public class YoutubeAutoComment {

    static final String VIDEO_ID = ""; // ! After "watch?v=" in video URL
    private static final String AUTHOR_USER_NAME = "Nagi";

    public YoutubeAutoComment() {
    }

    public static void main(String[] args) throws IOException {
        Credential credential = OAuthHelper.authorize();
        ReplyGenerator replyGenerator = new ReplyGenerator();

        Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    CommentThreadListResponse list = API.commentThreadList(VIDEO_ID);

                    for (CommentThread ct : list.items) {
                        Comment comment = ct.snippet.topLevelComment;
                        CommentListResponse replyList = API.commentsList(comment.id);

                        boolean replied = false;

                        for (Comment reply : replyList.items) {
                            // 既に返信済みかを判断
                            if (reply.snippet.authorDisplayName == AUTHOR_USER_NAME) {
                                replied = true;
                                break;
                            }
                        }

                        if (replied) {
                            continue;
                        }

                        String commentText = comment.snippet.textOriginal;
                        String result = replyGenerator.generate(commentText);

                        Comment insertComment = API.commentsInsert(credential, ct.id, result);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        timer.scheduleAtFixedRate(task, 0, 60000);


    }
}
