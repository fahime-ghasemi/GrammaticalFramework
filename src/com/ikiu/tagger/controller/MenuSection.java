/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ikiu.tagger.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * @author Emotion
 */
class MenuSection extends JMenuBar implements ActionListener {
    private JMenu File;
    private JMenuItem Setting;
    private JMenuItem save;
    private JMenuItem translator;
    private Context context;

    public MenuSection(Context context) {

        this.context = context;
        File = new JMenu("File");
        Setting = new JMenuItem("Setting");
        save = new JMenuItem("Save");
        if (context.getCurrentPanel() != null && context.getCurrentPanel() instanceof MainContent && !context.getCurrentPanel().hasOpenedTab())
            save.setEnabled(false);
        if (context.getCurrentPanel() != null && context.getCurrentPanel() instanceof MainContent)
            context.getCurrentPanel().setListener(new MainContent.MainContentOpenTabListener() {
                @Override
                public void onTabAdd() {
                    save.setEnabled(true);
                }

                @Override
                public void onTabRemove() {
                    save.setEnabled(false);
                }
            });
        translator = new JMenuItem("Open translator");
        File.add(save);
        File.add(Setting);
        File.add(translator);
        translator.addActionListener(this);
        Setting.addActionListener(this);
        save.addActionListener(this);
        //----
        add(File);
        //----
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        if (e.getSource() == Setting) {
            SettingDialog settingDialog = new SettingDialog(true);
            settingDialog.addCancelButton();
            settingDialog.addSaveButton();
            settingDialog.show();
        } else if (e.getSource() == save) {
            context.getCurrentPanel().saveCurrentTabChanges();
        } else if (e.getSource() == translator) {
            context.getMainPanel().openTranslator();
        }
    }

}
