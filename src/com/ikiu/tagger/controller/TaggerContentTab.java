package com.ikiu.tagger.controller;

import com.ikiu.tagger.model.DatabaseManager;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
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
    private MainContent container;
    int language;
    MainContentTabListener listener;
    Vector<DatabaseManager.TokenTableRow> tokenTableRows;

    public TaggerContentTab(String filesystemPath,int language, MainContentTabListener listener) {
        super(filesystemPath);
        this.listener = listener;
//        this.container = container;
        this.language = language;
        textPane.addMouseListener(this);
    }

    public MainContent getContainer()
    {
        return container;
    }

    public int getLanguage() {
        return language;
    }

    private DatabaseManager.TokenTableRow findToken(String word) {
        Iterator<DatabaseManager.TokenTableRow> iterator = tokenTableRows.iterator();
        while (iterator.hasNext()) {
            DatabaseManager.TokenTableRow row = iterator.next();
            if (row.getWord().equals(word))
                return row;
        }
        return null;
    }

    public void setTaggerText(int language) {
        DatabaseManager databaseManager = new DatabaseManager();
        if (language == DatabaseManager.ENGLISH)
            tokenTableRows = databaseManager.getEnglishTokens();
        else
            tokenTableRows = databaseManager.getPersianTokens();

        StyledDocument document = textPane.getStyledDocument();
        String[] words = olContent.split("\\W+");
        Style wordStyle = textPane.getStyle(StyleContext.DEFAULT_STYLE);

        Vector<DatabaseManager.TokenTableRow> tokenList = new Vector<>();
        for (int i = 0; i < words.length; ++i) {
            try {
                DatabaseManager.TokenTableRow row = findToken(words[i]);
                if (row != null) {
                    wordStyle.addAttribute(StyleConstants.Foreground, new ColorUIResource(Color.green));
                    document.insertString(document.getLength(), words[i], wordStyle);
                    if (tokenList.indexOf(row) == -1)
                        tokenList.add(row);
                } else {
                    wordStyle.addAttribute(StyleConstants.Foreground, new ColorUIResource(Color.red));
                    document.insertString(document.getLength(), words[i], wordStyle);
                }

                if (i != words.length - 1) {
                    wordStyle.addAttribute(StyleConstants.Foreground, new ColorUIResource(Color.white));
                    document.insertString(document.getLength(), " ", wordStyle);
                }
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
        if (listener != null)
            listener.onContentChangeListener(tokenList);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.isPopupTrigger() && textPane.getSelectedText()!=null)
        {
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
