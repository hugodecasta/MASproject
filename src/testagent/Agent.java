/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testagent;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.ArrayList;

/**
 *
 * @author p1608557
 */
public class Agent implements Drawable
{
    int type;
    int x,y;
    int angle;
    ArrayList<Point>chemin;
    Color myColor;
    boolean eatten;
    Image image;
    //------------------------ pasBioInspi
    public int directionY = Math.random()<.5?-10:10;
    public int directionX = Math.random()<.5?-30:30;
    //------------------------ pasBioInspi
    
    public Agent(int x,int y)
    {
        this.x = x;
        this.y = y;
        this.angle = 90;
        this.chemin = new ArrayList<>();
        initColor();
        initTailColor(200);
        try
        {
            image = ImageIO.read(new File("src/testagent/eatter.png"));
        }catch(Exception e)
        {
            System.err.append(e.getMessage());
        }
    }
    
    private void initColor()
    {
        int R = (int)(Math.random()*255);
        int G = (int)(Math.random()*255);
        int B = (int)(Math.random()*255);
        myColor = new Color(R, G, B);
    }
    
    public void find()
    {
        YourAlgo.find(this);
    }
    public int move(int xMove,int yMove)
    {
        int bumped = -1;
        
        if(chemin.size()==200)
            chemin.remove(0);
        
        x += xMove;
        y += yMove;
        
        int rebond = 10;
        if(x>MASystem.width)
        {
            bumped = 1;
            x = MASystem.width-rebond;
        }
        else if(x<0)
        {
            bumped = 3;
            x = rebond;
        }
        if(y>MASystem.height)
        {
            bumped = 2;
            y = MASystem.height-rebond;
        }
        else if(y<0)
        {
            bumped = 0;
            y = rebond;
        }
        chemin.add(new Point(x,y));
        return bumped;
    }
    
    public String toString()
    {
        String ret = "AGENT - "+x+", "+y+" - "+chemin.size();
        return ret;
    }

    @Override
    public void draw(int x, int y, double w, double h, Graphics g)
    {
        for(int i=1;i<chemin.size();++i)
        {
            int colorIndex = meinColors.length-(chemin.size()-i);
            colorIndex = colorIndex<0?0:colorIndex;
            g.setColor(meinColors[colorIndex]);
            //g.setColor(myColor);
            int px2 = chemin.get(i).x;
            int py2 = chemin.get(i).y;
            int px1 = chemin.get(i-1).x;
            int py1 = chemin.get(i-1).y;
            g.drawLine((int)((x+px1)*w), (int)((y+py1)*w), (int)(w*(x+px2)), (int)(w*(y+py2)));
        }
        //g.setColor(Color.white);
        int size = 10;
        int dsize = (int)(size*w)*2;
        //g.fillOval((int)(w*(x+this.x-size/2)), (int)(w*(y+this.y-size/2)), (int)(w*size), (int)(w*size));
        
        g.drawImage(image, (int)(w*(x+this.x-size/2)), (int)(w*(y+this.y-size/2)),dsize,dsize,null); 
    }
    
    Color[]meinColors;
    private void initTailColor(int tailLong)
    {
        meinColors = new Color[tailLong];
        for(int i=0;i<tailLong;++i)
        {
            int alpha = (int)((i*255)/tailLong);
            meinColors[i] = new Color(myColor.getRed(),myColor.getGreen(),myColor.getBlue(),alpha);
        }
    }
    //-----------------------------------------------------
    class Point
    {
        public int x,y;
        public Point(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
    }
}
