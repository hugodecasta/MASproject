/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testagent;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author p1608557
 */
public class Food implements Drawable
{
    int x,y;
    int size;
    boolean pick;
    
    public Food(int x,int y,int size)
    {
        this.x = x;
        this.y = y;
        this.size = size;
        this.pick = false;
    }
    
    public void pick()
    {
        this.pick = true;
    }
    
    public boolean touched(Agent agent)
    {
        if(pick)
            return false;
        double distance = Math.sqrt(Math.pow(agent.x-x,2)+Math.pow(agent.y-y,2));
        return distance<=size;
    }
    
    public String toString()
    {
        return "Food - "+x+", "+y+(this.pick?"  [PICKED]":"");
    }

    @Override
    public void draw(int x, int y, double w, double h, Graphics g)
    {
        if(pick)
            g.setColor(Color.lightGray);
        else
            g.setColor(Color.yellow);
        g.fillOval((int)(w*(x+this.x-size/2)), (int)(w*(y+this.y-size/2)), (int)(size*w), (int)(size*h));
    }
}
