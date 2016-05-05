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
    String templateV2 = "$engword$_V2 = mkV2 (compoundV \"$perword$\" (mkV \"کردن\" \"کن\")) \"را\"; ";
    String templateN2 = "$engword$_N2 = (mkN01 \"$perword$\" animate) ** {c=\"\"}; ";
    String templateV2S = "$engword$_V2S = mkV2  (compoundV \"جواب\" (mkV \"دادن\" \"ده\")) \"به\" False; ";
    String templateV2Q = "$engword$_V2Q = mkV2 (mkV_1 \"پرسیدن\") \"از\" False; ";
    String templateV2V = "$engword$_V2V = mkV2V (compoundV \"خواهش\" (mkV \"کردن\" \"کن\")) \"از\" \"\" False; ";
    String templateV2A = "$engword$_V2A = mkV2 (compoundV \"رنگ\" (mkV \"کردن\" \"کن\")) \"را\" ; ";
    String templateVA = "$engword$_VA = mkV \"شدن\" \"شو\"; ";
    String templateN3 = "$engword$_N3 = (mkN \"فاصله\" \"فواصل\" inanimate ) ** {c2=\"از\" ; c3 = \"تا\"}; ";
    String templateA2V = "$engword$_A2V = mkA \"آسان\" \"\" ; ";
    String templateVS = "$engword$_A2V = mkV_1 \"ترسیدن\";  ";
    String templateAV = "$engword$_AV = mkAV  \"$perword$\" ;  ";
    String templatePN = "$engword$_PN = mkPN \"$perword$\" inanimate;";
    String templateV3 = "$engword$_V3 = mkV3 (mkV_1 \"فرستادن\") \"را\" \"برای\"; ";
    String templateVQ = "$engword$_VQ = (mkV_1 \"دانستن\") ; ";
    String templateAdv = "$engword$_Adv =  ss \"$perword$\" ; ";
    String templateRegV = "$engword$_V =  mkV \"آمدن\" \"آی\" ; ";

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
                        break;
                    }
                    case "n2": {
                        template = templateN2.replace("$engword$", tokenTableRow.getWord().toLowerCase()).replace("$perword$", persianRow.getWord());
                        break;
                    }
                    case "v2": {
                        template = templateV2.replace("$engword$", tokenTableRow.getWord().toLowerCase()).replace("$perword$", persianRow.getWord());
                        break;
                    }
                    case "v2s": {
                        template = templateV2S.replace("$engword$", tokenTableRow.getWord().toLowerCase()).replace("$perword$", persianRow.getWord());
                        break;
                    }
                    case "v2q": {
                        template = templateV2Q.replace("$engword$", tokenTableRow.getWord().toLowerCase()).replace("$perword$", persianRow.getWord());
                        break;
                    }
                    case "v2v": {
                        template = templateV2V.replace("$engword$", tokenTableRow.getWord().toLowerCase()).replace("$perword$", persianRow.getWord());
                        break;
                    }
                    case "v2a": {
                        template = templateV2A.replace("$engword$", tokenTableRow.getWord().toLowerCase()).replace("$perword$", persianRow.getWord());
                        break;
                    }
                    case "va": {
                        template = templateVA.replace("$engword$", tokenTableRow.getWord().toLowerCase()).replace("$perword$", persianRow.getWord());
                        break;
                    }
                    case "n3": {
                        template = templateN3.replace("$engword$", tokenTableRow.getWord().toLowerCase()).replace("$perword$", persianRow.getWord());
                        break;
                    }
                    case "a2v": {
                        template = templateA2V.replace("$engword$", tokenTableRow.getWord().toLowerCase()).replace("$perword$", persianRow.getWord());
                        break;
                    }
                    case "vs": {
                        template = templateVS.replace("$engword$", tokenTableRow.getWord().toLowerCase()).replace("$perword$", persianRow.getWord());
                        break;
                    }
                    case "av": {
                        template = templateAV.replace("$engword$", tokenTableRow.getWord().toLowerCase()).replace("$perword$", persianRow.getWord());
                        break;
                    }
                    case "regular verb": {
                        template = templateRegV.replace("$engword$", tokenTableRow.getWord().toLowerCase()).replace("$perword$", persianRow.getWord());
                        break;
                    }
                    case "pn": {
                        template = templatePN.replace("$engword$", tokenTableRow.getWord().toLowerCase()).replace("$perword$", persianRow.getWord());
                        break;
                    }
                    case "v3": {
                        template = templateV3.replace("$engword$", tokenTableRow.getWord().toLowerCase()).replace("$perword$", persianRow.getWord());
                        break;
                    }
                    case "vq": {
                        template = templateVQ.replace("$engword$", tokenTableRow.getWord().toLowerCase()).replace("$perword$", persianRow.getWord());
                        break;
                    }
                    case "adv": {
                        template = templateAdv.replace("$engword$", tokenTableRow.getWord().toLowerCase()).replace("$perword$", persianRow.getWord());
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
