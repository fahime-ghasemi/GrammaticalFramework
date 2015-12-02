/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ikiu.tagger.controller;

import com.ikiu.tagger.util.ConfigurationTask;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * @author Emotion
 */
class MenuSection extends JMenuBar implements ActionListener {
    private JMenu File;
    private JMenuItem Setting;
    private ConfigurationTask configurationTask;
    private Context context;

    public MenuSection(Context context) {

        this.context = context;
        configurationTask = ConfigurationTask.getInstance();
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
            final JDialog settingDialog = new JDialog();
            settingDialog.setResizable(false);
            JLabel fileLabel = new JLabel("Workspace :");
            final JTextField txtWorkspace = new JTextField(50);
            txtWorkspace.setText(configurationTask.getWorkspace());
            final JButton btnWorkspace = new JButton("Select Directory");

            JLabel coreLabel = new JLabel("          Core :");
            final JTextField txtCore = new JTextField(50);
            txtCore.setText(configurationTask.getCore());
            final JButton btnCore = new JButton("Select Directory");

            JButton btnSaveConfig = new JButton("Save");
            JButton btnCancelConfig = new JButton("Cancel");

            settingDialog.setLayout(new FlowLayout());

            btnWorkspace.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == btnWorkspace) {
                        JFileChooser jFileChooser = new JFileChooser();
                        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                        int returnVal = jFileChooser.showOpenDialog(settingDialog);
                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            java.io.File file = jFileChooser.getSelectedFile();
                            txtWorkspace.setText(file.getPath());

                        }

                    }
                }
            });
            btnCore.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == btnCore) {
                        JFileChooser jFileChooser = new JFileChooser();
                        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                        int returnVal = jFileChooser.showOpenDialog(settingDialog);
                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            java.io.File file = jFileChooser.getSelectedFile();
                            txtCore.setText(file.getPath());

                        }

                    }
                }
            });

            btnSaveConfig.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    configurationTask.setWorkspace(txtWorkspace.getText());
                    configurationTask.setCore(txtCore.getText());

                    settingDialog.dispose();

                }
            });
            btnCancelConfig.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    settingDialog.dispose();

                }
            });

            settingDialog.add(fileLabel);
            settingDialog.add(txtWorkspace);
            settingDialog.add(btnWorkspace);
            settingDialog.add(coreLabel);
            settingDialog.add(txtCore);
            settingDialog.add(btnCore);
            settingDialog.add(btnSaveConfig);
            settingDialog.add(btnCancelConfig);

            settingDialog.pack();
//            settingDialog.setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
            settingDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            settingDialog.setVisible(true);
            settingDialog.setSize(900, 180);
        }
    }

}
