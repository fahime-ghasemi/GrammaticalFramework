package com.ikiu.tagger.controller.lexicon;

import com.ikiu.tagger.controller.EnglishTags;
import com.ikiu.tagger.controller.PersianTags;
import com.ikiu.tagger.model.DatabaseManager;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * Created by fahime on 10/9/15.
 */
public class LexiconUpdater extends JDialog {
    protected JTabbedPane tabbedPane;
    private JPanel panel;
    private JPanel toolbar;
    private JButton btnOk;
    private JButton btnCancel;
    private LexiconUpdaterTab mEnglishTab;
    private LexiconUpdaterTab mPersianTab;
    private LexiconUpdaterTab abstractTab;
    private EnglishTags mEnglishTags;
    private PersianTags mPersianTags;

    public interface LexiconUpdaterListener {
        public void onGenerateComplete();
    }

    private LexiconUpdaterListener listener;

    public LexiconUpdater(EnglishTags englishTags, PersianTags persianTags) throws HeadlessException {
        mEnglishTags = englishTags;
        mPersianTags = persianTags;
        panel = new JPanel(new GridBagLayout());
        toolbar = new JPanel(new FlowLayout());
        btnCancel = new JButton("Cancel");
        btnOk = new JButton("Ok");
        toolbar.add(btnOk);
        toolbar.add(btnCancel);
        //---

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.weightx = 1.0;
        panel.add(toolbar, constraints);

        tabbedPane = new JTabbedPane();
        mEnglishTab = new EnglishLexiconTab(mEnglishTags.getTokenTableRows());
        mPersianTab = new PersianLexiconTab(mEnglishTags.getTokenTableRows(), mPersianTags.getTokenTableRows());
        abstractTab = new AbstractLexiconTab(mEnglishTags.getTokenTableRows());
        tabbedPane.addTab("Abstract", abstractTab);
        tabbedPane.addTab("English", mEnglishTab);
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

    public void setListener(LexiconUpdaterListener listener) {
        this.listener = listener;
    }

    private ActionListener btnSaveActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateLexicon(((AbstractLexiconTab) abstractTab).getLexiconFilePath(), abstractTab.getLinListAsString());

            if (updateLexicon(((EnglishLexiconTab) mEnglishTab).getLexiconFilePath(), mEnglishTab.getLinListAsString())) {
                mEnglishTab.setGeneratedForAllRows(mEnglishTags.mTableModel);
            }
            if (updateLexicon(((PersianLexiconTab) mPersianTab).getLexiconFilePath(), mPersianTab.getLinListAsString())) {
                mPersianTab.setGeneratedForAllRows(mPersianTags.mTableModel);
            }

            if (listener != null)
                listener.onGenerateComplete();
            dispose();

        }
    };

    private Boolean updateLexicon(String filePath, String linList) {
        StringBuilder fileContent = new StringBuilder();
        int position;
        String line = null;

        try {
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(new File(filePath)), "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            while ((line = bufferedReader.readLine()) != null) {
                fileContent.append(line).append("\r\n");
            }
            inputStreamReader.close();
            bufferedReader.close();
            position = fileContent.indexOf("-- IDE Generated Section");
            position += 24;
            fileContent.insert(position, "\r\n" + linList);

            File absFile = new File(filePath);
            FileWriter absFileWriter = new FileWriter(absFile);
            BufferedWriter absBufferedWriter = new BufferedWriter(absFileWriter);

            absBufferedWriter.write(fileContent.toString());
            absBufferedWriter.close();
            absFileWriter.close();

            //---

//            row.setIsGenerated(true);
//            mEnglishTags.mTableModel.setValueAt(true, index, 5);
//            ---
//
//            persianRow.setIsGenerated(true);
//            mPersianTags.mTableModel.setValueAt(true, getRowIndex(persianRow.getId()), 3);
            //---

        } catch (IOException e1) {
            e1.printStackTrace();
//            row.setReadyForGenerate(false);
            return false;
        }
        return true;
    }

    private int getRowIndex(int id) {
//        for (int i = 0; i < mPersianTags.mTableModel.getRowCount(); ++i) {
//            if (Integer.valueOf(mPersianTags.mTableModel.getValueAt(i, 0).toString()) == id)
//                return i;
//        }
        return -1;
    }

    private DatabaseManager.TokenTableRow getPersianTableRow(int id) {
//        Iterator<DatabaseManager.TokenTableRow> iterator = mPersianTags.tokenTableRows.iterator();
//        while (iterator.hasNext()) {
//            DatabaseManager.TokenTableRow row = iterator.next();
//            if (row.getId() == id)
//                return row;
//        }
        return null;
    }

    public void display() {
        pack();
        setModalityType(ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    class TableRowCode {
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
