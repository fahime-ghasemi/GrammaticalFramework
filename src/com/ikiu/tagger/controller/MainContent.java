package com.ikiu.tagger.controller;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JScrollPane;

/**
 * Created by fahime on 9/4/15.
 */
public class MainContent {
    RSyntaxTextArea syntaxTextArea;
    public JComponent getComponent()
    {
        syntaxTextArea = new RSyntaxTextArea(35,50);
        JScrollPane scrollPane = new JScrollPane(syntaxTextArea);
        return scrollPane;
    }

    public void setTextAreaContent(String filesystemPath) {

        try {
            FileReader reader = new FileReader(filesystemPath);
            BufferedReader bufferedReader = new BufferedReader(reader);
            syntaxTextArea.read(bufferedReader,null);
            bufferedReader.close();
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
