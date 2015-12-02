package com.ikiu.tagger.model;

/**
 * Created by fahime on 11/26/15.
 */
public class WordsTreeContent {
    private String word;
    private String type;
    private int id;
    private int meaning;
    private boolean isGenerated;
    private int editedCells;
    private boolean mReadyForGenerate;
    //------

    public WordsTreeContent() {
    }

    public WordsTreeContent(int id,String word, String type, int meaning, boolean isGenerated) {
        this.word = word;
        this.type = type;
        this.id = id;
        this.meaning = meaning;
        this.isGenerated = isGenerated;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMeaning() {
        return meaning;
    }

    public void setMeaning(int meaning) {
        this.meaning = meaning;
    }

    public boolean isGenerated() {
        return isGenerated;
    }

    public void setIsGenerated(boolean isGenerated) {
        this.isGenerated = isGenerated;
    }

    public int getEditedCells() {
        return editedCells;
    }

    public void setEditedCells(int editedCells) {
        this.editedCells = editedCells;
    }

    public boolean isReadyForGenerate() {
        return mReadyForGenerate;
    }

    public void setReadyForGenerate(boolean mReadyForGenerate) {
        this.mReadyForGenerate = mReadyForGenerate;
    }
}
