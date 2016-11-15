/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testagent;

import java.awt.Color;
import java.awt.Graphics;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;

/**
 *
 * @author p1608557
 */
public class MASystem implements Drawable
{
    //TODO
    // Lire fichier arena
    public static ArrayList<Food> manger;
    int beginFoodCount;
    public static int width, height;
    AgentManager manager;
    int speed;
    MASFrame frame;
    long startTime,endTime,timeAdder;
    boolean play;
    boolean forceStopPlaying;
    
    public MASystem(SimulationParameter params)
    {
        init(params);
    }
    
    public void init(SimulationParameter params)
    {
        startTime = 0;
        endTime = 0;
        timeAdder = 0;
        this.width = params.width;
        this.height = params.height;
        manager = new AgentManager(params.nbAgents,params.killAgent,params.pathMaxLength);
        beginFoodCount = params.nbFood;
        initManger(params.nbFood,params.foodSizeRandom,params.foodSize);
        this.speed = params.speed;
        YourAlgo.setAlgoUsed(params.usedAlgo);
    }
    
    public void experiement(ExperimentParameter params)
    {
        forceStopPlaying = true;
        // 0: nbFood
        // 1: nbAgent
        
        int min=0, max=0;
        int step=1;
        switch(params.testinParameterId)
        {
            case 0:
                min = 1; max = 100; step = 10;
                break;
            case 1:
                min = 1; max = 100; step = 10;
                break;
        }
        SimulationParameter simul = params.sim;
        System.out.println(min+" - "+max);
        for(int i=min;i<=max;i+=step)
        {
            frame.allowDraw = false;
            switch(params.testinParameterId)
            {
                case 0:
                    simul.nbFood = i;
                    break;
                case 1:
                    simul.nbAgents = i;
                    frame.agentSlider.setValue(i);
                    break;
            }
            init(simul);
            
            boolean foodRemain = true;
            while(foodRemain)
            {
                foodRemain = updateOnly();
            }
            //frame.draw();
        }
        frame.launchSystem();
    }
    
    public void play()
    {
        if(remainingFood()<=0)
            return;
        timeAdder += endTime-startTime;
        startTime = System.currentTimeMillis();
        play = true;
    }
    
    public void pause()
    {
        play = false;
        frame.updateGUI();
    }

    public void setFrame(MASFrame frame)
    {
        this.frame = frame;
    }
    
    private void initManger(int nbMax,boolean randomSize,int size)
    {
        manger = new ArrayList<>();
        //TODO
        // food aléa
        // food spot
        int foodSize = size;
        for(int i=0;i<nbMax;++i)
        {
            int x = (int)(Math.random()*width);
            int y = (int)(Math.random()*height);
            int randSize = (int)(Math.random()*foodSize);
            x = x >width-foodSize?width-foodSize:x;
            y = y >height-foodSize?height-foodSize:y;
            x = x < foodSize?foodSize:x;
            y = y < foodSize?foodSize:y;
            manger.add(new Food(x,y,randomSize?randSize:foodSize));
        }
    }
    
    @Override
    public String toString()
    {
        String ret = "SYSTEM:";
        for(Food f : manger)
        {
            ret += "\n\t"+f;
        }
        ret += "\n"+manager;
        return ret;
    }
    public void run()
    {
        forceStopPlaying = false;
        while(!forceStopPlaying)
        {
            frame.draw();
            try
            {
                Thread.sleep(speed);
            }
            catch(Exception e)
            {
                System.err.println(e.getMessage());
            }
        }
    }
    Color info = new Color(0,0,0,100);
    
    public boolean updateOnly()
    {
            manager.run();
            return remainingFood()>0;
    }
    
    @Override
    public void draw(int x, int y, double w, double h, Graphics g)
    {
        if(remainingFood()>0 && play)
            manager.run();
        else if(play)
            pause();
        
        g.setColor(Color.GRAY);
        g.fillRect((int)(x*w), (int)(y*h), (int)(width*w), (int)(height*h));
        
        for(Food f : manger)
        {
            f.draw(x,y,w,h,g);
        }
        
        manager.draw(x,y,w,h,g);
        
        g.setColor(info);
        g.fillRect((int)(x*w),(int)(y*w),100,50);
        g.setColor(Color.white);
        g.drawString("FOOD : "+remainingFood()+" / "+beginFoodCount, (int)((x*w)+15), (int)((y*w)+25));
        
        if(play && remainingFood() > 0)
        {
            endTime = System.currentTimeMillis();
        }
        /*if(remainingFood() > 0)
            endTime = System.currentTimeMillis();*/
        //int second = (int)((endTime - startTime)/1000);
        int second = (int)(((endTime-startTime)+timeAdder)/1000);
        String strTime = second+"s";
        if(second>60)
        {
            strTime = (int)(Math.floor(second/60))+"m"+second%60+"s";
        }
        g.drawString("TIME : "+strTime, (int)((x*w)+15), (int)((y*w)+45));
    }
    
    public int remainingFood()
    {
        int ret = manger.size();
        for(Food a : manger)
            ret -= (a.pick?1:0);
        return ret;
    }
}
