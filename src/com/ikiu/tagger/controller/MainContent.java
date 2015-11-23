package com.ikiu.tagger.controller;

import com.ikiu.tagger.model.DatabaseManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

/**
 * Created by fahime on 9/4/15.
 */
public class MainContent extends JPanel implements MouseListener {

    protected JTabbedPane jTabbedPane;
    protected Context context;
    TaggerView taggerView;
    EnglishPanel english;
    PersianPanel persian;

    public MainContent(Context context) {
        setLayout(new BorderLayout());
        this.jTabbedPane = new JTabbedPane();
        jTabbedPane.addMouseListener(this);
        this.context = context;
        add(this.jTabbedPane);
    }

    public Context getContainer() {
        return context;
    }

    public JComponent getComponent() {
        return jTabbedPane;
    }

    public void select() {
        jTabbedPane.setBorder(BorderFactory.createLineBorder(Color.blue));
        context.setCurrentPanel(this);
    }

    public void deSelect() {
        jTabbedPane.setBorder(null);
    }

    public void setTextAreaContent(String filesystemPath) {

        int l = filesystemPath.lastIndexOf("/");
        String fileName = filesystemPath.substring(l + 1);

        int index = getTabIndex(fileName, filesystemPath);
        if (index == -1) {
            MainContentTab mainContentTab = new MainContentTab(filesystemPath);
            mainContentTab.setGeneralText();
            jTabbedPane.addTab(fileName, mainContentTab);
            jTabbedPane.setTabComponentAt(jTabbedPane.getTabCount() - 1, createTabHead(fileName));
            jTabbedPane.setSelectedIndex(jTabbedPane.getTabCount() - 1);
        } else {
            jTabbedPane.setSelectedIndex(index);
        }
    }

    public void showTaggerTab(String path) {
        if (taggerView == null) {
            taggerView = new TaggerView();
            taggerView.setTaggerBottomBar(new TaggerBottomBar());
            english = new EnglishPanel(taggerView);
            english.setTextPaneContent(path, DatabaseManager.ENGLISH);

            persian = new PersianPanel(taggerView);
            JSplitPane languagePanels = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, english, persian);
            languagePanels.setResizeWeight(0.5);

            taggerView.setLanguagePanels(languagePanels);
            jTabbedPane.addTab("Tagger", taggerView);
            jTabbedPane.setTabComponentAt(jTabbedPane.getTabCount() - 1, createTabHead("Tagger"));
            jTabbedPane.setSelectedIndex(jTabbedPane.getTabCount() - 1);
        } else {
            int index = getTabIndex("Tagger", null);
            jTabbedPane.setSelectedIndex(index);
            if (persian.isSelected)
                persian.setTextPaneContent(path, DatabaseManager.PERSIAN);
            else
                english.setTextPaneContent(path, DatabaseManager.ENGLISH);
        }


//        context.setContentPane(taggerView);
//        context.revalidate();


    }

    private int getTabIndex(String fileName, String filePath) {
        int i;
        for (i = 0; i <= jTabbedPane.getTabCount() - 1; i++)//To find current index of tab
        {
            if (jTabbedPane.getTitleAt(i).equals(fileName)) {
                if (filePath != null && jTabbedPane.getComponentAt(i) instanceof MainContentTab) {
                    MainContentTab contentTab = (MainContentTab) jTabbedPane.getComponentAt(i);
                    if (contentTab.getFilesystemPath().equals(filePath))
                        break;
                } else
                    break;
            }
        }
        if (i != jTabbedPane.getTabCount())
            return i;
        return -1;
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
                if (jTabbedPane.getComponentAt(i) instanceof MainContentTab && ((MainContentTab) jTabbedPane.getComponentAt(i)).isChanged()) {
                    ((MainContentTab) jTabbedPane.getComponentAt(i)).saveChanges();
                }
                if (jTabbedPane.getComponentAt(i) instanceof TaggerView)
                    taggerView = null;
                jTabbedPane.removeTabAt(i);
            }
        });

        pnlTab.add(lblTitle);
        pnlTab.add(btnClose);
        return pnlTab;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!e.isPopupTrigger()) {
            if (context.getCurrentPanel() != null) {
                context.getCurrentPanel().deSelect();
            }
            this.select();
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
