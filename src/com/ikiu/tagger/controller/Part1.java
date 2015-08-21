/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Emotion
 */
class part1 implements ActionListener {
    JMenuBar menu1;
    private JMenuBar menu2;
    private JMenu File;
    private JMenuItem New;
    private JMenuItem Setting;
    private JMenuItem GFproject;
    //private JFrame frame=new JFrame();

    public part1(JFrame frame){

        File= new JMenu("File");
        New = new JMenuItem("New");
        Setting=new JMenuItem("Setting");
        File.add(New);
        New.addActionListener(this);
        File.add(Setting);
        Setting.addActionListener(this);
        // GFproject=new JMenuItem("GFproject");
        //  Setting.add(GFproject);
        //  GFproject.addActionListener(this);
        menu1=new JMenuBar();
        menu2=new JMenuBar();
        menu1.add(File);
        frame.setJMenuBar(menu1);

    }
    public JMenuBar getComponent()
    {
        return menu1;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
