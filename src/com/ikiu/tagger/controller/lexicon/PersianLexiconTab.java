package com.ikiu.tagger.controller.lexicon;

import com.ikiu.tagger.model.DatabaseManager;

import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.util.Iterator;
import java.util.Vector;

/**
 * Created by fahime on 3/10/16.
 */
public class PersianLexiconTab extends LexiconUpdaterTab {
    protected Vector<DatabaseManager.TokenTableRow> persianTableRows;
    private String lexiconFilePath;
    String templateNoun = "$engword$_N = mkN01 \"$perword$\" inanimate ;";
    String templateAdjective = "$engword$_A = mkA \"$perword$\" ;";
    String templateV2 = "$engword$_V2 = mkV2 (compoundV \"$perword$\" (mkV \"کردن\" \"کن\")) \"را\";";
    String templateN2 = "$engword$_N2 = (mkN01 \"$perword$\" animate) ** {c=\"\"}; ";

    public PersianLexiconTab(Vector<DatabaseManager.TokenTableRow> tokenTableRows, Vector<DatabaseManager.TokenTableRow> persianTableRows) {
        super(tokenTableRows);
        this.persianTableRows = persianTableRows;
        lexiconFilePath = corePath + File.separatorChar + "persian" + File.separatorChar + "LexiconPes.gf";
        loadTokens();
    }

    @Override
    public void setGeneratedForAllRows(DefaultTableModel mTableModel) {
        for (int i = 0; i < persianTableRows.size(); ++i) {
            if (persianTableRows.get(i).isReadyForGenerate()) {
                persianTableRows.get(i).setIsGenerated(true);
                try {
                    mTableModel.setValueAt(true, i, 3);
                } catch (Exception ignore) {

                }
            }
        }
    }

    public String getLexiconFilePath() {
        return lexiconFilePath;
    }

    public void loadTokens() {
        if (tokenTableRows == null)
            return;
        Iterator<DatabaseManager.TokenTableRow> iterator = tokenTableRows.iterator();
        while (iterator.hasNext()) {
            String template = "";
            DatabaseManager.TokenTableRow tokenTableRow = iterator.next();
            if (!tokenTableRow.isReadyForGenerate()) continue;
            int meaningID = tokenTableRow.getMeaning();
            DatabaseManager.TokenTableRow persianRow = getPersianTableRow(meaningID);
            persianRow.setReadyForGenerate(true);
            if (persianRow != null) {
                switch (tokenTableRow.getType()) {
                    case "noun": {
                        template = templateNoun.replace("$engword$", tokenTableRow.getWord().toLowerCase()).replace("$perword$", persianRow.getWord());
                        break;
                    }
                    case "adjective": {
                        template = templateAdjective.replace("$engword$", tokenTableRow.getWord().toLowerCase()).replace("$perword$", persianRow.getWord());
                        ;
                        break;
                    }
                    case "n2": {
                        template = templateN2.replace("$engword$", tokenTableRow.getWord().toLowerCase()).replace("$perword$", persianRow.getWord());
                        ;
                        break;
                    }
                    case "v2": {
                        template = templateV2.replace("$engword$", tokenTableRow.getWord().toLowerCase()).replace("$perword$", persianRow.getWord());
                        ;
                        break;
                    }
                }
                linList.add(template);
                Object[] newRow = {persianRow.getWord().toLowerCase(), template};
                mTableModel.addRow(newRow);
            }
        }
    }

    private DatabaseManager.TokenTableRow getPersianTableRow(int id) {
        Iterator<DatabaseManager.TokenTableRow> iterator = persianTableRows.iterator();
        while (iterator.hasNext()) {
            DatabaseManager.TokenTableRow row = iterator.next();
            if (row.getId() == id)
                return row;
        }
        return null;
    }
}
