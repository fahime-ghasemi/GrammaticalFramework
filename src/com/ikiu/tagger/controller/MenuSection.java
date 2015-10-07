/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ikiu.tagger.controller;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 * @author Emotion
 */
class MenuSection implements ActionListener {
    JMenuBar menu1;
    private JMenuBar menu2;
    private JMenu File;
    private JMenuItem New;
    private JMenuItem Setting;
    private JMenu view;
    private JMenuItem tagger;
    private MainPart mainFrame;
    //private JFrame frame=new JFrame();

    public MenuSection(MainPart frame) {

        this.mainFrame = frame;
        File = new JMenu("File");
        New = new JMenuItem("New");
        Setting = new JMenuItem("Setting");
        File.add(New);
        New.addActionListener(this);
        File.add(Setting);
        Setting.addActionListener(this);
        //----
        view = new JMenu("View");
        tagger = new JMenuItem("Tagger");
        tagger.addActionListener(this);
        view.add(tagger);
        menu1 = new JMenuBar();
        menu1.add(File);
        menu1.add(view);
        //----

        frame.setJMenuBar(menu1);

    }

    public JMenuBar getComponent() {
        return menu1;
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        if (e.getSource() == Setting) {
            JFrame fileFrame = new JFrame();
            JButton btnSelectDirectory = new JButton("Select Directory");
            JButton btnSaveConfig = new JButton("Save");
            JTextField txtWorkspace = new JTextField(20);

            fileFrame.setLayout(new FlowLayout());
            JLabel fileLabel = new JLabel("Workspace :");

            btnSelectDirectory.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == btnSelectDirectory) {
                        JFileChooser jFileChooser = new JFileChooser();
                        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                        int returnVal = jFileChooser.showOpenDialog(fileFrame);
                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            java.io.File file = jFileChooser.getSelectedFile();
                            txtWorkspace.setText(file.getPath());

                        }

                    }
                }
            });

            btnSaveConfig.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fileFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    fileFrame.dispose();

                }
            });

            fileFrame.add(fileLabel);
            fileFrame.add(txtWorkspace);
            fileFrame.add(btnSelectDirectory);
            fileFrame.add(btnSaveConfig);

            fileFrame.setVisible(true);
            fileFrame.setSize(500, 200);
        } else if (e.getSource() == tagger) {
            mainFrame.setLayout(new BorderLayout());
            //-----

            mainFrame.setContentPane(new TaggerView(mainFrame));
            mainFrame.revalidate();

        }
    }

}
