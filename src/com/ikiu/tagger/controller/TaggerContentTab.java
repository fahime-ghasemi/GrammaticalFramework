package com.ikiu.tagger.controller;

import com.ikiu.tagger.model.DatabaseManager;
import com.ikiu.tagger.model.WordsTreeManager;
import com.ikiu.tagger.model.WordsTreeNode;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.plaf.ColorUIResource;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

/**
 * Created by fahime on 9/25/15.
 */
public class TaggerContentTab extends MainContentTab implements MouseListener {

    private JPopupMenu popupMenu;
    private TaggerView taggerView;
    int language;
    WordsTreeNode treeRoot;
    Vector<DatabaseManager.TokenTableRow> tokenList;
    char[] wordSplitters = new char[]{' ', '.', ',', '?', '؟', '(', ')', '[', ']', '!', '{', '}', '\'', '\"'};

    public TaggerContentTab(String filesystemPath, int language, TaggerView taggerView) {
        super(filesystemPath);
        this.taggerView = taggerView;
        this.language = language;
        tokenList = new Vector<>();
        textPane.addMouseListener(this);
        if(language == DatabaseManager.PERSIAN)
        {
            Locale persianLocale= new Locale("fa","IR");
            textPane.applyComponentOrientation(ComponentOrientation.getOrientation(persianLocale));
        }
    }

    public TaggerView getTaggerView() {
        return taggerView;
    }

    public int getLanguage() {
        return language;
    }

    public Vector<DatabaseManager.TokenTableRow> getTokenList() {
        return tokenList;
    }

    private DatabaseManager.TokenTableRow findToken(int language, String word) {
        WordsTreeManager wordsTreeManager = WordsTreeManager.getInstance();
        if (language == DatabaseManager.ENGLISH)
            return wordsTreeManager.searchEnglishTree(word);
        return wordsTreeManager.searchPersianTree(word);
    }

    public void setTaggerText(int language) {

        StyledDocument document = textPane.getStyledDocument();
        int cursor = 0;
        Style wordStyle = textPane.getStyle(StyleContext.DEFAULT_STYLE);

        while (cursor < olContent.length()) {

            int index = getFirstSplitterIndex(olContent, cursor);
            if (index == Integer.MAX_VALUE)
                index = olContent.length();
            String word = olContent.substring(cursor, index);
            try {
                DatabaseManager.TokenTableRow row = findToken(language, word);
                if (row != null) {
                    wordStyle.addAttribute(StyleConstants.Foreground, new ColorUIResource(Color.green));
                    if (tokenList.indexOf(row) == -1)
                        tokenList.add(row);
                } else {
                    wordStyle.addAttribute(StyleConstants.Foreground, new ColorUIResource(Color.red));
                }
                document.insertString(document.getLength(), word, wordStyle);

                if (index != olContent.length()) {
                    if (olContent.charAt(index) == ' ')
                        wordStyle.addAttribute(StyleConstants.Foreground, new ColorUIResource(Color.white));
                    else
                        wordStyle.addAttribute(StyleConstants.Foreground, new ColorUIResource(Color.black));
                    document.insertString(document.getLength(), String.valueOf(olContent.charAt(index)), wordStyle);
                }
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
            cursor = index + 1;
        }
    }

    int getFirstSplitterIndex(String content, int fromIndex) {
        int index = Integer.MAX_VALUE;
        for (int i = 0; i < wordSplitters.length; ++i) {
            int find = content.indexOf(wordSplitters[i], fromIndex);
            if (find != -1 && find < index)
                index = find;
        }
        return index;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.isPopupTrigger() && textPane.getSelectedText() != null) {
            popupMenu = new PopupMenuTagger(this, textPane);
            popupMenu.show((JComponent) e.getSource(), e.getX(), e.getY());
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
