package com.ikiu.tagger.controller.popupmenu;

import com.ikiu.tagger.controller.TaggerContentTab;
import com.ikiu.tagger.controller.TaggerView;
import com.ikiu.tagger.controller.lexicon.CustomTag;
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
    private JMenuItem menuItemV2S;
    private JMenuItem menuItemV2Q;
    private JMenuItem menuItemV2V;
    private JMenuItem menuItemV2A;
    private JMenuItem menuItemVA;
    private JMenuItem menuItemN3;
    private JMenuItem menuItemA2V;
    private JMenuItem menuItemVS;
    private JMenuItem menuItemAV;
    private JMenuItem menuItemRegV;
    private JMenuItem menuItemIrregV;
    private JMenuItem menuItemPN;
    private JMenuItem menuItemV3;
    private JMenuItem menuItemVQ;
    private JMenuItem menuItemAdv;
    private JMenuItem menuItemCustom;

    private JMenuItem menuItemTag;
    private JTextPane generalSource;
    private JTextPane englishSource;
    private JTextPane persianSource;
    private TaggerContentTab rootInvoker;

    public PopupMenuTagger(TaggerContentTab rootInvoker, JTextPane source) {
        this.rootInvoker = rootInvoker;
        this.generalSource = source;
        preparePopup();

    }

    public void preparePopup() {
        menuItemTag = new JMenu("Add Tag");
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

        menuItemV2S = new JMenuItem("V2S");
        menuItemV2S.addActionListener(this);
        menuItemV2S.setActionCommand("v2s");

        menuItemV2Q = new JMenuItem("V2Q");
        menuItemV2Q.addActionListener(this);
        menuItemV2Q.setActionCommand("v2q");

        menuItemV2V = new JMenuItem("V2V");
        menuItemV2V.addActionListener(this);
        menuItemV2V.setActionCommand("v2v");

        menuItemV2A = new JMenuItem("V2A");
        menuItemV2A.addActionListener(this);
        menuItemV2A.setActionCommand("v2a");

        menuItemVA = new JMenuItem("VA");
        menuItemVA.addActionListener(this);
        menuItemVA.setActionCommand("va");

        menuItemN3 = new JMenuItem("N3");
        menuItemN3.addActionListener(this);
        menuItemN3.setActionCommand("n3");

        menuItemA2V = new JMenuItem("A2V");
        menuItemA2V.addActionListener(this);
        menuItemA2V.setActionCommand("a2v");

        menuItemVS = new JMenuItem("VS");
        menuItemVS.addActionListener(this);
        menuItemVS.setActionCommand("vs");

        menuItemAV = new JMenuItem("AV");
        menuItemAV.addActionListener(this);
        menuItemAV.setActionCommand("av");

        menuItemRegV = new JMenuItem("Regular Verb");
        menuItemRegV.addActionListener(this);
        menuItemRegV.setActionCommand("regular verb");

        menuItemIrregV = new JMenuItem("Irregular Verb");
        menuItemIrregV.addActionListener(this);
        menuItemIrregV.setActionCommand("irregular verb");

        menuItemPN = new JMenuItem("PN");
        menuItemPN.addActionListener(this);
        menuItemPN.setActionCommand("pn");

        menuItemV3 = new JMenuItem("V3");
        menuItemV3.addActionListener(this);
        menuItemV3.setActionCommand("v3");

        menuItemVQ = new JMenuItem("VQ");
        menuItemVQ.addActionListener(this);
        menuItemVQ.setActionCommand("vq");

        menuItemAdv = new JMenuItem("Adv");
        menuItemAdv.addActionListener(this);
        menuItemAdv.setActionCommand("adv");

        menuItemCustom = new JMenuItem("Custom");
        menuItemCustom.addActionListener(this);
        menuItemCustom.setActionCommand("custom");


        menuItemTag.add(menuItemNoun);
        menuItemTag.add(menuItemAdjective);
        menuItemTag.add(menuItemV2);
        menuItemTag.add(menuItemN2);
        menuItemTag.add(menuItemV2S);
        menuItemTag.add(menuItemV2Q);
        menuItemTag.add(menuItemV2V);
        menuItemTag.add(menuItemV2A);
        menuItemTag.add(menuItemVA);
        menuItemTag.add(menuItemN3);
        menuItemTag.add(menuItemA2V);
        menuItemTag.add(menuItemVS);
        menuItemTag.add(menuItemAV);
        menuItemTag.add(menuItemRegV);
        menuItemTag.add(menuItemIrregV);
        menuItemTag.add(menuItemPN);
        menuItemTag.add(menuItemV3);
        menuItemTag.add(menuItemVQ);
        menuItemTag.add(menuItemAdv);
        menuItemTag.add(menuItemCustom);
        add(menuItemTag);
        setOpaque(true);
        setLightWeightPopupEnabled(true);
    }

    public PopupMenuTagger(TaggerContentTab rootInvoker, JTextPane englishSource, JTextPane persianSource) {
        this.rootInvoker = rootInvoker;
        this.englishSource = englishSource;
        this.persianSource = persianSource;
        preparePopup();
    }

    private void openCustomDialog(String trimedString) {
        CustomTag customTag = new CustomTag(trimedString);
        customTag.display(new CustomTag.CustomListener() {
            @Override
            public void onOkListener(String type, String word) {
                addToken(type, word);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (generalSource == null) {
            DatabaseManager.TokenTableRow persianToken = new DatabaseManager.TokenTableRow();
            persianToken.setType(e.getActionCommand());
            String trimedString = persianSource.getSelectedText().trim();
            trimedString = trimedString.replaceAll("\uFEFF", "");
            persianToken.setWord(trimedString);
            TaggerView taggerView = rootInvoker.getTaggerView();
            DatabaseManager.TokenTableRow row = taggerView.addPersianTag(persianToken);
            int persianId = row.getId();
            if (persianId > 0) {
                updatePersianTextColor();
                rootInvoker.wordsTreeManager.addPersianTag(row);

                DatabaseManager.TokenTableRow englishToken = new DatabaseManager.TokenTableRow();
                englishToken.setType(e.getActionCommand());
                trimedString = englishSource.getSelectedText().trim();
                trimedString = trimedString.replaceAll("\uFEFF", "");
                englishToken.setWord(trimedString);
                englishToken.setMeaning(persianId);
                row = taggerView.addEnglishTag(englishToken);
                if (row.getId() > 0) {
                    updateEnglishTextColor();
                    rootInvoker.wordsTreeManager.addEnglishTag(row);

                }
            }


        } else {
            String trimedString = generalSource.getSelectedText().trim();
            trimedString = trimedString.replaceAll("\uFEFF", "");

            if(e.getActionCommand().equals("custom"))
                openCustomDialog(trimedString);
            else {

                addToken(e.getActionCommand(), trimedString);
            }
        }

    }

    private void addToken(String type, String word) {
        DatabaseManager.TokenTableRow insertToken = new DatabaseManager.TokenTableRow();
        insertToken.setType(type);
        insertToken.setWord(word);
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
        StyledDocument doc = generalSource.getStyledDocument();
        int from = generalSource.getSelectionStart();
        int to = generalSource.getSelectionEnd();
        Style style = generalSource.getStyle(StyleContext.DEFAULT_STYLE);
        style.addAttribute(StyleConstants.Foreground, new ColorUIResource(Color.green));
        doc.setCharacterAttributes(from, to - from, style, true);
    }

    private void updateEnglishTextColor() {
        StyledDocument doc = englishSource.getStyledDocument();
        int from = englishSource.getSelectionStart();
        int to = englishSource.getSelectionEnd();
        Style style = englishSource.getStyle(StyleContext.DEFAULT_STYLE);
        style.addAttribute(StyleConstants.Foreground, new ColorUIResource(Color.green));
        doc.setCharacterAttributes(from, to - from, style, true);
    }

    private void updatePersianTextColor() {
        StyledDocument doc = persianSource.getStyledDocument();
        int from = persianSource.getSelectionStart();
        int to = persianSource.getSelectionEnd();
        Style style = persianSource.getStyle(StyleContext.DEFAULT_STYLE);
        style.addAttribute(StyleConstants.Foreground, new ColorUIResource(Color.green));
        doc.setCharacterAttributes(from, to - from, style, true);
    }
}
