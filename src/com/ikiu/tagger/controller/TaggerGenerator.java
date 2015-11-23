package com.ikiu.tagger.controller;

import com.ikiu.tagger.model.DatabaseManager;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

/**
 * Created by fahime on 10/9/15.
 */
public class TaggerGenerator extends JDialog {
    protected JTabbedPane tabbedPane;
    private JPanel panel;
    private JPanel toolbar;
    private JButton btnOk;
    private JButton btnCancel;
    private TaggerGeneratorTab mEnglishTab;
    private TaggerGeneratorTab mPersianTab;
    private EnglishTags mEnglishTags;
    private PersianTags mPersianTags;
    public interface TaggerGeneratorListener
    {
        public void onGenerateComplete();
    }
    private TaggerGeneratorListener listener;
    public TaggerGenerator(EnglishTags englishTags, PersianTags persianTags) throws HeadlessException {
        mEnglishTags = englishTags;
        mPersianTags = persianTags;
        panel=new JPanel(new GridBagLayout());
        toolbar = new JPanel(new FlowLayout());
        btnCancel = new JButton("Cancel");
        btnOk = new JButton("Ok");
        toolbar.add(btnCancel);
        toolbar.add(btnOk);
        //---

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.weightx = 1.0;
        panel.add(toolbar,constraints);

        tabbedPane = new JTabbedPane();
        mEnglishTab = new TaggerGeneratorTab();
        mPersianTab = new TaggerGeneratorTab();
        tabbedPane.addTab("English",mEnglishTab);
        tabbedPane.addTab("Persian", mPersianTab);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weighty = 1.0;
        panel.add(tabbedPane, constraints);
        //---
        btnOk.addActionListener(btnSaveActionListener);
        setContentPane(panel);

    }
    public void setListener(TaggerGeneratorListener listener)
    {
        this.listener = listener;
    }
    private ActionListener btnSaveActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Iterator<DatabaseManager.TokenTableRow> iterator = mEnglishTags.tokenTableRows.iterator();
            int index=-1;
            while (iterator.hasNext())
            {
                index++;
                DatabaseManager.TokenTableRow row = iterator.next();
                if(row.isReadyForGenerate())
                {
                    String engTemplate=mEnglishTab.getTemplate();
                    engTemplate = engTemplate.replace("$engword$",row.getWord().toLowerCase())
                               .replace("$word$",row.getWord());
                    engTemplate.concat("\r\n");

                    int meaningID=row.getMeaning();
                    DatabaseManager.TokenTableRow persianRow=getPersianTableRow(meaningID);
                    if(persianRow!=null) {
                        String perTemplate = mPersianTab.getTemplate();
                        perTemplate = perTemplate.replace("$engword$", row.getWord().toLowerCase())
                                .replace("$word$", persianRow.getWord());
                        perTemplate.concat("\r\n");
                        String old = mEnglishTab.getFileTextArea().getText();
                        mEnglishTab.getFileTextArea().insert(engTemplate, mEnglishTab.getFilePosition());
                        mPersianTab.getFileTextArea().insert(perTemplate, mPersianTab.getFilePosition());
                        //write to file
                        try {
                            File file = new File(mEnglishTab.getFilePath());
                            FileWriter fileWriter = new FileWriter(file);
                            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                            bufferedWriter.write(mEnglishTab.getFileTextArea().getText());
                            bufferedWriter.close();
                            fileWriter.close();
                            row.setIsGenerated(true);
                            mEnglishTags.mTableModel.setValueAt(true,index, 5);
                            //---
                            file = new File(mPersianTab.getFilePath());
                            FileWriter  perFileWriter = new FileWriter(file);
                            BufferedWriter  perBufferedWriter = new BufferedWriter(perFileWriter);
                            perBufferedWriter.write(mPersianTab.getFileTextArea().getText());
                            perBufferedWriter.close();
                            perFileWriter.close();
                            persianRow.setIsGenerated(true);
                            mPersianTags.mTableModel.setValueAt(true, getRowIndex(persianRow.getId()), 3);
                            //---

                        } catch (IOException e1) {
                            e1.printStackTrace();
                            row.setReadyForGenerate(false);
                        }
                        if (row.isGenerated() && !persianRow.isGenerated()) {
                            row.setReadyForGenerate(false);
                            File file = new File(mEnglishTab.getFilePath());
                            try {
                                FileWriter fileWriter = new FileWriter(file);
                                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                                bufferedWriter.write(mEnglishTab.getFileTextArea().getText());
                                bufferedWriter.close();
                                fileWriter.close();
                                row.setIsGenerated(false);
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        }
                    }
                }
            }
            if(listener!=null)
                listener.onGenerateComplete();
            dispose();

        }
    };

    private int getRowIndex(int id)
    {
        for (int i=0;i<mPersianTags.mTableModel.getRowCount();++i) {
            if(Integer.valueOf(mPersianTags.mTableModel.getValueAt(i,0).toString())==id)
                return i;
        }
        return -1;
    }
    private DatabaseManager.TokenTableRow getPersianTableRow (int id)
    {
        Iterator<DatabaseManager.TokenTableRow > iterator = mPersianTags.tokenTableRows.iterator();
        while (iterator.hasNext())
        {
            DatabaseManager.TokenTableRow row = iterator.next();
            if(row.getId() ==id)
                return row;
        }
        return null;
    }
    public void display()
    {
        pack();
        setModalityType(ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
    class TableRowCode
    {
        private String englishCode;
        private int englishIndex;
        private String persianCode;
        private int persianIndex;

        public TableRowCode(String englishCode, int englishIndex, String persianCode, int persianIndex) {
            this.englishCode = englishCode;
            this.englishIndex = englishIndex;
            this.persianCode = persianCode;
            this.persianIndex = persianIndex;
        }
    }
}
