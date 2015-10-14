package com.ikiu.tagger.controller;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * Created by fahime on 10/9/15.
 */
public class TaggerGenerator extends JDialog {
    protected JTabbedPane tabbedPane;
    private JPanel panel;
    private JPanel toolbar;
    private JButton btnOk;
    private JButton btnCancel;

    public TaggerGenerator() throws HeadlessException {
        panel=new JPanel(new GridBagLayout());
        toolbar = new JPanel(new FlowLayout());
        btnCancel = new JButton("Cancel");
        btnOk = new JButton("Ok");
        toolbar.add(btnCancel);
        toolbar.add(btnOk);
        //---

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.weightx = 1.0;
        panel.add(toolbar,constraints);

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("English", new TaggerGeneratorTab());
        tabbedPane.addTab("Persian", new TaggerGeneratorTab());
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weighty = 1.0;
        panel.add(tabbedPane, constraints);
        //---

        setContentPane(panel);

    }

    public void display()
    {
        pack();
        setModalityType(ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
