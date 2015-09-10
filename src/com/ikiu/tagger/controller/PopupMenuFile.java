package com.ikiu.tagger.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;

/**
 * Created by fahime on 9/4/15.
 */
public class PopupMenuFile extends JPopupMenu implements ActionListener{
    private JMenuItem menuItemRename;
    private JTree tree;

    public PopupMenuFile(JTree tree) {

        this.tree =tree;
        menuItemRename = new JMenuItem("Rename");
        menuItemRename.addActionListener(this);
        menuItemRename.setActionCommand("rename");
        //---
        add(menuItemRename);
        setOpaque(true);
        setLightWeightPopupEnabled(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
