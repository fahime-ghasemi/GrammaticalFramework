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
    String templateV2S = "$word$_V2S : V2S ;";
    String templateV2Q = "$word$_V2Q : V2Q ;";
    String templateV2V = "$word$_V2V : V2V ;";
    String templateV2A = "$word$_V2A : V2A ;";
    String templateVA = "$word$_VA : VA ;";
    String templateN3 = "$word$_N3 : N3 ;";
    String templateA2V = "$word$_A2V : A2V ;";
    String templateVS = "$word$_VS : VS ;";
    String templateAV = "$word$_AV : AV ;";
    String templateRegV = "$word$_V : V ;";
    String templateIrregV = "$word$_V : V ;";
    String templatePN = "$word$_PN : PN ;";
    String templateV3 = "$word$_V3 : V3 ;";
    String templateVQ = "$word$_VQ : VQ ;";
    String templateAdv = "$word$_Adv : Adv ;";

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
                case "v2s": {
                    template = templateV2S.replace("$word$", tokenTableRow.getWord().toLowerCase());
                    break;
                }
                case "v2q": {
                    template = templateV2Q.replace("$word$", tokenTableRow.getWord().toLowerCase());
                    break;
                }
                case "v2v": {
                    template = templateV2V.replace("$word$", tokenTableRow.getWord().toLowerCase());
                    break;
                }
                case "v2a": {
                    template = templateV2A.replace("$word$", tokenTableRow.getWord().toLowerCase());
                    break;
                }
                case "va": {
                    template = templateVA.replace("$word$", tokenTableRow.getWord().toLowerCase());
                    break;
                }
                case "n3": {
                    template = templateN3.replace("$word$", tokenTableRow.getWord().toLowerCase());
                    break;
                }
                case "a2v": {
                    template = templateA2V.replace("$word$", tokenTableRow.getWord().toLowerCase());
                    break;
                }
                case "vs": {
                    template = templateVS.replace("$word$", tokenTableRow.getWord().toLowerCase());
                    break;
                }
                case "av": {
                    template = templateAV.replace("$word$", tokenTableRow.getWord().toLowerCase());
                    break;
                }
                case "regular verb": {
                    template = templateRegV.replace("$word$", tokenTableRow.getWord().toLowerCase());
                    break;
                }
                case "Irregular verb": {
                    template = templateIrregV.replace("$word$", tokenTableRow.getWord().toLowerCase());
                    break;
                }
                case "pn": {
                    template = templatePN.replace("$word$", tokenTableRow.getWord().toLowerCase());
                    break;
                }
                case "v3": {
                    template = templateV3.replace("$word$", tokenTableRow.getWord().toLowerCase());
                    break;
                }
                case "vq": {
                    template = templateVQ.replace("$word$", tokenTableRow.getWord().toLowerCase());
                    break;
                }
                case "adv": {
                    template = templateAdv.replace("$word$", tokenTableRow.getWord().toLowerCase());
                    break;
                }

            }
            linList.add(template);
            Object[] newRow = {tokenTableRow.getWord().toLowerCase(), template};
            mTableModel.addRow(newRow);
        }
    }
}
