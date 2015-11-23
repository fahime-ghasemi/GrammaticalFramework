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
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 * @author Emotion
 */
class MenuSection extends JMenuBar implements ActionListener {
    private JMenu File;
    private JMenuItem Setting;

    private Context context;

    public MenuSection(Context context) {

        this.context = context;
        File = new JMenu("File");
        Setting = new JMenuItem("Setting");
        File.add(Setting);
        Setting.addActionListener(this);
        //----
        add(File);
        //----
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        if (e.getSource() == Setting) {
            final JFrame fileFrame = new JFrame();
            final JButton btnSelectDirectory = new JButton("Select Directory");
            JButton btnSaveConfig = new JButton("Save");
            final JTextField txtWorkspace = new JTextField(20);

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
        }
    }

}
