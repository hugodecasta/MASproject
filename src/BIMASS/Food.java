/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BIMASS;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

/**
 *
 * @author p1608557
 */
public class Food implements Drawable
{
    int x,y; // coordonnées sur le système
    int startSize,size; // taille de la food (et taille actuelle si useSize est actif)
    int life; // vie du patch de food (lié à sa taille)
    boolean pick; // indique si le patch a été mangé
    boolean useLife; // patch one shot ou avec de la vie
    boolean useSize; // doit avoir useLife actif, le patch diminueras de taille a chaque passage d'un agent
    int minSize; // taille minimale d'un patch
    boolean agentIsSized; // les agents disposent d'une hitbox (ilssont des volumes, pas des points)
    Image food, eatted; // images représentant les patchs sous leurs deux états
    
    public Food(int x,int y,int size,boolean useLife,boolean useSize, boolean agentIsSized)
    {
        this.x = x;
        this.y = y;
        this.size = size;
        this.life = size;
        this.useLife = useLife;
        this.useSize = useSize;
        this.startSize = size;
        this.minSize = 9;
        this.agentIsSized = agentIsSized;
        this.pick = false;
    }
    
    public void setImages(Image food, Image eatted)
    {
        this.food = food;
        this.eatted = eatted;
    }
    
    // gère le contact d'un agent et d'un patch
    public void pick(int power)
    {
        // si le patch possède de la vie, il en perd un montant, sinon il la perd entièrement
        this.life -= useLife?power:this.life;
        // si le patch perd de la taille à chaque contact, il passe à une taille liée à sa vie
        if(useSize)
        {
            this.size = this.life;
        }
        // si le patch n'as plus de vie, il regagne sa taille de départ
        this.pick = this.life <= minSize || (!useLife);
        if(pick)
            this.size = startSize;
    }
    
    public boolean touched(Agent agent)
    {
        if(pick)
            return false;
        double distance = Math.sqrt(Math.pow(agent.x-x,2)+Math.pow(agent.y-y,2));
        return distance<=size/(agentIsSized?1:2);
    }
    
    float distance(Point v, Point w)
    {
        return (float)Math.sqrt(Math.pow((w.x-v.x), 2) + Math.pow((w.y-v.y), 2));
    }
    Point minusPoints(Point a, Point b)
    {
        return new Point(a.x-b.x,a.y-b.y);
    }
    Point addPoints(Point a, Point b)
    {
        return new Point(a.x+b.x,a.y+b.y);
    }
    float length_squared(Point v, Point w)
    {
        return (float)Math.pow(distance(v,w), 2);
    }
    float dotPoints(Point a, Point b)
    {
        return a.x*b.x + a.y+b.y;
    }
    Point mulPoint(float mul, Point a)
    {
        return new Point(a.x*mul,a.y*mul);
    }
    boolean crossed(Point start, Point end)
    {
        Point center = new Point(x,y);
        
        Point p2 = new Point(end.x - start.x, end.y - start.y);
        float sum = p2.x*p2.x + p2.y*p2.y;
        float u = ((center.x - start.x) * p2.x + (center.y - start.y) * p2.y) / sum;

        if (u > 1)
            u = 1;
        else if (u < 0)
            u = 0;

        float xd = start.x + u * p2.x;
        float yd = start.y + u * p2.y;

        float dx = xd - center.x;
        float dy = yd - center.y;

        double distance = Math.sqrt(dx*dx + dy*dy);
        return distance<=size/(agentIsSized?1:2);
    }
    
    @Override
    public String toString()
    {
        return "Food - "+x+", "+y+(this.pick?"  [PICKED]":"");
    }

    @Override
    public void draw(int x, int y, double w, double h, Graphics g)
    {
        Image drawImage = food;
        if(pick)
            drawImage = eatted;
        int imageSize = (int)(size*w);
        g.drawImage(drawImage, (int)(w*(x+this.x-size/2)), (int)(w*(y+this.y-size/2)), 
                imageSize, imageSize, null);
        
        if(!pick)
        {
            g.setColor(Color.WHITE);
            Point pEaten = new Point(this.x-size/2, this.y+size/2);
            float percent = (((float)(life-minSize)/(float)(startSize-minSize))*100f);
            g.drawString((int)percent+"%", (int)(w*(x+pEaten.x)), (int)(w*(y+pEaten.y)));
        }
    }
}
