package com.ikiu.tagger.controller;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Created by fahime on 9/4/15.
 */
public class MainContent {
    JTextArea textArea;
    public JComponent getComponent()
    {
         textArea = new JTextArea();
        Font font=textArea.getFont();

        textArea.setFont(font.deriveFont(20f));
        JScrollPane scrollPane = new JScrollPane(textArea);

        return scrollPane;
    }

    public void setTextAreaContent(String filesystemPath) {

        try {
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(new File(filesystemPath)),"UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            textArea.read(bufferedReader, null);
            bufferedReader.close();
            inputStreamReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
