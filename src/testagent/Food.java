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
import java.io.IOException;
import javax.imageio.ImageIO;
import sun.awt.image.ToolkitImage;

/**
 *
 * @author p1608557
 */
public class Food implements Drawable
{
    int x,y;
    int startSize,size;
    int life;
    boolean pick;
    boolean useLife;
    boolean useSize;
    int minSize;
    boolean agentIsSized;
    Image food, eatted;
    
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
    
    public void pick(int power)
    {
        this.life -= useLife?power:this.life;
        if(useSize)
        {
            this.size = this.life;
        }
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
        /*float l2 = length_squared(start, end);  // i.e. |w-v|^2 -  avoid a sqrt
        if (l2 == 0.0) return distance(center, start) <= size;   // v == w case
        // Consider the line extending the segment, parameterizted as v + t (w - v).
        // We find projection of point p onto the line. 
        // It falls where t = [(p-v) . (w-v)] / |w-v|^2
        // We clamp t from [0,1] to handle points outside the segment vw.
        float t = Math.max(0, Math.min(1, dotPoints(minusPoints(center, start), minusPoints(end,start)) / l2));
        Point projection = addPoints(start, mulPoint(t, minusPoints(end, start)));  // Projection falls on the segment
        return distance(center, projection) <= size;*/
        
        Point p2 = new Point(end.x - start.x, end.y - start.y);
        float sum = p2.x*p2.x + p2.y*p2.y;
        float u = ((center.x - start.x) * p2.x + (center.y - start.y) * p2.y) / sum;

        if (u > 1)
            u = 1;
        else if (u < 0)
            u = 0;

        float x = start.x + u * p2.x;
        float y = start.y + u * p2.y;

        float dx = x - center.x;
        float dy = y - center.y;

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
        /*if(pick)
            g.setColor(Color.lightGray);
        else
            g.setColor(Color.yellow);
        g.fillOval((int)(w*(x+this.x-size/2)), (int)(w*(y+this.y-size/2)), (int)(size*w), (int)(size*h));
        */
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
    //-----------------------------------------------------
}
