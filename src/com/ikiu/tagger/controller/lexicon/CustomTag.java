package com.ikiu.tagger.controller.lexicon;

import com.ikiu.tagger.controller.EnglishTags;
import com.ikiu.tagger.controller.PersianTags;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by fahime on 4/29/16.
 */
public class CustomTag extends JDialog {
    JTextField txtWord;
    JButton btnOk;
    JLabel labelType;
    JLabel labelWord;
    JButton btnCancel;
    JComboBox<String> comboType;
    private CustomListener listener;
    ActionListener okActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            if (listener != null)
                listener.onOkListener(comboType.getSelectedItem().toString().toLowerCase(),txtWord.getText());
        }
    };
    ActionListener cancelActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
        }
    };

    public CustomTag(String word) {
        setResizable(false);
        txtWord = new JTextField(50);
        txtWord.setText(word);
        labelWord = new JLabel("Word :");
        labelType = new JLabel("Type :");
        btnOk = new JButton("Ok");
        btnOk.addActionListener(okActionListener);
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(cancelActionListener);

        comboType = new JComboBox<>();
        comboType.addItem("Noun");
        comboType.addItem("Adjective");
        comboType.addItem("N2");
        comboType.addItem("V2");
        comboType.addItem("V2S");
        comboType.addItem("V2Q");
        comboType.addItem("V2V");
        comboType.addItem("V2A");
        comboType.addItem("VA");
        comboType.addItem("N3");
        comboType.addItem("A2V");
        comboType.addItem("VS");
        comboType.addItem("AV");
        comboType.addItem("Regular Verb");
        comboType.addItem("Irregular Verb");
        comboType.addItem("PN");
        comboType.addItem("V3");
        comboType.addItem("VQ");
        comboType.addItem("Adv");
        setLayout(new FlowLayout());
        add(labelType);
        add(comboType);
        add(labelWord);
        add(txtWord);
        add(btnOk);
        add(btnCancel);
        pack();
//            settingDialog.setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(900, 150);

    }

    public void display(CustomListener listener) {
        this.listener = listener;
        setVisible(true);
    }

    public interface CustomListener {
        public void onOkListener(String type, String word);
    }

}
