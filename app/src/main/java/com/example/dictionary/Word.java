package com.example.dictionary;

public class Word {
    private String mVietTranslation;
    private String mDefautlTranslation;


    private int mImageResourceId = NO_IMAGE_PROVIDED;

    private static final int NO_IMAGE_PROVIDED = -1;

    private int mAudioResourceId;

    public Word(String defautlTranslation,String vietTranslation,int imageResourceId,int audioResourceId) {
        this.mDefautlTranslation = defautlTranslation;
        this.mVietTranslation = vietTranslation;
        this.mImageResourceId = imageResourceId;
        this.mAudioResourceId = audioResourceId;
    }

    /*get default translation of the word*/
    public String getDefautlTranslation() {
        return mDefautlTranslation;
    }

    public String getVietTranslation() {
        return mVietTranslation;
    }

    public int getmImageResourceId() {
        return mImageResourceId;
    }

    public boolean hasImage() {
        return mImageResourceId!=NO_IMAGE_PROVIDED;
    }
    public int getmAudioResourceId () {
        return mAudioResourceId;
    }

    @Override
    public String toString() {
        return "Word{" +
                "mVietTranslation='" + mVietTranslation + '\'' +
                ", mDefautlTranslation='" + mDefautlTranslation + '\'' +
                ", mImageResourceId=" + mImageResourceId +
                ", mAudioResourceId=" + mAudioResourceId +
                '}';
    }

}