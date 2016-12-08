/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testagent;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author p1608557
 */
public class AgentManager implements Drawable
{
    ArrayList<Agent>agents;
    int agentNumber;
    boolean killAgent;
    boolean foodCollision;
    int nbIteration;
    
    public AgentManager(int agentNumber,boolean killAgent,int maxPathLength, boolean foodCollision)
    {
        this.agentNumber = agentNumber;
        this.killAgent = killAgent;
        initAgents(maxPathLength);
        this.foodCollision = foodCollision;
        nbIteration = 0;
    }
    
    public float getChemin()
    {
        int chemin = 0;
        for(Agent a : agents)
        {
            chemin += a.distParcourue;
        }
        return chemin/agents.size();
    }
    
    private void initAgents(int maxPathLength)
    {
        Image image = null;
        try {
            image = ImageIO.read(new File("src/testagent/eatter.png"));
        } catch (IOException ex) {
            Logger.getLogger(AgentManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        agents = new ArrayList<>();
        for(int i=0;i<agentNumber;++i)
        {
            int x = (int)(Math.random()*MASystem.width);
            int y = (int)(Math.random()*MASystem.height);
            Agent na = new Agent(x,y,maxPathLength);
            na.setImage(image);
            agents.add(na);
        }
    }
    
    public void run()
    {
        ArrayList<Agent> removers = new ArrayList<>();
        
        for(Agent a : agents)
        {
            if(!(killAgent && a.eatten))
            {
                Point start = null, end=null;
                if(foodCollision)
                    start = a.getPosition();
                
                a.find();
                if(foodCollision)
                    end = a.getPosition();
                a.updateDistance();
                
                for(Food f : MASystem.manger)
                {
                    if(f.touched(a))
                    {
                        removers.add(a);
                        f.pick(a.power);
                    }
                    else if(foodCollision)
                    {
                        if(f.crossed(start, end))
                        {
                            removers.add(a);
                            f.pick(a.power);
                        }
                    }
                }
            }
        }
        
        for(Agent a : removers)
        {
            //agents.remove(a);
            a.eatten = true;
            a.nbEaten += a.power;
        }
        nbIteration++;
    }
    
    Point foodCross(Point start, Point end)
    {
        return null;
    }
    
    @Override
    public String toString()
    {
        String ret = "Manager:";
        for(Agent a : agents)
        {
            ret +="\n\t"+agents;
        }
        return ret;
    }

    @Override
    public void draw(int x, int y, double w, double h, Graphics g)
    {
        for(Agent a : agents)
        {
            a.draw(x,y,w,h,g);
        }
    }
}
