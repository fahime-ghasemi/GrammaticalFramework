package com.ikiu.tagger.controller;

import com.ikiu.tagger.model.DatabaseManager;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 * Created by fahime on 10/9/15.
 */
public class TaggerGeneratorTab extends JPanel {
    protected String template;
    protected String filePath;
    protected int filePosition;
    private JTextField txtFile;
    private JTextArea textAreaFile;
    private JTextArea textAreaTemplate;
    private JButton btnBrowse;

    public String getTemplate() {
        return textAreaTemplate.getText();
    }
    public JTextArea getFileTextArea ()
    {
        return textAreaFile;
    }

    public String getFilePath() {
        return filePath;
    }

    public int getFilePosition() {

        filePosition = textAreaFile.getText().indexOf("@position");
        textAreaFile.replaceRange("",filePosition,filePosition+8 );
        return filePosition;
    }

    private ActionListener btnBrowseActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            jFileChooser.setFileFilter(new FileNameExtensionFilter("GF Files", "gf"));
            jFileChooser.setAcceptAllFileFilterUsed(false);
            jFileChooser.setMultiSelectionEnabled(false);
            int returnVal = jFileChooser.showOpenDialog((Component) e.getSource());
            if (returnVal == JFileChooser.APPROVE_OPTION) {

                File file = jFileChooser.getSelectedFile();

                try {
                    filePath = file.getPath();
                    InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(new File(filePath)), "UTF-8");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder fileContent = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        fileContent.append(line).append("\r\n");
                    }
                    fileContent.toString();
                    textAreaFile.setText(fileContent.toString());
                    txtFile.setText(file.getPath());
                    bufferedReader.close();
                    inputStreamReader.close();
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }


            }
        }
    };

    public TaggerGeneratorTab() {

        JPanel fileSelector = new JPanel(new FlowLayout());
        fileSelector.add(new JLabel("File :"));
        txtFile = new JTextField(60);
        btnBrowse = new JButton("Browse");
        btnBrowse.addActionListener(btnBrowseActionListener);
        fileSelector.add(txtFile);
        fileSelector.add(btnBrowse);
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1.0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(fileSelector, constraints);
        //----
        JScrollPane scrollPane = new JScrollPane();
        textAreaFile = new JTextArea();
        textAreaFile.setRows(20);
        textAreaFile.setColumns(60);
        textAreaFile.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if(e.isPopupTrigger()) {
                    JPopupMenu popupMenu = new JPopupMenu();
                    JMenuItem menuItem = new JMenuItem("Set Position");
                    menuItem.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            textAreaFile.insert("@position",textAreaFile.getCaretPosition());
                        }
                    });
                    popupMenu.add(menuItem);
                    popupMenu.show((JComponent)e.getSource(),e.getX(), e.getY());
                }
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
        });
        scrollPane.getViewport().add(textAreaFile);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weighty = 0.5;
        add(scrollPane, constraints);
        //---
        JLabel lblTemplate = new JLabel("Template :");
        constraints.weighty = 0;
        constraints.ipadx = 30;
        constraints.ipady = 20;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.gridx = 0;
        constraints.gridy = 2;

        add(lblTemplate, constraints);
        constraints.fill = GridBagConstraints.BOTH;
        textAreaTemplate = new JTextArea();
        textAreaTemplate.setRows(5);
        JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.getViewport().add(textAreaTemplate);

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.weighty = 0.5;
        add(scrollPane1, constraints);
        setSize(600, 800);

    }

    public void generate()
    {
        template = textAreaTemplate.getText();

    }
}
