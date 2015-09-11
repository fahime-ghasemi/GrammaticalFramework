package com.ikiu.tagger.controller;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Created by fahime on 9/11/15.
 */
public class MainContentTab extends JScrollPane {
    JTextArea textArea;
    String filesystemPath;
    String olContent;

    public MainContentTab(String filesystemPath) {
        super();
        this.filesystemPath = filesystemPath;
        //--------
        this.textArea = new JTextArea();
        Font font=textArea.getFont();
        textArea.setFont(font.deriveFont(20f));
        getViewport().add(textArea);
        //--
        try {

            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(new File(filesystemPath)), "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder fileContent = new StringBuilder();
            String line;
            while ((line=bufferedReader.readLine())!=null)
            {
                fileContent.append(line).append("\r\n");
            }

            olContent = fileContent.toString();
            textArea.setText(fileContent.toString());
            bufferedReader.close();
            inputStreamReader.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public boolean isChanged()
    {
        if(!olContent.equals(textArea.getText()))
            return true;
        return false;
    }
    public void saveChanges()
    {
        int select = JOptionPane.showConfirmDialog(null, "Do you want to save?", "Info", JOptionPane.YES_NO_OPTION);
        if (select == JOptionPane.YES_OPTION) {
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filesystemPath));
                bufferedWriter.write(textArea.getText());
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
