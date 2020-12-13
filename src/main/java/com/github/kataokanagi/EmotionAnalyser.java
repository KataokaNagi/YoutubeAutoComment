package com.github.kataokanagi;

import com.github.kataokanagi.utils.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmotionAnalyser {

    private static final String TAG = "EmotionAnalyser";

    private Map<String, Integer> sentiMap = new HashMap<>();

    public EmotionAnalyser(String dictionaryPath) throws IOException {
        File file = new File(dictionaryPath);

        BufferedReader br = new BufferedReader(new FileReader(file));

        String line = null;

        while ((line = br.readLine()) != null) {
            String[] split = line.split("\t");
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
        }

        br.close();
    }

    public double getPositiveLevel(String comment) {
        return 0.0;
    }

    private List<String> getCommentSurfaceList() {
        return null;
    }

    private double evalFunc(int positiveCount, int negativeCount) {
        double eval;
        if (positiveCount + negativeCount != 0) {
            eval = (double) (positiveCount - negativeCount) / (positiveCount + negativeCount);
        } else {
            eval = 0.0;
        }
        Log.d(TAG, "eval = " + eval);
        return eval;
    }
}
