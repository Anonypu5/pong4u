package com.company;

import com.sun.javafx.geom.Vec2d;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.Random;

/**
 * Created by eliasffyksen on 15/03/15.
 */
public class GameState extends State{

    public static Dimension padSize = new Dimension(10,100), ballSize = new Dimension(30, 30);
    public Vec2d
            leftPad = new Vec2d(0, (Main.HEIGHT / 2)),
            rightPad = new Vec2d(Main.WIDTH - (int) padSize.getWidth(), Main.HEIGHT / 2),
            topPad = new Vec2d((Main.WIDTH / 2), 0),
            bottomPad = new Vec2d((Main.WIDTH / 2), Main.HEIGHT - (int) padSize.getWidth()),
            ball  = new Vec2d((Main.WIDTH / 2) - (int) (ballSize.getWidth() / 2), Main.HEIGHT / 2 - (int) ballSize.getHeight() / 2),
            ballVec = new Vec2d(250,250),
            ballTime = new Vec2d(0,0);
    private Canvas canvas;

    public GameState(Canvas canvas){
        Random r = new Random();
        int x = r.nextInt(200);
        x += 100;
        int y = r.nextInt(3);
        System.out.println(y);
        switch(y){
            case 0:
                ballVec.x = 250;
                ballVec.y = -250;
                break;
            case 1:
                ballVec.x = 250;
                ballVec.y = 250;
                break;
            case 2:
                ballVec.x = -250;
                ballVec.y = 250;
                break;
            case 3:
                ballVec.x = -250;
                ballVec.y = -250;
                break;
        }
        System.out.println(ballVec.x);
        System.out.println(ballVec.y);
        System.out.println(Math.sqrt(ballVec.x * ballVec.x + ballVec.y * ballVec.y));
        Main.points = 0;
        this.canvas = canvas;
        canvas.addMouseMotionListener(this);
        canvas.addMouseListener(this);
    }

    public void render(Graphics g){
        g.setColor(Color.green);
        g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);

        String text = ""+Main.points;
        AffineTransform affinetransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
        Font font = new Font("TimesRoman", Font.BOLD, 50);
        int textwidth = (int)(font.getStringBounds(text, frc).getWidth());
        int textheight = (int)(font.getStringBounds(text, frc).getHeight());
        g.setFont(font);
        g.setColor(Color.blue);
        g.drawString(""+Main.points, Main.WIDTH / 2 - textwidth/2, Main.HEIGHT / 2 + textheight/2);

        g.setColor(Color.red);
        g.fillOval((int) ball.x, (int) ball.y, (int) ballSize.getHeight(), (int) ballSize.getWidth());

        g.setColor(Color.blue);
        g.fillRect((int) leftPad.x, (int) leftPad.y, (int) padSize.getWidth(), (int) padSize.getHeight());
        g.fillRect((int) rightPad.x, (int) rightPad.y, (int) padSize.getWidth(), (int) padSize.getHeight());
        g.fillRect((int) topPad.x, (int) topPad.y, (int) padSize.getHeight(), (int) padSize.getWidth());
        g.fillRect((int) bottomPad.x, (int) bottomPad.y, (int) padSize.getHeight(), (int) padSize.getWidth());
    }

    public void tick(long time){
        int x = this.y - (int) padSize.getHeight() / 2;
        if(x < 0){
            x = 0;
        }else if(x > Main.HEIGHT - padSize.getHeight()){
            x = Main.HEIGHT - (int) padSize.getHeight();
        }
        leftPad.y = x;
        rightPad.y = x;

        x = this.x - (int) padSize.getHeight() / 2;
        if(x < 0){
            x = 0;
        }else if(x > Main.WIDTH - padSize.getHeight()){
            x = Main.WIDTH - (int) padSize.getHeight();
        }
        topPad.x = x;
        bottomPad.x = x;

        ballTime.x += time;
        ballTime.y += time;

        x = (int)(ballTime.x / 1000000000 * ballVec.x);
        ballTime.x -= x / ballVec.x * 1000000000;
        ball.x += x;

        x = (int)(ballTime.y / 1000000000 * ballVec.y);
        ballTime.y -= x / ballVec.y * 1000000000;
        ball.y += x;

        if(ballVec.y > 0 && ball.y + ballSize.getHeight() > Main.HEIGHT - padSize.getWidth() && ball.y < Main.HEIGHT){
            if(ball.x > bottomPad.x - ballSize.getWidth() && ball.x < bottomPad.x + padSize.getHeight()){
                ballVec.y = -ballVec.y;
                ballVec.y += (ballVec.y / Math.abs(ballVec.y)) * 10;
                ballVec.x += (ballVec.x / Math.abs(ballVec.x)) * 10;
                ball.y = Main.HEIGHT - padSize.getWidth() - ballSize.getHeight();
                Main.points++;
            }
        }else if(ball.y < 0 + padSize.getWidth() && ball.y > 0){
            if(ball.x > topPad.x - ballSize.getWidth() && ball.x < topPad.x + padSize.getHeight()){
                ballVec.y = -ballVec.y;
                ballVec.y += (ballVec.y / Math.abs(ballVec.y)) * 10;
                ballVec.x += (ballVec.x / Math.abs(ballVec.x)) * 10;
                ball.y = padSize.getWidth();
                Main.points++;
            }
        }
        if(ballVec.x > 0 && ball.x + ballSize.getWidth() > Main.WIDTH - padSize.getWidth() && ball.x < Main.WIDTH){
            if(ball.y > rightPad.y - ballSize.getHeight() && ball.y < rightPad.y + padSize.getHeight()){
                ballVec.x = -ballVec.x;
                ballVec.y += (ballVec.y / Math.abs(ballVec.y)) * 10;
                ballVec.x += (ballVec.x / Math.abs(ballVec.x)) * 10;
                ball.x = Main.WIDTH - padSize.getWidth() - ballSize.getWidth();
                Main.points++;
            }
        }else if(ball.x < 0 + padSize.getWidth() && ball.x > 0){
            if(ball.y > leftPad.y - ballSize.getHeight() && ball.y < leftPad.y + padSize.getHeight()){
                ballVec.x = -ballVec.x;
                ballVec.y += (ballVec.y / Math.abs(ballVec.y)) * 10;
                ballVec.x += (ballVec.x / Math.abs(ballVec.x)) * 10;
                ball.x = padSize.getWidth();
                Main.points++;
            }
        }
        if(ball.x > Main.WIDTH || ball.y > Main.HEIGHT || ball.x < 0 - ballSize.getWidth() || ball.y < 0 - ballSize.getHeight()){
            Main.currentState = new TryAgainState(canvas);
        }
    }
}
