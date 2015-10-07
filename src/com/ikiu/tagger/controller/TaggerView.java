package com.ikiu.tagger.controller;

import com.ikiu.tagger.util.ConfigurationTask;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Created by fahime on 9/25/15.
 */
public class TaggerView extends JSplitPane {
    private MainPart container;
    private TaggerBottomBar taggerBottomBar;

    public TaggerView(MainPart container) {
        this.container = container;
        JPanel englishPanel=new JPanel(new BorderLayout());
        MainContent english = new EnglishPanel(container);
        englishPanel.add(english.getComponent());

        JPanel persianPanel=new JPanel(new BorderLayout());
        MainContent persian = new PersianPanel(container);
        persianPanel.add(persian.getComponent());

        taggerBottomBar = new TaggerBottomBar();
        //------------------------------
        container.setCurrentPanel(english);
        //----------------------
        JPanel projectExplorer=new JPanel(new BorderLayout());
        DefaultMutableTreeNode top = new DefaultMutableTreeNode(new Tree.TreeNodeData("Project Explorer","root",(new ConfigurationTask()).getWorkspace()), true);
        Tree tree=new Tree(top,container);
        JScrollPane jScrollPane = new JScrollPane((JTree)tree);
        projectExplorer.add(jScrollPane);

        //-----------------------------------------------
        JSplitPane languagePanels=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,englishPanel,persianPanel);

        languagePanels.setResizeWeight(0.5);
        JSplitPane mainContent=new JSplitPane(JSplitPane.VERTICAL_SPLIT,languagePanels, taggerBottomBar);
        mainContent.setResizeWeight(0.6);
        //----
        setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        setLeftComponent(projectExplorer);
        setRightComponent(mainContent);
        setResizeWeight(0.1);
    }

    public void addEnglishTag(String word)
    {
        taggerBottomBar.addEnglishTag(word);
    }
    public void addPersianTag(String word)
    {
        taggerBottomBar.addPersianTag(word);
    }
}
