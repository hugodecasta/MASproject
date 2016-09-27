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
    int size;
    boolean pick;
    Image food, eatted;
    
    public Food(int x,int y,int size)
    {
        this.x = x;
        this.y = y;
        this.size = size;
        this.pick = false;
        try
        {
            food = ImageIO.read(new File("src/testagent/food.png"));
            eatted = ImageIO.read(new File("src/testagent/eatted.png"));
        }catch(Exception e)
        {
            System.err.append(e.getMessage());
        }
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
        return distance<=size/2;
    }
    
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
    }
}
