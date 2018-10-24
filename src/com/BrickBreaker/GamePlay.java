package com.BrickBreaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePlay extends JPanel implements KeyListener, ActionListener {

    private boolean play = false;
    private int score = 0;

    private int totalBricks = 21;



    private Timer time;
    private int delay = 8;


    private int playerX = 310;
    private int ballPositionX = 120;
    private int ballPositionY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;

    private MapGenerator map;

        public GamePlay(){
            map = new MapGenerator(3,7);
            addKeyListener(this);
            setFocusable(true);
            setFocusTraversalKeysEnabled(false);
            time = new Timer(delay,this);
            time.start();
        }

        public void paint(Graphics g){
            // background
            g.setColor(Color.BLACK);
            g.fillRect(1,1,692,592);

            // drawing map

            map.draw((Graphics2D)g);



            // borders
            g.setColor(Color.yellow);
            g.fillRect(0,0,692,3);
            g.fillRect(0,0,3,592);



            // scores
            g.setColor(Color.white);
            g.setFont(new Font("serif",Font.BOLD,25));
            g.drawString(""+score,590,30 );


            // paddle
            g.setColor(Color.green);
            g.fillRect(playerX,550,100,8);



            // the ball
            g.setColor(Color.yellow);
            g.fillOval(ballPositionX,ballPositionY,20,20);

                if (totalBricks <= 0){
                    play = false;
                    ballXdir = 0;
                    ballYdir = 0;
                    g.setColor(Color.RED);
                    g.setFont(new Font("serif", Font.BOLD,30));
                    g.drawString("You won:",260,300);

                    g.setFont(new Font("serif",Font.BOLD,20));
                    g.drawString("Press Enter to Restart",230,350);
                }


            if (ballPositionY > 570){
                play = false;
                ballXdir = 0;
                ballYdir = 0;
                g.setColor(Color.RED);
                g.setFont(new Font("serif", Font.BOLD,30));
                g.drawString("Game Over, Scores: ",190,300);

                g.setFont(new Font("serif",Font.BOLD,20));
                g.drawString("Press Enter to Restart",230,350);
            }


            g.dispose();
        }
    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        time.start();

        if (play){
            // intersection with the paddle

                if (new Rectangle(ballPositionX,ballPositionY,20,20).intersects(new Rectangle(playerX,550,100,8))){
                    ballYdir = -ballYdir;
                }
                // looping through each and every brick
             A:   for (int i =0; i<map.map.length; i++){
                    for (int j=0; j<map.map[0].length; j++){
                        if (map.map[i][j]>0){
                            int brickX = j * map.brickwidth + 80;
                            int brickY = i * map.brickHeight + 50;
                            int brickwidth = map.brickwidth;
                            int brickHeight = map.brickHeight;


                            Rectangle rect = new Rectangle(brickX,brickY,brickwidth,brickHeight);
                            Rectangle ballRect = new Rectangle(ballPositionX,ballPositionY,20,20);
                            Rectangle brickRect = rect;

                            if (ballRect.intersects(brickRect)){
                                map.setBrickValue(0,i,j);
                                totalBricks--;
                                score += 5;
                                    if (ballPositionX + 19 <= brickRect.x || ballPositionX + 1 >= brickRect.x + brickRect.width){
                                        ballXdir = -ballXdir;
                                    } else {
                                        ballYdir = -ballYdir;
                                    }
                                    break A;
                            }
                        }
                    }
                }

            ballPositionX += ballXdir;
            ballPositionY += ballYdir;
                if (ballPositionX < 0){
                    ballXdir = -ballXdir;
                }
                if (ballPositionY < 0){
                    ballYdir = - ballYdir;
                }
                if (ballPositionX > 670){
                    ballXdir = -ballXdir;
                }
        }

        repaint();


    }

    /**
     * Invoked when a key has been typed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key typed event.
     *
     * @param e
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Invoked when a key has been pressed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key pressed event.
     *
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT){
            if (playerX >= 600){
                playerX = 600;
            } else {
                moveRight();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT){
                if(playerX < 10 ){
                    playerX = 10;
                } else {
                    moveLeft();
                }
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            if (!play){
                play = true;
                ballPositionX = 120;
                ballPositionY = 350;
                ballXdir = -1;
                ballYdir = -2;
                playerX = 310;
                score = 0;
                totalBricks = 21;
                map = new MapGenerator(3,7);

                repaint();
            }
        }
    }

    private void moveLeft() {
            play = true;
            playerX -= 20;
    }

    private void moveRight() {
        play = true;
        playerX += 20;
    }

    /**
     * Invoked when a key has been released.
     * See the class description for {@link KeyEvent} for a definition of
     * a key released event.
     *
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e) {

    }
}
