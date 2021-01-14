package com.github.kataokanagi;

import com.github.kataokanagi.utils.Log;
import com.github.kataokanagi.youtubeapi.API;
import com.github.kataokanagi.youtubeapi.OAuthHelper;
import com.github.kataokanagi.youtubeapi.model.*;
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

        UserInfoPlus userinfo = API.userInfoMe(credential);

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
                            if (reply.snippet.authorDisplayName.equals(userinfo.name)) {
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
