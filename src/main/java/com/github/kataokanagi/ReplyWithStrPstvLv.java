package com.github.kataokanagi;

import java.io.*;

// WikipediaキーワードAPIのテスト //! を改良
public class ReplyWithStrPstvLv {

  // コンストラクタ
  public ReplyWithStrPstvLv() {
  }

  static void reply(String cmnt, double strPstLv) {
    System.out.println("> " + cmnt);

    // ! strPstLv \in [-1,1]
    if (strPstLv == 1.0) {
      System.out.println("すっごーい！\n");
    } else {
      switch ((int) ((strPstLv + 1) * 10) % 20) {
        case 0:
        case 1:
        case 2:
        case 3:
          System.out.println("ワロス\n");
          break;
        case 4:
        case 5:
        case 6:
          System.out.println("あし\n");
          break;
        case 7:
        case 8:
        case 9:
          System.out.println("わろし\n");
          break;
        case 10:
        case 11:
        case 12:
          System.out.println("よろし\n");
          break;
        case 13:
        case 14:
        case 15:
          System.out.println("よし\n");
          break;
        default: // ! case 16, 17, 18, 19
          System.out.println("ヨシ！\n");
      }
    }
  }
}
