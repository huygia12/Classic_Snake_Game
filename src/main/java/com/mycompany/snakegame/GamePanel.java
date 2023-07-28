/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.snakegame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author FPTSHOP
 */
public class GamePanel extends JPanel implements ActionListener {

    public GamePanel() {
        newGame();
        setUp();
    }

    private void setUp() {
        setPreferredSize(dimension);
        setBackground(Color.DARK_GRAY);
        setFocusable(true);
        addKeyListenerEvent();
    }

    private void addKeyListenerEvent() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        if (direction != 'D') {
                            SNAKE_HEAD = SNAKE_HEAD_GO_UP;
                            direction = 'U';
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if (direction != 'U') {
                            SNAKE_HEAD = SNAKE_HEAD_GO_DOWN;
                            direction = 'D';
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (direction != 'L') {
                            SNAKE_HEAD = SNAKE_HEAD_GO_RIGHT;
                            direction = 'R';
                        }
                        break;
                    case KeyEvent.VK_LEFT:
                        if (direction != 'R') {
                            SNAKE_HEAD = SNAKE_HEAD_GO_LEFT;
                            direction = 'L';
                        }
                        break;
                }
            }
        });
    }

    private void setFoodCoordinates() {
        foodX = random.nextInt(dimension.width / BLOCK_SIZE) * BLOCK_SIZE;
        foodY = random.nextInt(dimension.height / BLOCK_SIZE) * BLOCK_SIZE;

        for (int i = 0; i < snakeLength; i++) {
            if ((SNAKE_X[i] == foodX) && (SNAKE_Y[i] == foodY)) {
                setFoodCoordinates();
            }
        }
    }

    private void snakeMove() {
        for (int i = snakeLength - 1; i > 0; i--) {
            SNAKE_X[i] = SNAKE_X[i - 1];
            SNAKE_Y[i] = SNAKE_Y[i - 1];
        }

        switch (direction) {
            case 'U':
                SNAKE_Y[0] -= BLOCK_SIZE;
                break;
            case 'D':
                SNAKE_Y[0] += BLOCK_SIZE;
                break;
            case 'L':
                SNAKE_X[0] -= BLOCK_SIZE;
                break;
            case 'R':
                SNAKE_X[0] += BLOCK_SIZE;
                break;
        }
    }

    private void draw(Graphics gp) {
        // draw map
        int count = 0;
        for (int i = 0; i < dimension.height; i += BLOCK_SIZE) {
            for (int j = 0; j < dimension.width; j += BLOCK_SIZE) {
                if (count % 2 == 0) {
                    gp.setColor(ODD_SQUARE_COLOR);
                    gp.fillRect(j, i, BLOCK_SIZE, BLOCK_SIZE);
                } else {
                    gp.setColor(EVEN_SQUARE_COLOR);
                    gp.fillRect(j, i, BLOCK_SIZE, BLOCK_SIZE);
                }
                count++;
            }
            count++;
        }

        // draw food
        gp.drawImage(APPLE_IMAGE,
                foodX, foodY, BLOCK_SIZE, BLOCK_SIZE, null);

        // draw snake
        gp.drawImage(SNAKE_HEAD,
                SNAKE_X[0], SNAKE_Y[0], BLOCK_SIZE, BLOCK_SIZE, null);
        for (int i = snakeLength - 1; i > 0; i--) {
            gp.drawImage(SNAKE_BODY_IMAGE,
                    SNAKE_X[i], SNAKE_Y[i], BLOCK_SIZE, BLOCK_SIZE, null);
        }

        if (!running) {
            gameOver(gp);
        } else {
            // display scores
            gp.setColor(SCORE_COLOR);
            gp.setFont(new Font("Sans serif", Font.BOLD, BLOCK_SIZE));
            String scoreToStr = "Scores : " + score;
            FontMetrics metrics = getFontMetrics(gp.getFont());
            gp.drawString(scoreToStr,
                    (dimension.width - metrics.stringWidth(scoreToStr)) / 2,
                    metrics.getFont().getSize());
        }
    }

    public void newGame() {
        initVariables();
        setFoodCoordinates();
        if (timer != null) {
            timer.stop();
        }
        timer = new Timer(snakeVelocity, this);
        timer.start();
    }

    private void gameOver(Graphics gp) {
        gp.setColor(GAME_OVER_COLOR);
        gp.setFont(new Font("Sans serif", Font.BOLD, 50));
        FontMetrics metrics1 = getFontMetrics(gp.getFont());
        gp.drawString(GAME_OVER_STRING, (dimension.width - metrics1.stringWidth(GAME_OVER_STRING)) / 2,
                (dimension.height - metrics1.getFont().getSize()) / 2);

        gp.setColor(SCORE_COLOR);
        gp.setFont(new Font("Sans serif", Font.BOLD, 25));
        String scoreToStr = "Scores : " + score;
        FontMetrics metrics2 = getFontMetrics(gp.getFont());
        gp.drawString(scoreToStr, (dimension.width - metrics2.stringWidth(scoreToStr)) / 2,
                ((dimension.height - metrics1.getFont().getSize()) / 2) + metrics2.getFont().getSize());

        timer.stop();
    }

    private void checkHit() {
        for (int i = 1; i < snakeLength; i++) {
            if ((SNAKE_X[0] == SNAKE_X[i]) && (SNAKE_Y[0] == SNAKE_Y[i])) {
                running = false;
            }
        }
        if (SNAKE_X[0] >= dimension.width || SNAKE_X[0] < 0 || SNAKE_Y[0] >= dimension.height || SNAKE_Y[0] < 0) {
            running = false;
        }
    }

    private void eatFood() {
        if (SNAKE_X[0] == foodX && SNAKE_Y[0] == foodY) {
            snakeLength++;
            score++;
            snakeMove();
            setFoodCoordinates();
        }
    }

    private void initVariables() {
        SNAKE_X = new int[NUMBER_OF_BLOCKS];
        SNAKE_Y = new int[NUMBER_OF_BLOCKS];
        direction = 'R';
        snakeLength = 5;
        score = 0;
        snakeLength = 5;
        SNAKE_HEAD = SNAKE_HEAD_GO_RIGHT;
        running = true;
        pause = true;
    }

    public void setSnakeVelicity(int v) {
        this.snakeVelocity = v;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public boolean getPause() {
        return pause;
    }

    public boolean getRunning() {
        return running;
    }

    @Override
    protected void paintComponent(Graphics gp) {
        super.paintComponent(gp); // paint background
        draw(gp);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (pause) {
            if (running) {
                snakeMove();
                checkHit();
                eatFood();
            }
            repaint();
        }
    }

    private final Color ODD_SQUARE_COLOR = new Color(170, 215, 81);
    private final Color EVEN_SQUARE_COLOR = new Color(162, 209, 73);
    private final Color GAME_OVER_COLOR = Color.DARK_GRAY;
    private final Color SCORE_COLOR = Color.WHITE;
    private final String GAME_OVER_STRING = "GAME OVER!";
    private final Image APPLE_IMAGE = new ImageIcon(MainFrame.IMAGE_FOLDER + "apple.png").getImage();
    private final Image SNAKE_BODY_IMAGE = new ImageIcon(MainFrame.IMAGE_FOLDER + "snakeBody.png").getImage();
    private final Image SNAKE_HEAD_GO_DOWN = new ImageIcon(MainFrame.IMAGE_FOLDER + "snakeHeadGoDown.jpg").getImage();
    private final Image SNAKE_HEAD_GO_UP = new ImageIcon(MainFrame.IMAGE_FOLDER + "snakeHeadGoUp.png").getImage();
    private final Image SNAKE_HEAD_GO_RIGHT = new ImageIcon(MainFrame.IMAGE_FOLDER + "snakeHeadGoRight.png").getImage();
    private final Image SNAKE_HEAD_GO_LEFT = new ImageIcon(MainFrame.IMAGE_FOLDER + "snakeHeadGoLeft.jpg").getImage();
    private final int BLOCK_SIZE = 25;
    static final int EASY = 120;
    static final int NORMAL = 85;
    static final int HARD = 50;
    static Dimension dimension = new Dimension(500, 500);
    private final int NUMBER_OF_BLOCKS = (dimension.height * dimension.width) / (BLOCK_SIZE * BLOCK_SIZE);
    private int[] SNAKE_X;
    private int[] SNAKE_Y;
    Random random = new Random();
    private Timer timer;
    private char direction;
    private boolean running;
    private boolean pause;
    private int snakeVelocity = EASY;
    private int foodX;
    private int foodY;
    private int score;
    private int snakeLength;
    private Image SNAKE_HEAD;
}
