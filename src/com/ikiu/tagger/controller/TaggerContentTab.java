package com.ikiu.tagger.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;

/**
 * Created by fahime on 9/25/15.
 */
public class TaggerContentTab extends MainContentTab implements MouseListener {

    private JPopupMenu popupMenu;
    private MainContent container;
    int language;
    public TaggerContentTab(String filesystemPath,int language,MainContent container) {
        super(filesystemPath);
        this.container = container;
        this.language = language;
        textArea.addMouseListener(this);
    }

    public MainContent getContainer()
    {
        return container;
    }

    public int getLanguage() {
        return language;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.isPopupTrigger() && textArea.getSelectedText()!=null)
        {
            popupMenu = new PopupMenuTagger(this,textArea.getSelectedText());
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
