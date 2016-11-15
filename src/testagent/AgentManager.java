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
    
    public AgentManager(int agentNumber,boolean killAgent,int maxPathLength)
    {
        this.agentNumber = agentNumber;
        this.killAgent = killAgent;
        initAgents(maxPathLength);
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
                a.find();
                for(Food f : MASystem.manger)
                {
                    if(f.touched(a))
                    {
                        removers.add(a);
                        f.pick();
                    }
                }
            }
        }
        
        for(Agent a : removers)
        {
            //agents.remove(a);
            a.eatten = true;
            a.nbEaten += 1;
        }
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
