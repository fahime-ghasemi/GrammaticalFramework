package com.ikiu.tagger.controller;

import com.ikiu.tagger.model.DatabaseManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;


/**
 * Created by fahime on 9/4/15.
 */
public class PopupMenuTagger extends JPopupMenu implements ActionListener {
    private JMenuItem menuItemNoun;
    private String source;
    private TaggerContentTab rootInvoker;

    public PopupMenuTagger(TaggerContentTab rootInvoker, String source) {
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
            if(rootInvoker.getLanguage()== DatabaseManager.ENGLISH)
                context.addEnglishTag(source);
            else if(rootInvoker.getLanguage()== DatabaseManager.PERSIAN)
                context.addPersianTag(source);
            //---

        }
    }
}
