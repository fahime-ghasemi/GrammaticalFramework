package com.ikiu.tagger.controller;

import com.ikiu.tagger.model.DatabaseManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

/**
 * Created by fahime on 9/24/15.
 */
public class EnglishPanel extends JPanel implements MouseListener{

    protected JTabbedPane jTabbedPane;
    protected TaggerView context;
    boolean isSelected;

    public EnglishPanel(TaggerView context) {
        this.context = context;
        this.jTabbedPane = new JTabbedPane();
        jTabbedPane.addMouseListener(this);
        setLayout(new BorderLayout());
        add(jTabbedPane);
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public void select() {
        jTabbedPane.setBorder(BorderFactory.createLineBorder(Color.blue));
        isSelected = true;
//        context.setCurrentPanel(this);
    }
    public void deSelect() {
        jTabbedPane.setBorder(null);
        isSelected = false;
    }
    public void setTextAreaContent(String filesystemPath) {

        int l = filesystemPath.lastIndexOf("/");
        String fileName = filesystemPath.substring(l + 1);

        jTabbedPane.addTab(fileName, new TaggerContentTab(filesystemPath, DatabaseManager.ENGLISH,null));
        jTabbedPane.setTabComponentAt(jTabbedPane.getTabCount() - 1, createTabHead(fileName));
        jTabbedPane.setSelectedIndex(jTabbedPane.getTabCount() - 1);
    }

    public JPanel createTabHead(String title) {
        final String st = title;
        JPanel pnlTab = new JPanel();
        pnlTab.setLayout(new BoxLayout(pnlTab, BoxLayout.LINE_AXIS));
        pnlTab.setOpaque(false);
        JButton btnClose = new JButton("x");
        JLabel lblTitle = new JLabel(title + "    ");
        btnClose.setBorderPainted(false);
        btnClose.setOpaque(false);

        btnClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                int i;
                for (i = 0; i <= jTabbedPane.getTabCount() - 1; i++)//To find current index of tab
                {
                    if (st.equals(jTabbedPane.getTitleAt(i)))
                        break;
                }
                if ( jTabbedPane.getComponentAt(i) instanceof MainContentTab && ((MainContentTab) jTabbedPane.getComponentAt(i)).isChanged()) {
                    ((MainContentTab) jTabbedPane.getComponentAt(i)).saveChanges();
                }
                jTabbedPane.removeTabAt(i);
            }
        });

        pnlTab.add(lblTitle);
        pnlTab.add(btnClose);
        return pnlTab;
    }

    public void setTextPaneContent(String filesystemPath, int language) {
        int l = filesystemPath.lastIndexOf("/");
        String fileName = filesystemPath.substring(l + 1);
        int index = getTabIndex(fileName, filesystemPath);
        if (index == -1) {
            TaggerContentTab mainContentTab = new TaggerContentTab(filesystemPath,DatabaseManager.ENGLISH, new TaggerContentTab.MainContentTabListener() {
                @Override
                public void onContentChangeListener(Vector<DatabaseManager.TokenTableRow> tokenTableRows) {
                    if (language == DatabaseManager.ENGLISH)
                        context.refreshEnglishTags(tokenTableRows);
                    else
                        context.refreshPersianTags(tokenTableRows);
                }
            });
            mainContentTab.setTaggerText(language);
            jTabbedPane.addTab(fileName, mainContentTab);
            jTabbedPane.setTabComponentAt(jTabbedPane.getTabCount() - 1, createTabHead(fileName));
            jTabbedPane.setSelectedIndex(jTabbedPane.getTabCount() - 1);
        } else {
            jTabbedPane.setSelectedIndex(index);
        }
    }

    private int getTabIndex(String fileName, String filePath) {
        int i;
        for (i = 0; i <= jTabbedPane.getTabCount() - 1; i++)//To find current index of tab
        {
            TaggerContentTab contentTab = (TaggerContentTab) jTabbedPane.getComponentAt(i);
            if (jTabbedPane.getTitleAt(i).equals(fileName) && contentTab.getFilesystemPath().equals(filePath)) {
                    break;
            }
        }
        if (i != jTabbedPane.getTabCount())
            return i;
        return -1;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!e.isPopupTrigger()) {
//            if (context.getCurrentPanel() != null) {
//                context.getCurrentPanel().deSelect();
//            }
            this.select();
            context.deSelectPersianTab();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

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
