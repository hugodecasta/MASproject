/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testagent;

import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author p1608557
 */
public class AgentManager implements Drawable
{
    ArrayList<Agent>agents;
    int agentNumber;
    
    public AgentManager(int agentNumber)
    {
        this.agentNumber = agentNumber;
        initAgents();
    }
    
    private void initAgents()
    {
        agents = new ArrayList<>();
        for(int i=0;i<agentNumber;++i)
        {
            int x = (int)(Math.random()*MASystem.width);
            int y = (int)(Math.random()*MASystem.height);
            agents.add(new Agent(x,y));
        }
    }
    
    public void run()
    {
        ArrayList<Agent> removers = new ArrayList<>();
        for(Agent a : agents)
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
        
        for(Agent a : removers)
        {
            agents.remove(a);
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
