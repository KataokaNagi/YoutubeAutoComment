package com.github.kataokanagi;

import com.github.kataokanagi.utils.Log;
import com.github.kataokanagi.youtubeapi.API;
import com.github.kataokanagi.youtubeapi.OAuthHelper;
import com.github.kataokanagi.youtubeapi.model.Comment;
import com.github.kataokanagi.youtubeapi.model.CommentThread;
import com.github.kataokanagi.youtubeapi.model.CommentThreadListResponse;
import com.google.api.client.auth.oauth2.Credential;

import java.io.IOException;
import java.util.*;

public class YoutubeAutoComment {

    static final String VIDEO_ID = ""; // ! After "watch?v=" in video URL

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
                        Date publishedDate = ct.snippet.topLevelComment.snippet.publishedAt;

                        String comment = ct.snippet.topLevelComment.snippet.textOriginal;

                        String result = replyGenerator.generate(comment);

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
