package com.ikiu.tagger.controller;

import com.ikiu.tagger.model.DatabaseManager;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;


/**
 * Created by fahime on 9/4/15.
 */
public class PopupMenuTagger extends JPopupMenu implements ActionListener {
    private JMenuItem menuItemNoun;
    private JMenuItem menuItemV2;
    private JMenuItem menuItemAdjective;
    private JMenuItem menuItemN2;

    private JMenuItem menuItemTag;
    private JTextPane source;
    private TaggerContentTab rootInvoker;

    public PopupMenuTagger(TaggerContentTab rootInvoker, JTextPane source) {
        this.rootInvoker = rootInvoker;
        this.source = source;
        menuItemTag=new JMenu("Add Tag");
        menuItemNoun = new JMenuItem("Noun");
        menuItemNoun.addActionListener(this);
        menuItemNoun.setActionCommand("noun");

        menuItemAdjective = new JMenuItem("Adjective");
        menuItemAdjective.addActionListener(this);
        menuItemAdjective.setActionCommand("adjective");

        menuItemN2 = new JMenuItem("N2");
        menuItemN2.addActionListener(this);
        menuItemN2.setActionCommand("n2");

        menuItemV2 = new JMenuItem("V2");
        menuItemV2.addActionListener(this);
        menuItemV2.setActionCommand("v2");


        menuItemTag.add(menuItemNoun);
        menuItemTag.add(menuItemAdjective);
        menuItemTag.add(menuItemV2);
        menuItemTag.add(menuItemN2);
        add(menuItemTag);
        setOpaque(true);
        setLightWeightPopupEnabled(true);

    }


    @Override
    public void actionPerformed(ActionEvent e) {

        DatabaseManager.TokenTableRow insertToken = new DatabaseManager.TokenTableRow();
        insertToken.setType(e.getActionCommand());
        String trimedString = source.getSelectedText().trim();
        trimedString = trimedString.replaceAll("\uFEFF", "");

        insertToken.setWord(trimedString);
        TaggerView taggerView = rootInvoker.getTaggerView();
        if (rootInvoker.getLanguage() == DatabaseManager.ENGLISH) {
            DatabaseManager.TokenTableRow row = taggerView.addEnglishTag(insertToken);
            if (row.getId() > 0) {
                updateTextColor();
                rootInvoker.wordsTreeManager.addEnglishTag(row);

            }
        } else if (rootInvoker.getLanguage() == DatabaseManager.PERSIAN) {
            DatabaseManager.TokenTableRow row = taggerView.addPersianTag(insertToken);
            if (row.getId() > 0) {
                updateTextColor();
                rootInvoker.wordsTreeManager.addPersianTag(row);

            }
        }


    }

    private void updateTextColor() {
        StyledDocument doc = source.getStyledDocument();
        int from = source.getSelectionStart();
        int to = source.getSelectionEnd();
        Style style = source.getStyle(StyleContext.DEFAULT_STYLE);
        style.addAttribute(StyleConstants.Foreground, new ColorUIResource(Color.green));
        doc.setCharacterAttributes(from, to - from, style, true);
    }
}
