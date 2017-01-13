package com.ikiu.tagger.controller;

import com.ikiu.tagger.model.DatabaseManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.StyledEditorKit;

/**
 * Created by fahime on 9/4/15.
 */
public class MainContent extends JPanel implements MouseListener {

    protected JTabbedPane jTabbedPane;
    protected Context context;
    TaggerView taggerView;
    EnglishPanel english;
    PersianPanel persian;
    boolean hasOpenedTab = false;
    private MainContentOpenTabListener listener;

    public interface MainContentOpenTabListener {
        public void onTabAdd();
        public void onTabRemove();
    }

    public void setListener(MainContentOpenTabListener listener) {
        this.listener = listener;
    }

    public MainContent(Context context) {
        setLayout(new BorderLayout());
        this.jTabbedPane = new JTabbedPane();
        jTabbedPane.addMouseListener(this);
        jTabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (jTabbedPane.getTabCount() > 0) {
                    hasOpenedTab = true;
                    if (listener != null)
                        listener.onTabAdd();
                }
                else
                {
                    hasOpenedTab = false;
                    if (listener != null)
                        listener.onTabRemove();
                }

            }
        });
        this.context = context;
        add(this.jTabbedPane);
    }

    public Context getContainer() {
        return context;
    }

    public JComponent getComponent() {
        return jTabbedPane;
    }

    public TaggerView getTaggerView() {
        return taggerView;
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

    public void openTranslator() {
        int index = -1;
        int i;
        for (i = 0; i <= jTabbedPane.getTabCount() - 1; i++)//To find current index of tab
        {
            if (jTabbedPane.getTitleAt(i).equals("Translator"))
                break;
        }
        if (i != jTabbedPane.getTabCount())
            index = i;
        if (index == -1) {
            com.ikiu.translator.Context contentPane = new com.ikiu.translator.Context();

            jTabbedPane.addTab("Translator", contentPane);
            jTabbedPane.setTabComponentAt(jTabbedPane.getTabCount() - 1, createTabHead("Translator"));
            jTabbedPane.setSelectedIndex(jTabbedPane.getTabCount() - 1);
        } else
            jTabbedPane.setSelectedIndex(index);

    }

    public void showTaggerTab(String path) {
        if (taggerView == null) {
            taggerView = new TaggerView();
            taggerView.setTaggerBottomBar(new TaggerBottomBar(taggerView));
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

    public void saveCurrentTabChanges() {
        try {
            int currentTabIndex = jTabbedPane.getSelectedIndex();
            MainContentTab componentAt = (MainContentTab) jTabbedPane.getComponentAt(currentTabIndex);
            if (componentAt.isChanged())
                componentAt.saveChanges();
        } catch (Exception ignore) {

        }

    }

    public boolean hasOpenedTab() {
        return hasOpenedTab;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
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
