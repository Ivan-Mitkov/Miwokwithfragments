package com.example.android.miwokwithfragments;

import android.media.Image;

/**
 * Created by ivan on 16.9.2017 г..
 */

public class Word {


    private String miwokWord;
    private String defaultTranslation;
    private int ic;
    private int mAudio;

    public Word( String defaultTranslation,String miwokWord, int mAudio){
        this.mAudio=mAudio;
        this.miwokWord=miwokWord;
        this.defaultTranslation=defaultTranslation;
    }

    public Word(String defaultTranslation, String miwokWord ,int ic, int mAudio ){
        this.mAudio=mAudio;
        this.ic=ic;
        this.miwokWord=miwokWord;
        this.defaultTranslation=defaultTranslation;
    }

    public int getmAudio() {
        return mAudio;
    }

    public void setmAudio(int mAudio) {
        this.mAudio = mAudio;
    }

    public String getMiwokWord() {
        return miwokWord;
    }

    public void setMiwokWord(String miwokWord) {
        this.miwokWord = miwokWord;
    }

    public String getDefaultTranslation() {
        return defaultTranslation;
    }

    public void setDefaultTranslation(String defaultTranslation) {
        this.defaultTranslation = defaultTranslation;
    }

    public int getImageResourceId() {
        return ic;
    }

    public void setImageResourceId(int ic) {
        this.ic = ic;
    }

}
