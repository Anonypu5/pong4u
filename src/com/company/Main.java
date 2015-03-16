package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Main extends Canvas implements Runnable{

    public static JFrame jf;
    public static int points = 0;
    public static State currentState;
    public static int WIDTH = 800, HEIGHT = WIDTH/16*10, fps = 0, fpsTime = 0;
    public static boolean isRunning = true;
    public static BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
    public static Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");

    public static void main(String[] args) {
        jf = new JFrame("pong4u");
        jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
        Main game = new Main();
        currentState = new MainMenuState(game);
        jf.add(game);
        jf.pack();
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
        new Thread(game, "game").start();
    }

    public void run(){
        loop();
    }

    public void loop(){
        long time = System.nanoTime();
        long timeForTick = 0;
        while(isRunning){
            long currentTime = System.nanoTime();
            long t = currentTime - time;
            time = currentTime;
            timeForTick += t;
            fpsTime += t;
            if(fpsTime / 1000000000 >= 1){
                fpsTime = 0;
                fps = 0;
            }

            render();
            tick(t);
            fps++;
        }
    }

    public void render(){
        BufferStrategy bs = getBufferStrategy();
        if (bs== null){
            createBufferStrategy(2);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        currentState.render(g);
        bs.show();
    }

    public Main() {
        setSize(new Dimension(WIDTH, HEIGHT));
    }

    public void tick(long time){
        currentState.tick(time);
    }

}
