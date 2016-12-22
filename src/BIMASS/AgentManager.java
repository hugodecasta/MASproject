/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BIMASS;

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
    ArrayList<Agent>agents; // liste des agents
    int agentNumber; // nombre des agents
    boolean killAgent; // indique si les agents ne peuvent manger qu'une nourriture
    boolean foodCollision; // indique si la collision (passage au dessus des nourritures) est activé
    int nbIteration; // décompte du nombre d'itérations
    
    public AgentManager(int agentNumber,boolean killAgent,int maxPathLength, boolean foodCollision)
    {
        this.agentNumber = agentNumber;
        this.killAgent = killAgent;
        initAgents(maxPathLength);
        this.foodCollision = foodCollision;
        nbIteration = 0;
    }
    
    // initialisation des agents dans le système
    private void initAgents(int maxPathLength)
    {
        // init image
        Image image = null;
        try {
            image = ImageIO.read(new File("src/BIMASS/eatter.png"));
        } catch (IOException ex) {
            Logger.getLogger(AgentManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        // init positionnement
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
    
    // interaction des agents sur le système
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

    // affichage des agents dans le système
    @Override
    public void draw(int x, int y, double w, double h, Graphics g)
    {
        for(Agent a : agents)
        {
            a.draw(x,y,w,h,g);
        }
    }
}
