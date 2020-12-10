package com.github.kataokanagi;

import java.util.*;

public class YtubeAutoCmntMain {

  // ! @see
  // https://console.developers.google.com/apis/credentials?authuser=2&project=kodo1a&supportedpurview=project
  static final String API_KEY = "AIzaSyDAYlOyOmte9cEew4T19VkHkKQXF86uJL0";
  static final String VIDEO_ID = "F6Z8OWJD5IE"; // ! After "watch?v=" in video URL

  public YtubeAutoCmntMain() {
  }

  public static void main(String[] args) {
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
