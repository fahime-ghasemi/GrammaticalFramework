package com.ikiu.tagger.controller;

import com.ikiu.tagger.model.DatabaseManager;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Vector;

import javax.print.attribute.AttributeSet;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.plaf.ColorUIResource;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.xml.crypto.Data;

/**
 * Created by fahime on 9/11/15.
 */
public class MainContentTab extends JScrollPane {
    protected JTextPane textPane;
    String filesystemPath;
    String olContent;

    public MainContentTab(String filesystemPath) {
        super();
        this.filesystemPath = filesystemPath;
        //--------

        this.textPane = new JTextPane();
        Font font = textPane.getFont();
        textPane.setFont(font.deriveFont(20f));
        getViewport().add(textPane);
        //--
        try {

            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(new File(filesystemPath)), "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder fileContent = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                fileContent.append(line).append("\r\n");
            }
            olContent = fileContent.toString();

            bufferedReader.close();
            inputStreamReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public String getFilesystemPath() {
        return filesystemPath;
    }

    public void setGeneralText() {
        textPane.setText(olContent);
    }

    public boolean isChanged() {

        if (!olContent.equals(textPane.getText()))
            return true;
        return false;
    }

    public void saveChanges() {
        int select = JOptionPane.showConfirmDialog(null, "Do you want to save?", "Info", JOptionPane.YES_NO_OPTION);
        if (select == JOptionPane.YES_OPTION) {
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filesystemPath));
                bufferedWriter.write(textPane.getText());
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public interface MainContentTabListener {
        public void onContentChangeListener(Vector<DatabaseManager.TokenTableRow> tokenTableRows);
    }
}
