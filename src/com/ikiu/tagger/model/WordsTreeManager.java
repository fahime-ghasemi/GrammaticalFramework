package com.ikiu.tagger.model;

import javax.xml.crypto.Data;
import java.util.Vector;

/**
 * Created by fahime on 11/26/15.
 */
public class WordsTreeManager {
    DatabaseManager databaseManager;
    WordsTreeNode englishTreeRoot;
    WordsTreeNode persianTreeRoot;

    private static WordsTreeManager mInstance;

    private WordsTreeManager() {
        databaseManager = new DatabaseManager();
        englishTreeRoot = new WordsTreeNode();
        persianTreeRoot = new WordsTreeNode();
    }

    public static synchronized WordsTreeManager getInstance() {
        if (mInstance == null)
            mInstance = new WordsTreeManager();
        return mInstance;
    }

    public WordsTreeNode getEnglishTree() {
        return englishTreeRoot;
    }

    public WordsTreeNode getPersianTree() {
        return persianTreeRoot;
    }

    public void createEnglishTree() {
        WordsTreeNode cursor = englishTreeRoot;
        Vector<DatabaseManager.TokenTableRow> tokens = databaseManager.getEnglishTokens();
        for (int i = 0; i < tokens.size(); ++i) {
            char[] letters = tokens.get(i).getWord().toLowerCase().toCharArray();
            for (int j = 0; j < (letters.length - 1); ++j) {
                WordsTreeNode temp = cursor.getLetters().get(letters[j]);
                if (temp == null) {
                    temp = new WordsTreeNode();
                    cursor.getLetters().put(letters[j], temp);
                }
                cursor = temp;
            }
            WordsTreeNode contentNode = cursor.getLetters().get(letters[letters.length - 1]);
            if (contentNode == null) {
                contentNode = new WordsTreeNode();
                cursor.getLetters().put(letters[letters.length - 1], contentNode);
            }
            contentNode.setIsTemporary(false);
            contentNode.setContent(tokens.get(i));

            cursor = englishTreeRoot;
        }

    }

    public void createPersianTree() {
        WordsTreeNode cursor = persianTreeRoot;
        Vector<DatabaseManager.TokenTableRow> tokens = databaseManager.getPersianTokens();
        for (int i = 0; i < tokens.size(); ++i) {
            char[] letters = tokens.get(i).getWord().toLowerCase().toCharArray();
            for (int j = 0; j < (letters.length - 1); ++j) {
                WordsTreeNode temp = cursor.getLetters().get(letters[j]);
                if (temp == null) {
                    temp = new WordsTreeNode();
                    cursor.getLetters().put(letters[j], temp);
                }
                cursor = temp;
            }
            WordsTreeNode contentNode = cursor.getLetters().get(letters[letters.length - 1]);
            if (contentNode == null) {
                contentNode = new WordsTreeNode();
                cursor.getLetters().put(letters[letters.length - 1], contentNode);
            }
            contentNode.setIsTemporary(false);
            contentNode.setContent(tokens.get(i));

            cursor = persianTreeRoot;
        }

    }

    public DatabaseManager.TokenTableRow searchEnglishTree(String word) {
        WordsTreeNode cursor = englishTreeRoot;
        char[] letters = word.toLowerCase().toCharArray();
        for (int i = 0; i < letters.length; ++i) {
            cursor = cursor.getLetters().get(letters[i]);
            if (cursor == null)
                return null;
        }
        return cursor.getContent();
    }

    public DatabaseManager.TokenTableRow searchPersianTree(String word) {
        WordsTreeNode cursor = persianTreeRoot;
        char[] letters = word.toLowerCase().toCharArray();
        for (int i = 0; i < letters.length; ++i) {
            cursor = cursor.getLetters().get(letters[i]);
            if (cursor == null)
                return null;
        }
        return cursor.getContent();
    }
}
