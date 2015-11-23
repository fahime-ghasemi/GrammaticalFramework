package com.ikiu.tagger.controller;

import com.ikiu.tagger.model.DatabaseManager;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
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
    private JTextPane source;
    private TaggerContentTab rootInvoker;

    public PopupMenuTagger(TaggerContentTab rootInvoker, JTextPane source) {
        this.rootInvoker = rootInvoker;
        this.source = source;
        menuItemNoun = new JMenuItem("Noun");
        menuItemNoun.addActionListener(this);
        menuItemNoun.setActionCommand("noun");

        add(menuItemNoun);
        setOpaque(true);
        setLightWeightPopupEnabled(true);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("noun")) {

            Context context = rootInvoker.getContainer().getContainer();
            if(rootInvoker.getLanguage()== DatabaseManager.ENGLISH) {
                if(context.addEnglishTag(source.getSelectedText())>0)
                {
                    updateTextColor();

                }
            }
            else if(rootInvoker.getLanguage()== DatabaseManager.PERSIAN) {
                if(context.addPersianTag(source.getSelectedText())>0)
                {
                    updateTextColor();

                }
            }

        }
    }

    private void updateTextColor() {
        StyledDocument doc = source.getStyledDocument();
        int from = source.getSelectionStart();
        int to = source.getSelectionEnd();
        Style style = source.getStyle(StyleContext.DEFAULT_STYLE);
        style.addAttribute(StyleConstants.Foreground, new ColorUIResource(Color.green));
        doc.setCharacterAttributes(from,to-from,style, true);
    }
}
