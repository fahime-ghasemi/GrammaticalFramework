package com.ikiu.tagger.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fahime on 11/26/15.
 */
public class WordsTreeNode {

    boolean isTemporary;
    Map<Character,WordsTreeNode> letters ;
    DatabaseManager.TokenTableRow content;

    public WordsTreeNode() {
        letters = new HashMap<>();
    }

    public boolean isTemporary() {
        return isTemporary;
    }

    public void setIsTemporary(boolean isTemporary) {
        this.isTemporary = isTemporary;
    }

    public Map<Character, WordsTreeNode> getLetters() {
        return letters;
    }

    public void setLetters(Map<Character, WordsTreeNode> letters) {
        this.letters = letters;
    }

    public DatabaseManager.TokenTableRow getContent() {
        return content;
    }

    public void setContent(DatabaseManager.TokenTableRow content) {
        this.content = content;
    }
}
