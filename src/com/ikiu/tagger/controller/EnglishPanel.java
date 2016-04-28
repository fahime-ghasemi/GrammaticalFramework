package com.ikiu.tagger.controller;

import com.ikiu.tagger.controller.popupmenu.PopupMenuTagger;
import com.ikiu.tagger.model.DatabaseManager;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

/**
 * Created by fahime on 9/24/15.
 */
public class EnglishPanel extends JPanel implements MouseListener {

    protected JTabbedPane jTabbedPane;
    protected TaggerView context;
    boolean isSelected;

    public EnglishPanel(TaggerView context) {
        this.context = context;
        this.jTabbedPane = new JTabbedPane();
        jTabbedPane.addMouseListener(this);

        jTabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JTabbedPane source = (JTabbedPane) e.getSource();
                if (source.getSelectedIndex() == -1)
                    context.refreshEnglishTags(new Vector<DatabaseManager.TokenTableRow>());
                else {
                    TaggerContentTab contentTab = (TaggerContentTab) jTabbedPane.getComponentAt(source.getModel().getSelectedIndex());
                    context.refreshEnglishTags(contentTab.getTokenList(""));
                }
            }
        });
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0.5;
        constraints.weighty = 1;
        add(jTabbedPane, constraints);

        constraints.fill = GridBagConstraints.NONE;
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 0.0;

        JButton setMeaning = new JButton("<>");
        setMeaning.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                PopupMenuTagger popupMenu = new PopupMenuTagger(this, textPane);
//                popupMenu.show((JComponent) e.getSource(), e.getX(), e.getY());
                if (context.getEnglishPanel().getCurrentTab() == null || context.getPersianPanel().getCurrentTab() == null) {
                    JOptionPane.showMessageDialog(setMeaning, "Language panel(s) aren't open");
                    return;
                }
                if (context.getEnglishPanel().getCurrentTab().getTextPane().getSelectedText() == null ||
                        context.getPersianPanel().getCurrentTab().getTextPane().getSelectedText() == null) {
                    JOptionPane.showMessageDialog(setMeaning, "Select each token first");
                    return;
                }
                PopupMenuTagger popupMenu = new PopupMenuTagger(context.getEnglishPanel().getCurrentTab(), context.getEnglishPanel().getCurrentTab().getTextPane(), context.getPersianPanel().getCurrentTab().getTextPane());
                popupMenu.show(setMeaning, 0, setMeaning.getBounds().height);

            }
        });
        add(setMeaning, constraints);
    }

    public TaggerContentTab getCurrentTab() {
        if (jTabbedPane.getSelectedIndex() != -1)
            return (TaggerContentTab) jTabbedPane.getComponentAt(jTabbedPane.getSelectedIndex());
        return null;
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

        jTabbedPane.addTab(fileName, new TaggerContentTab(filesystemPath, DatabaseManager.ENGLISH, context));
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
                if (jTabbedPane.getComponentAt(i) instanceof MainContentTab && ((MainContentTab) jTabbedPane.getComponentAt(i)).isChanged()) {
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
            TaggerContentTab mainContentTab = new TaggerContentTab(filesystemPath, DatabaseManager.ENGLISH, context);
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
        if (e.getButton() == MouseEvent.BUTTON1) {
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
