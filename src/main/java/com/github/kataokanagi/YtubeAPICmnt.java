package com.github.kataokanagi;

import java.io.*;
import java.util.*;
import java.net.*;

// @see https://www.memory-lovers.blog/entry/2014/11/19/234038

public class YtubeAPICmnt {

  HttpURLConnection conn;

  // コンストラクタ
  public YtubeAPICmnt() {
  }

  public void connctToYtube(String apiKey, String videoId) {
    // Proxyサーバの設定（学内）←設定しなくても正常動作する
    System.setProperty("http.proxyHost", "proxy.sic.shibaura-it.ac.jp");
    System.setProperty("http.proxyPort", "10080");

    // Web APIのリクエストURLを構築する
    // ! @see
    // https://www.it-swarm-ja.tech/ja/youtube-api/youtube%E3%83%93%E3%83%87%E3%82%AA%E3%81%AB%E9%96%A2%E3%81%99%E3%82%8B%E3%81%99%E3%81%B9%E3%81%A6%E3%81%AE%E3%82%B3%E3%83%A1%E3%83%B3%E3%83%88%E3%82%92%E5%8F%96%E5%BE%97%E3%81%99%E3%82%8B%E6%96%B9%E6%B3%95%E3%81%AF%EF%BC%9F/1043094220/
    String apiUrl = "https://www.googleapis.com/youtube/v3/commentThreads?";
    apiUrl += "key=" + apiKey;
    apiUrl += "&textFormat=plainText";
    apiUrl += "&part=snippet";
    apiUrl += "&videoId=" + videoId;
    apiUrl += "&maxResults=100";

    try {
      // HTTP接続を確立し，処理要求を送る
      conn = (HttpURLConnection) (new URL(apiUrl)).openConnection();
      conn.setRequestMethod("GET"); // GETメソッド

    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public String getJsonStr() {
    String response = null; // Webサーバからの応答

    // Webサーバからの応答を受け取る
    try {
      BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
      response = "";
      String line;
      while ((line = br.readLine()) != null) {
        response += line;
      }
      br.close();
      System.out.println("Got JSON\n"); // ! debug

    } catch (IOException ex) {
      ex.printStackTrace();
    }

    return response;
  }

  // START_TAGからEND_TAGで囲まれた文字列の中の、コメント部分を取得して返す
  public void addStrBtweenTagsToList(String jsonStr, List<String> cmntList, String startTag, String endTag) {
    int endIdx;
    String neededStr;
    if (jsonStr != null && jsonStr.length() > 0) {
      int offset = 0;
      while ((offset = jsonStr.indexOf("\"", offset)) != -1) {
        if (jsonStr.startsWith(startTag, offset)) {
          endIdx = jsonStr.indexOf(endTag, offset);
          neededStr = jsonStr.substring(offset + endTag.length(), endIdx);
          neededStr = neededStr.split("\"")[1];
          cmntList.add(neededStr);
        }
        offset++;
      }
      System.out.println("Got Comments\n"); // ! debug
    } else { // ! added
      System.out.println("Error: response is NULL.");
    }

  }

  public List<String> getCmntList() {
    List<String> cmntList = new ArrayList<String>();
    List<String> tmpStrList = new ArrayList<String>();
    String jsonStr = this.getJsonStr();
    addStrBtweenTagsToList(jsonStr, cmntList, "\"textDisplay\"", "\"textOriginal\"");
    // for (String cmnt : cmntList) { // ! debug
    // System.out.println(cmnt);
    // }
    return cmntList;
  }

  /**
   * @fn autoCmnt
   * @brief
   * @note Now building
   */
  public void autoCmnt() {
    ;
  }

  public void disconnctYtube() {
    conn.disconnect();
  }

}
