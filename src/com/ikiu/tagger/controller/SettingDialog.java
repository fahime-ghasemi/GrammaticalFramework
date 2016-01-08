package com.ikiu.tagger.controller;

import com.ikiu.tagger.util.ConfigurationTask;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by fahime on 12/25/15.
 */
public class SettingDialog {
    private ConfigurationTask configurationTask;
    final JDialog settingDialog = new JDialog();
    JTextField txtWorkspace;
    JTextField txtCore;


    public SettingDialog() {
        configurationTask = ConfigurationTask.getInstance();
        txtCore = new JTextField(50);
        txtWorkspace = new JTextField(50);
        settingDialog.setResizable(false);
        JLabel fileLabel = new JLabel("Workspace :");
        txtWorkspace.setText(configurationTask.getWorkspace());
        final JButton btnWorkspace = new JButton("Select Directory");

        JLabel coreLabel = new JLabel("          Core :");
        txtCore.setText(configurationTask.getCore());
        final JButton btnCore = new JButton("Select Directory");


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


        settingDialog.add(fileLabel);
        settingDialog.add(txtWorkspace);
        settingDialog.add(btnWorkspace);
        settingDialog.add(coreLabel);
        settingDialog.add(txtCore);
        settingDialog.add(btnCore);
    }

    public void show() {

        settingDialog.pack();
//            settingDialog.setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
        settingDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        settingDialog.setVisible(true);
        settingDialog.setSize(900, 180);
    }

    public void addCancelButton() {
        JButton btnCancelConfig = new JButton("Cancel");
        btnCancelConfig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settingDialog.dispose();

            }
        });
        settingDialog.add(btnCancelConfig);

    }

    public void addSaveButton() {
        JButton btnSaveConfig = new JButton("Save");

        btnSaveConfig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                configurationTask.setWorkspace(txtWorkspace.getText());
                configurationTask.setCore(txtCore.getText());

                settingDialog.dispose();

            }
        });


        settingDialog.add(btnSaveConfig);

    }

    public void addNextButton() {
        JButton btnNextConfig = new JButton("Next");

        btnNextConfig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (txtWorkspace.getText() == null || txtWorkspace.getText().equals("") ||
                        txtCore.getText() == null || txtCore.getText().equals(""))
                    return;

                configurationTask.setWorkspace(txtWorkspace.getText());
                configurationTask.setCore(txtCore.getText());

                settingDialog.dispose();
                Context.showGUI();

            }
        });
        settingDialog.add(btnNextConfig);

    }
}
