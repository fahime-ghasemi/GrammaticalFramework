package com.ikiu.tagger.controller.lexicon;

import com.ikiu.tagger.model.DatabaseManager;

import java.io.File;
import java.util.Iterator;
import java.util.Vector;

/**
 * Created by fahime on 3/10/16.
 */
public class AbstractLexiconTab extends LexiconUpdaterTab {
    private String lexiconFilePath;
    String templateNoun = "$word$_N : N ;";
    String templateAdjective = "$word$_A : A ;";
    String templateV2 = "$word$_V2 : V2 ;";
    String templateN2 = "$word$_N2 : N2 ;";

    public AbstractLexiconTab(Vector<DatabaseManager.TokenTableRow> tokenTableRows) {
        super(tokenTableRows);
        lexiconFilePath = corePath + File.separatorChar + "abstract" + File.separatorChar + "Lexicon.gf";
        loadTokens();
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
            switch (tokenTableRow.getType()) {
                case "noun": {
                    template = templateNoun.replace("$word$", tokenTableRow.getWord().toLowerCase());
                    break;
                }
                case "adjective": {
                    template = templateAdjective.replace("$word$", tokenTableRow.getWord().toLowerCase());
                    break;
                }
                case "n2": {
                    template = templateN2.replace("$word$", tokenTableRow.getWord().toLowerCase());
                    break;
                }
                case "v2": {
                    template = templateV2.replace("$word$", tokenTableRow.getWord().toLowerCase());
                    break;
                }
            }
            linList.add(template);
            Object[] newRow = {tokenTableRow.getWord().toLowerCase(), template};
            mTableModel.addRow(newRow);
        }
    }
}
