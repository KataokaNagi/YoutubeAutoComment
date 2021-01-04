package com.github.kataokanagi;

import com.github.kataokanagi.youtubeapi.API;
import com.github.kataokanagi.youtubeapi.OAuthHelper;
import com.google.api.client.auth.oauth2.Credential;

import java.io.IOException;
import java.util.*;

public class YtubeAutoCmntMain {

  // ! @see
  // https://console.developers.google.com/apis/credentials?authuser=2&project=kodo1a&supportedpurview=project

  /**************************************************/
  // Put API key by hand after making branch
  // and put away before PR
  static final String API_KEY = "";
  /**************************************************/
  static final String VIDEO_ID = "F6Z8OWJD5IE"; // ! After "watch?v=" in video URL

  public YtubeAutoCmntMain() {
  }

  public static void main(String[] args) throws IOException {
    Credential credential = OAuthHelper.authorize();
    String accessToken = credential.getAccessToken();

    var list = API.commentThreadList(VIDEO_ID);
    var comment = API.commentsInsert(credential, list.items[0].id, "test auto reply from code");

    YtubeAPICmnt yAPIC = new YtubeAPICmnt();
    List<String> cmntList = new ArrayList<String>();
    GetStrPstvLv getStPsLv = new GetStrPstvLv();

    yAPIC.connctToYtube(API_KEY, VIDEO_ID);
    System.out.println("Connected\n");

    cmntList = yAPIC.getCmntList();
    for (String cmnt : cmntList) {
      ReplyWithStrPstvLv.reply(cmnt, getStPsLv.getLv(cmnt)); // ! Lv \in [-1, 1]
    }

    yAPIC.disconnctYtube();
    System.out.println("Disconnected\n");
  }
}
