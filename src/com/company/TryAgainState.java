package com.company;

import com.sun.javafx.geom.Vec2d;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

/**
 * Created by eliasffyksen on 15/03/15.
 */
public class TryAgainState extends State{

    public static Dimension padSize = new Dimension(10,100);
    public static Vec2d
            leftPad = new Vec2d(0, (Main.HEIGHT / 2)),
            rightPad = new Vec2d(Main.WIDTH - (int) padSize.getWidth(), Main.HEIGHT / 2),
            topPad = new Vec2d((Main.WIDTH / 2), 0),
            bottomPad = new Vec2d((Main.WIDTH / 2), Main.HEIGHT - (int) padSize.getWidth()),
            text = new Vec2d(Main.WIDTH/2-70,Main.HEIGHT/2 + 30);
    private Color color = Color.blue;
    private final int textwidth, textheight;
    private Canvas canvas;

    public TryAgainState(Canvas canvas){
        this.canvas = canvas;
        canvas.addMouseMotionListener(this);
        canvas.addMouseListener(this);
        String text = "PLAY";
        AffineTransform affinetransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
        Font font = new Font("TimesRoman", Font.BOLD, 50);
        textwidth = (int)(font.getStringBounds(text, frc).getWidth());
        textheight = (int)(font.getStringBounds(text, frc).getHeight());
    }

    public void render(Graphics g){

        g.setColor(Color.green);
        g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);

        g.setColor(Color.blue);
        g.fillRect((int) leftPad.x, (int) leftPad.y, (int) padSize.getWidth(), (int) padSize.getHeight());
        g.fillRect((int) rightPad.x, (int) rightPad.y, (int) padSize.getWidth(), (int) padSize.getHeight());
        g.fillRect((int) topPad.x, (int) topPad.y, (int) padSize.getHeight(), (int) padSize.getWidth());
        g.fillRect((int) bottomPad.x, (int) bottomPad.y, (int) padSize.getHeight(), (int) padSize.getWidth());

        g.setFont(new Font("TimesRoman", Font.BOLD, 50));
        g.drawString("GAME", Main.WIDTH / 2 - 150, Main.HEIGHT / 2 - 50);
        g.setColor(Color.red);
        g.drawString("OVER", Main.WIDTH / 2 + 20, Main.HEIGHT / 2 - 50);
        g.setColor(color);
        g.drawString("PLAY",(int)text.x,(int)text.y);

        String text = "SCORE: "+Main.points;
        AffineTransform affinetransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
        Font font = new Font("TimesRoman", Font.BOLD, 50);
        int textwidth = (int)(font.getStringBounds(text, frc).getWidth());
        int textheight = (int)(font.getStringBounds(text, frc).getHeight());
        g.setFont(font);
        g.setColor(Color.blue);
        g.drawString(text, Main.WIDTH / 2 - textwidth/2, Main.HEIGHT / 2 + textheight/2 + 80);
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

        if(this.x > text.x && this.y < text.y && this.x < text.x+textwidth && this.y > text.y-textheight){
            color = Color.red;
            if(this.pressed){
                Main.currentState = new GameState(canvas);
            }
        }else{
            color = Color.blue;
        }
    }
}
