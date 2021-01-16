package com.github.kataokanagi;

import com.github.kataokanagi.utils.Log;
import com.github.kataokanagi.EmotionAnalyser;

import java.io.IOException;

public class ReplyGenerator {

  private static final String TAG = "ReplyGenerator";
  private EmotionAnalyser emotionAnalyser;

  public ReplyGenerator() throws IOException {
    emotionAnalyser = new EmotionAnalyser();
  }

  public String generate(String comment) throws IOException {
    String reply;
    double positiveLevel = emotionAnalyser.getPositiveLevel(comment);

    if (positiveLevel == 1.0) { // positiveLevel \in [-1,1]
      reply = "すっごーい！";
    } else {
      int val = (int) ((positiveLevel + 1) * 10) % 20;
      if (val >= 0 && val <= 3) {
        reply = "ワロス";
      } else if (val >= 4 && val <= 6) {
        reply = "あし";
      } else if (val >= 7 && val <= 9) {
        reply = "わろし";
      } else if (val >= 10 && val <= 12) {
        reply = "よろし";
      } else if (val >= 13 && val <= 15) {
        reply = "よし";
      } else { // case 16, 17, 18, 19
        reply = "ヨシ！";
      }
    }

    Log.v(TAG, comment + " -> " + reply);
    return reply;
  }

}
