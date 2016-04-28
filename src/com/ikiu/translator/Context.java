package com.ikiu.translator;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by fahime on 3/3/16.
 */
public class Context extends JPanel {
    JPanel toolbar;
    JButton translateButton;
    JButton swapLanguages;
    JButton importFile;
    LanguagePanel leftPanel;
    LanguagePanel rightPanel;
    String leftLang;
    String rightLang;
    private ActionListener translateButtonActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String requests[] = leftPanel.getText().split("\n");
            StringBuilder responses = new StringBuilder();
            for (int i = 0; i < requests.length; ++i) {
                String response = GFShell.parseAndLinearize(leftLang, requests[i].trim(), rightLang);
                if (response.equals("\r\n"))
                    responses.append("unsupported\n");
                else {
                    responses.append(removeDuplicates(response));
                }
            }
            rightPanel.setText(responses.toString());
        }
    };

    private ActionListener importFileButtonActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int returnVal = jFileChooser.showOpenDialog(Context.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                java.io.File file = jFileChooser.getSelectedFile();
                try {
                    InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(new File(file.getAbsolutePath())), "UTF-8");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder fileContent = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        fileContent.append(line).append("\r\n");
                    }
                    leftPanel.setText(fileContent.toString());
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

    public String removeDuplicates(String response) {
        String allOutputs[] = response.split("\r\n");
        Set<String> uniqueOutputs = new LinkedHashSet<>();
        for (int j = 0; j < allOutputs.length; ++j) {
            uniqueOutputs.add(allOutputs[j].trim());
        }
        StringBuilder output = new StringBuilder();
        for (String word : uniqueOutputs) {
            output.append(word + "\n");
        }
        return output.toString();
    }

    private ActionListener swapLanguagesButtonActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (leftPanel.getHeader().equals("English"))
                persianToEnglishView();
            else if (leftPanel.getHeader().equals("Persian"))
                englishToPersianView();
        }
    };

    public Context() {
        this.leftLang = "Eng";
        this.rightLang = "Pes";
        this.toolbar = new JPanel(new FlowLayout());
        importFile = new JButton("Import File");
        importFile.addActionListener(importFileButtonActionListener);
        toolbar.add(importFile);
        translateButton = new JButton("Translate");
        translateButton.addActionListener(translateButtonActionListener);
        toolbar.add(translateButton);
        swapLanguages = new JButton("Swap Languages");
        swapLanguages.addActionListener(swapLanguagesButtonActionListener);
        toolbar.add(swapLanguages);


        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        leftPanel = new LanguagePanel("English");
        rightPanel = new LanguagePanel("Persian");
        rightPanel.setEnabled(false);

        JSplitPane languagePanels = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        languagePanels.setResizeWeight(0.5);        //----
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 0;
//        constraints.gridwidth = 1;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        add(languagePanels, constraints);

        constraints.fill = GridBagConstraints.NONE;
//        constraints.gridwidth = 2;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        add(toolbar, constraints);
        //---
        leftPanel.getTextPane().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {

            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (leftPanel.getText().length() == 0)
                    rightPanel.setText("");
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
    }

    public void englishToPersianView() {
        leftPanel.setHeader("English");
        rightPanel.setHeader("Persian");
        leftLang = "Eng";
        rightLang = "Pes";
    }

    public void persianToEnglishView() {
        leftPanel.setHeader("Persian");
        rightPanel.setHeader("English");
        leftLang = "Pes";
        rightLang = "Eng";
    }
}
