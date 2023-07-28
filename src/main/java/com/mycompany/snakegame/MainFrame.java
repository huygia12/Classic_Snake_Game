/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.snakegame;

import java.awt.BorderLayout;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author FPTSHOP
 */
public class MainFrame extends JFrame {

    public MainFrame() {
        setUp();
    }

    private void setUp(){
        setLayout(new BorderLayout());
        GamePanel gamePanel = new GamePanel();
        ControlPanel ctrlPanel = new ControlPanel(gamePanel);
        add(gamePanel, BorderLayout.NORTH);
        add(ctrlPanel, BorderLayout.SOUTH);
        pack();
        
        setIconImage(new ImageIcon(GAME_ICON).getImage());
        setTitle("Snake Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    final static String SEPARATOR = File.separator;
    final static String IMAGE_FOLDER = System.getProperty("user.dir") 
            + SEPARATOR + "src" + SEPARATOR + "main" + SEPARATOR + "image"+ SEPARATOR;
    final static String GAME_ICON = IMAGE_FOLDER + "gameIcon.png";
}
