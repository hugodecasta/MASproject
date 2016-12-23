/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BIMASS;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

/**
 *
 * @author p1608557
 */
public class Agent implements Drawable
{
    int type;
    int x,y;
    int old_x,old_y;
    ArrayList<Point>chemin;
    Color myColor;
    boolean eatten;
    Image image;
    int maxPathLength;
    boolean usePathLength;
    Color[]meinColors;
    int power;
    public int nbEaten;
    //-----------------------------Donnée pour l'algo Robot
    public int directionY, directionX;
    
    public Agent(int x,int y,int maxPathLength)
    {
        old_x = x;
        old_y = y;
        this.x = x;
        this.y = y;
        this.power = 1;
        this.nbEaten = 0;
        this.chemin = new ArrayList<>();
        initColor();
        this.maxPathLength = maxPathLength;
        usePathLength = maxPathLength<301;
        if(usePathLength)
            initTailColor(maxPathLength);
        
    }
    
    public void setImage(Image image)
    {
        this.image = image;
    }
    
    private void initColor()
    {
        int min = 50,max = 255;
        int R = (int)(Math.random()*(max-min))+min;
        int G = (int)(Math.random()*(max-min))+min;
        int B = (int)(Math.random()*(max-min))+min;
        myColor = new Color(R, G, B);
    }
    
    public void find()
    {
        YourAlgo.find(this);
    }
    Point getPosition()
    {
        return new Point(this.x,this.y);
    }
    public int move(int xMove,int yMove)
    {
        int bumped = -1;
        
        if(usePathLength && chemin.size()==maxPathLength)
            chemin.remove(0);
        
        old_x = x;
        old_y = y;
        
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
        
        old_x = x;
        old_y = y;
        
        chemin.add(new Point(x,y));
        
        return bumped;
    }
    
    @Override
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
            if(usePathLength)
            {
                int colorIndex = meinColors.length-(chemin.size()-i);
                colorIndex = colorIndex<0?0:colorIndex;
                g.setColor(meinColors[colorIndex]);
            }
            else
            {
                g.setColor(myColor);
            }
            //g.setColor(myColor);
            float px2 = chemin.get(i).x;
            float py2 = chemin.get(i).y;
            float px1 = chemin.get(i-1).x;
            float py1 = chemin.get(i-1).y;
            g.drawLine((int)((x+px1)*w), (int)((y+py1)*w), (int)(w*(x+px2)), (int)(w*(y+py2)));
        }
        //g.setColor(Color.white);
        int size = 10;
        int dsize = (int)(size*w)*2;
        //g.fillOval((int)(w*(x+this.x-size/2)), (int)(w*(y+this.y-size/2)), (int)(w*size), (int)(w*size));
                
        g.drawImage(image, (int)(w*(x+this.x-size/2)), (int)(w*(y+this.y-size/2)),dsize,dsize,null);
        g.setColor(Color.WHITE);
        Point pEaten = new Point(this.x-size/2, this.y+size/2);
        g.drawString(""+nbEaten, (int)(w*(x+pEaten.x)), (int)(w*(y+pEaten.y)));
    }
    
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
}
