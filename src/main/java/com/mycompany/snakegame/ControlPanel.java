/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.snakegame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

/**
 *
 * @author FPTSHOP
 */
public class ControlPanel extends JPanel {

    public ControlPanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        initComponents();
        setUp();
    }

    private void setUp() {
        setPreferredSize(dimension);
        setBackground(Color.DARK_GRAY);
        FlowLayout layout = new FlowLayout();
        layout.setHgap(5);
        setLayout(layout);

        add("newGame", newGameBtn);
        add("pause", pauseBtn);
        add("difficulty", difficultyComboBox);
    }

    private void initComponents() {
        newGameBtn = new JButton();
        pauseBtn = new JButton();
        difficultyComboBox = new JComboBox<>();

        pauseBtn.setText("Pause");
        pauseBtn.setFont(new Font("Sans serif", Font.BOLD, 14));
        pauseBtn.setForeground(Color.WHITE);
        pauseBtn.setFocusable(false);
        pauseBtn.setPreferredSize(new Dimension(GamePanel.dimension.width / 3 - 5, 30));
        pauseBtn.setBackground(Color.GRAY);
        pauseBtn.addActionListener((java.awt.event.ActionEvent evt) -> {
            gamePanel.setPause(!gamePanel.getPause());
        });

        newGameBtn.setText("New Game");
        newGameBtn.setFont(new Font("Sans serif", Font.BOLD, 14));
        newGameBtn.setForeground(Color.WHITE);
        newGameBtn.setFocusable(false);
        newGameBtn.setPreferredSize(new Dimension(GamePanel.dimension.width / 3 - 5, 30));
        newGameBtn.setBackground(Color.GRAY);
        newGameBtn.addActionListener((java.awt.event.ActionEvent evt) -> {
            gamePanel.newGame();
        });

        difficultyComboBox.setFont(new Font("Sans serif", Font.BOLD, 14));
        difficultyComboBox.setForeground(Color.WHITE);
        difficultyComboBox.addItem("Easy");
        difficultyComboBox.addItem("Normal");
        difficultyComboBox.addItem("Hard");
        difficultyComboBox.setFocusable(false);
        difficultyComboBox.setPreferredSize(new Dimension(GamePanel.dimension.width / 3 - 5, 30));
        difficultyComboBox.setBackground(Color.GRAY);
        difficultyComboBox.addItemListener((java.awt.event.ItemEvent evt) -> {
            switch ((String) difficultyComboBox.getSelectedItem()) {
                case "Easy":
                    gamePanel.setSnakeVelicity(GamePanel.EASY);
                    break;
                case "Normal":
                    gamePanel.setSnakeVelicity(GamePanel.NORMAL);
                    break;
                case "Hard":
                    gamePanel.setSnakeVelicity(GamePanel.HARD);
                    break;
            }
        });
    }

    private final GamePanel gamePanel;
    private JComboBox<String> difficultyComboBox;
    private JButton newGameBtn;
    private JButton pauseBtn;
    Dimension dimension = new Dimension(GamePanel.dimension.width, 40);
}
