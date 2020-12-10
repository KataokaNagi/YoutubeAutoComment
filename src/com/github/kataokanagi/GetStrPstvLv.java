package com.github.kataokanagi;

import java.io.*;
import java.net.*;
import java.util.*;

//! @see https://spjai.com/emotion-analysis/
public class GetStrPstvLv {

  Map<String, Integer> sentiMap = new HashMap<String, Integer>(); // 評価極性辞書から読み込み、単語とスコアを保持したマップ

  // コンストラクタ
  public GetStrPstvLv() {
    loadPolarDictnry();
  }

  public void loadPolarDictnry() {
    File file = new File("pn.csv.m3.120408.trim"); // 評価極性辞書のロード

    try {
      BufferedReader br = new BufferedReader(new FileReader(file));
      String str = br.readLine();
      while (str != null) {
        String[] split = str.split("\t"); // タブで分割
        if (split.length > 1) {
          String emotion = split[1].trim(); // p or e or n
          int sentiScore = 0;
          if (emotion.equals("p")) {
            sentiScore = 1; // pの場合+1
          } else if (emotion.equals("n")) {
            sentiScore = -1; // nの場合-1
          }
          sentiMap.put(split[0].trim(), sentiScore); // 単語とスコアの格納
        }
        str = br.readLine();
      }
      br.close();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  // ! @see TestMeCab1.java
  public List<String> getCmntSurfaceList(String cmnt) {
    List<String> surfaceList = new ArrayList<String>();

    try {
      // MeCabを起動し，入出力用のストリームを生成する
      Process process = Runtime.getRuntime().exec("mecab");
      BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
      PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(process.getOutputStream())));

      pw.println(cmnt); // MeCabへ文を送信
      pw.flush();

      String line2;
      while ((line2 = br.readLine()) != null) { // 解析結果をMeCabから受信
        if (line2.equals("EOS")) { // EOSは文の終わりを表す
          break;
        }
        // System.out.println(line2); //! Debug

        // ! Kataoka Added
        String surface = line2.split(",")[0].split("\t")[0];
        // System.out.println(surface); // ! Debug
        surfaceList.add(surface);
      }
      br.close();
      pw.close();
      process.destroy();
    } catch (IOException ex) {
      ex.printStackTrace();
    }

    return surfaceList;
  }

  public double getLv(String cmnt) {
    // 感情スコア、+1ならばPositive、0ならばneutral、-1ならばnegative
    int posiCnt = 0;
    int negaCnt = 0;
    int neutralCnt = 0;

    List<String> surfaceList = getCmntSurfaceList(cmnt);

    // 評価極性辞書に単語が存在する場合、スコア加算
    for (String surface : surfaceList) {
      if (sentiMap.containsKey(surface)) {
        switch (sentiMap.get(surface)) {
          case 1:
            ++posiCnt;
            break;
          case -1:
            ++negaCnt;
            break;
          default: // ! 0 (neutral)
            ++neutralCnt;
        }
      }
    }

    System.out.println("(nega, neut, posi) = (" + negaCnt + "," + neutralCnt + "," + posiCnt + ")"); // ! debug
    return evalFunc(posiCnt, negaCnt);
  }

  // ! @see https://www.slideshare.net/jnlp/11thesis-ishisaka-32213284
  // ! @see p.42
  public double evalFunc(int posiCnt, int negaCnt) {
    double eval;
    if (posiCnt + negaCnt != 0) {
      eval = (double) (posiCnt - negaCnt) / (posiCnt + negaCnt);
    } else {
      eval = 0.0;
    }
    System.out.println("eval = " + eval); // ! debug
    return eval;
  }
}
