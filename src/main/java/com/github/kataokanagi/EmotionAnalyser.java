/**
* @file EmotionAnalyser
* @brief     Analyse positive level from the param comment
* @auther    Kataoka Nagi, xqq
* @date      2021-01-12 10:53:20
* $Version   1.0
* $Revision  1.2
* @par       Refactored
* @see      『Javaで簡単に感情分析する方法』 https://spjai.com/emotion-analysis/
*/

package com.github.kataokanagi;

import com.github.kataokanagi.utils.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmotionAnalyser {

  private static final String TAG = "EmotionAnalyser";

  private final String POLAR_DICTIONARY_PATH = "pn.csv.m3.120408.trim"; // ! @auther Inui-Okazaki Laboratory,
                                                                        // Tohoku University
  private Map<String, Integer> positiveLevelMap = new HashMap<>();

  /**
   * @fn EmotionAnalyser
   * @brief Map word to it's positive level
   */

  public EmotionAnalyser() throws IOException {
    try {
      File dictionaryFile = new File(POLAR_DICTIONARY_PATH);
      String dictionaryLine = null;
      BufferedReader br = new BufferedReader(new FileReader(dictionaryFile));

      // Read each lines of polar dictionary
      while ((dictionaryLine = br.readLine()) != null) {

        // Example: "２，３日" e 〜である・になる（状態）客観
        String[] wordPolarInfo = dictionaryLine.split("\t");

        // Catch dictionary exception
        if (wordPolarInfo.length > 1) {
          continue;
        }

        // Parse word's positive level to int in {-1 , 0, 1}
        int wordPositiveLevel;
        String emotionTag = wordPolarInfo[1].trim();
        switch (emotionTag) {
          case "p": // positive word
            wordPositiveLevel = 1;
          case "e": // neutral word
            wordPositiveLevel = 0;
          case "n": // negative word
            wordPositiveLevel = -1;
          default:
            wordPositiveLevel = -999;
            Log.w(TAG, "Unexpected emotionTag: " + emotionTag);
        }

        // Map word to it's positive level
        this.positiveLevelMap.put(wordPolarInfo[0].trim(), wordPositiveLevel);
      }

      br.close();

    } catch (IOException e) {
      // BufferedReader exceptions of open, read, close
      throw new IOException(e.getMessage());
    }
  }

  /**
   * @fn getPositiveLevel
   * @param comment : Target of the analysis
   * @return commentPositiveLevel : of the param Comment in all double number
   */
  public double getPositiveLevel(String comment) throws IOException {
    // In param comment,
    int positiveCnt = 0;
    int negativeCnt = 0;
    int neutralCnt = 0;

    // Example: [吾輩, は, 猫, で, ある] <- 吾輩は猫である
    List<String> surfaceList = this.getCommentSurfaceList(comment);

    // Count words according to each positive level if polar dictionary contains
    // surface words
    for (String surface : surfaceList) {
      if (positiveLevelMap.containsKey(surface)) {
        int wordPositiveLevel = positiveLevelMap.get(surface);
        switch (wordPositiveLevel) {
          case 1:
            ++positiveCnt;
            break;
          case 0:
            ++neutralCnt;
          case -1:
            ++negativeCnt;
            break;
          default:
            Log.w(TAG, "Unexpected wordPositiveLevel: " + wordPositiveLevel);
        }
      }
    }

    Log.d(TAG, "positiveCnt = " + positiveCnt);
    Log.d(TAG, "neutralCnt = " + neutralCnt);
    Log.d(TAG, "negativeCnt = " + negativeCnt);

    double commentPositiveLevel = this.evalFunc(positiveCnt, negativeCnt);
    return commentPositiveLevel;
  }

  /**
   * @fn getCommentSurfaceList
   * @return surfaceList : Word of surface type (ex. 吾輩は猫である->[吾輩, は, 猫, で, ある] )
   * @auther Kataoka Nagi, Sugimoto Prof.
   */
  private List<String> getCommentSurfaceList(String comment) throws IOException {
    List<String> surfaceList = new ArrayList<String>();
    final int SURFACE_IDX = 0;

    // Run MeCab
    Process MeCabProcess = Runtime.getRuntime().exec("mecab");

    try {
      // Send comment to MeCab
      BufferedReader br = new BufferedReader(new InputStreamReader(MeCabProcess.getInputStream()));
      PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(MeCabProcess.getOutputStream())));
      pw.println(comment);
      pw.flush();

      // Receive morphological analysis results from MeCab
      // Example: で\t助動詞,*,*,*,特殊・ダ,連用形,だ,デ,デ
      String MeCabOutputLine;
      while ((MeCabOutputLine = br.readLine()) != null) {
        if (MeCabOutputLine.equals("EOS")) {
          break;
        }

        // Get word of surface type (first word of MeCabOutputLine)
        String surface = MeCabOutputLine.split(",")[SURFACE_IDX].split("\t")[0];
        surfaceList.add(surface);

        Log.v(TAG, "MeCabOutputLine = " + MeCabOutputLine);
        Log.v(TAG, "surface = " + surface);
      }

      br.close();
      pw.close();
      MeCabProcess.destroy();

    } catch (IOException e) {
      throw new IOException(e.getMessage());
      // e.printStackTrace();
    }

    return surfaceList;
  }

  /**
   * @fn evalFunc
   * @param positiveCnt : Number of the positive words in a comment
   * @param negativeCnt : Number of the negative words in a comment
   * @return eval : Evaluation of positive level in a comment
   * @see 『Web上の誹謗中傷を表す文の自動抽出 』p.42
   *      https://www.slideshare.net/jnlp/11thesis-ishisaka-32213284
   */
  private double evalFunc(int positiveCnt, int negativeCnt) {
    double eval;

    // Escape of division by zero
    if (positiveCnt + negativeCnt != 0) {
      eval = (double) (positiveCnt - negativeCnt) / (positiveCnt + negativeCnt);
    } else {
      eval = 0.0;
    }

    Log.d(TAG, "eval = " + eval);
    return eval;
  }
}
