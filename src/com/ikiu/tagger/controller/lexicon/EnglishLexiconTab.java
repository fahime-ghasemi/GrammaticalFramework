package com.ikiu.tagger.controller.lexicon;

import com.ikiu.tagger.model.DatabaseManager;

import java.io.File;
import java.util.Iterator;
import java.util.Vector;

/**
 * Created by fahime on 3/10/16.
 */
public class EnglishLexiconTab extends LexiconUpdaterTab {
    private String lexiconFilePath;
    String templateNoun = "$word$_N = regN \"$word$\" ;";
    String templateAdjective = "$word$_A = regADeg \"$word$\" ;";
    String templateV2 = "$word$_V2 = dirV2 (regV \"$word$\") ;";
    String templateN2 = "$word$_N2 = regN \"$word$\" ;";
    String templateV2S = "$word$_V2S = mkV2S (mkV \"$word$\" \"e.g. answered\") toP ;";
    String templateV2Q = "$word$_V2Q = mkV2Q (regV \"$word$\") noPrep ;";
    String templateV2V = "$word$_V2V = mkV2V (regDuplV \"$word$\") noPrep toP ;";
    String templateV2A = "$word$_V2A = mkV2A (regV \"$word$\") noPrep ;";
    String templateVA = "$word$_VA = mkVA (irregV \"$word$\" \"e.g. became\" \"e.g. become\") ;";
    String templateN3 = "$word$_N3 = mkN3 (regN \"$word$\") fromP toP ;";
    String templateA2V = "$word$_A2V = mkA2V (regA \"$word$\") forP ;";
    String templateVS = "$word$_VS = mkVS (regV \"$word$\") ;";
    String templateAV = "$word$_AV = mkAV (regA \"$word$\") ;";
    String templateRegV = "$word$_V = regV \"$word$\" ;";
    String templateIrregV = "$word$_V = irregV \"$word$\" \"e.g. came\" \"e.g. come\" ;";
    String templateV3 = "$word$_V3 = dirV3 (irregV \"$word$\" \"e.g. sold\" \"e.g. sold\") toP ;";
    String templateVQ = "$word$_VQ = mkVQ (mkV \"$word$\" \"e.g. wondered\") ;";
    String templateAdv = "$word$_Adv = mkAdv \"$word$\" ;";
    String templatePN = "$word$_PN = mkPN (mkN nonhuman (mkN \"$word$\")) ;";

    public EnglishLexiconTab(Vector<DatabaseManager.TokenTableRow> tokenTableRows) {
        super(tokenTableRows);
        lexiconFilePath = corePath + File.separatorChar + "english" + File.separatorChar + "LexiconEng.gf";
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
                case "irregular verb": {
                    template = templateIrregV.replace("$word$", tokenTableRow.getWord().toLowerCase());
                    break;
                }
                case "pn": {
                    //@todo change first char to uppercase
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
