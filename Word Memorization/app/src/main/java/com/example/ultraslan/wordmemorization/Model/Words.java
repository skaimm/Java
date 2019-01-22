package com.example.ultraslan.wordmemorization.Model;

import android.media.MediaPlayer;

import java.io.Serializable;
import java.util.Date;

public class Words implements Serializable{

    private String word_id;
    private String word_path;
    private String user_id;
    private String word;
    private MediaPlayer pronuncation;
    private String mean1;
    private String mean2;
    private String mean3;
    private String mean4;
    private String mean5;
    private String level;
    private int situation;
    private boolean isitnew;
    private String studydate;

    public Words(String word_id, String word_path, String user_id, String word, MediaPlayer pronuncation,boolean isitnew,
                 String mean1, String mean2, String mean3, String mean4, String mean5, String level,int situation,String studydate) {
        this.word_id = word_id;
        this.word_path = word_path;
        this.user_id = user_id;
        this.word = word;
        this.pronuncation = pronuncation;
        this.mean1 = mean1;
        this.mean2 = mean2;
        this.mean3 = mean3;
        this.mean4 = mean4;
        this.mean5 = mean5;
        this.level = level;
        this.situation = situation;
        this.isitnew = isitnew;
        this.studydate = studydate;
    }

    public Words(){
    }

    public String getWord_id() {
        return word_id;
    }

    public void setWord_id(String word_id) {
        this.word_id = word_id;
    }

    public String getWord_path() {
        return word_path;
    }

    public void setWord_path(String word_path) {
        this.word_path = word_path;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public MediaPlayer getPronuncation() {
        return pronuncation;
    }

    public void setPronuncation(MediaPlayer pronuncation) {
        this.pronuncation = pronuncation;
    }

    public String getMean1() {
        return mean1;
    }

    public void setMean1(String mean1) {
        this.mean1 = mean1;
    }

    public String getMean2() {
        return mean2;
    }

    public void setMean2(String mean2) {
        this.mean2 = mean2;
    }

    public String getMean3() {
        return mean3;
    }

    public void setMean3(String mean3) {
        this.mean3 = mean3;
    }

    public String getMean4() {
        return mean4;
    }

    public void setMean4(String mean4) {
        this.mean4 = mean4;
    }

    public String getMean5() {
        return mean5;
    }

    public void setMean5(String mean5) {
        this.mean5 = mean5;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getSituation() {
        return situation;
    }

    public void setSituation(int situation) {
        this.situation = situation;
    }

    public String getStudydate() {
        return studydate;
    }

    public void setStudydate(String studydate) {
        this.studydate = studydate;
    }

    public boolean isIsitnew() {
        return isitnew;
    }

    public void setIsitnew(boolean isitnew) {
        this.isitnew = isitnew;
    }

    @Override
    public String toString() {
        return word ;
    }

    public String toStringMeans(){
        return  mean1 + '\'' + mean2 + '\'' + mean3 + '\'' + mean4 + '\'' + mean5 ;
    }
}


