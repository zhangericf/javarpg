package com.zhange.rpg;

import javax.swing.*;

/**
 * Parametre de la fenetre
 */

public class Windows extends JFrame {
    public Windows() {
        setTitle("Mon RPG");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new GamePanel(1000, 800));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }
}

