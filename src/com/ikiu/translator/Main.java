package com.ikiu.translator;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public static void main(String[] args) {
        Main appWindow = new Main();

        appWindow.setLayout(new BorderLayout());
        Context contentPane = new Context();
        appWindow.setContentPane(contentPane);

        appWindow.pack();
        appWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        appWindow.setVisible(true);
        appWindow.setSize(600, 700);
        appWindow.setExtendedState(MAXIMIZED_BOTH);
    }
}
