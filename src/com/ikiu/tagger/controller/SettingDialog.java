package com.ikiu.tagger.controller;

import com.ikiu.tagger.util.ConfigurationTask;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * Created by fahime on 12/25/15.
 */
public class SettingDialog {
    private ConfigurationTask configurationTask;
    final JDialog settingDialog = new JDialog();
    JTextField txtWorkspace;
    JTextField txtCore;
    JTextField txtEnglish;
    JTextField txtPersian;
    Boolean isFromSettingMenu;

    public SettingDialog(Boolean isFromSettingMenu) {
        this.isFromSettingMenu = isFromSettingMenu;
        configurationTask = ConfigurationTask.getInstance();
        txtCore = new JTextField(50);
        txtWorkspace = new JTextField(50);
        txtEnglish = new JTextField(50);
        txtPersian = new JTextField(50);
        settingDialog.setResizable(false);
        JLabel fileLabel = new JLabel("Workspace :");
        txtWorkspace.setText(configurationTask.getWorkspace());
        final JButton btnWorkspace = new JButton("Select Directory");

        JLabel coreLabel = new JLabel("          Core :");
        txtCore.setText(configurationTask.getCore());
        final JButton btnCore = new JButton("Select Directory");

        JLabel englishFileLabel = new JLabel("English file for translator :");
        txtEnglish.setText(configurationTask.getEnglishFile());
        final JButton btnEnglishFile = new JButton("Select File");

        JLabel persianFileLabel = new JLabel("Persian file for translator :");
        txtPersian.setText(configurationTask.getPersianFile());
        final JButton btnPersianFile = new JButton("Select File");


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

        btnEnglishFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTranslatorFilePath(txtEnglish);
            }
        });

        btnPersianFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTranslatorFilePath(txtPersian);
            }
        });


        settingDialog.add(fileLabel);
        settingDialog.add(txtWorkspace);
        settingDialog.add(btnWorkspace);

        settingDialog.add(coreLabel);
        settingDialog.add(txtCore);
        settingDialog.add(btnCore);
        if (isFromSettingMenu) {
            settingDialog.add(englishFileLabel);
            settingDialog.add(txtEnglish);
            settingDialog.add(btnEnglishFile);

            settingDialog.add(persianFileLabel);
            settingDialog.add(txtPersian);
            settingDialog.add(btnPersianFile);
        }
    }

    private void setTranslatorFilePath(JTextField translatorField) {
        ConfigurationTask configurationTask = ConfigurationTask.getInstance();
        JDialog dialog = new JDialog();
        dialog.setLayout(new BorderLayout());
        JPanel treePanel = new JPanel(new GridBagLayout());

        DefaultMutableTreeNode top = new DefaultMutableTreeNode(new ProjectTree.RootNode("Project Explorer", configurationTask.getWorkspace()), true);
        TaggerChooser taggerChooser = new TaggerChooser(top);
        JScrollPane jScrollPane = new JScrollPane((JTree) taggerChooser);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weighty = 1.0;
        treePanel.add(jScrollPane, constraints);

        JPanel toolbar = new JPanel(new FlowLayout());
        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        JButton btnOk = new JButton("Ok");
        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (taggerChooser.getSelectionPath() != null) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) taggerChooser.getSelectionPath().getLastPathComponent();
                    ProjectTree.TreeNode userObject = (ProjectTree.TreeNode) node.getUserObject();
                    try {
                        translatorField.setText(userObject.getFilesystemPath());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                dialog.dispose();
            }
        });
        toolbar.add(btnCancel);
        toolbar.add(btnOk);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 1.0;
        constraints.weighty = 0.1;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        treePanel.add(toolbar, constraints);

        dialog.add(treePanel);
        dialog.pack();
        dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
    }

    public void show() {

        settingDialog.pack();
//            settingDialog.setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
        settingDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        settingDialog.setVisible(true);
        if(isFromSettingMenu)
        settingDialog.setSize(910, 220);
        else
            settingDialog.setSize(910,150);
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
                configurationTask.setEnglishFile(txtEnglish.getText());
                configurationTask.setPersianFile(txtPersian.getText());

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
