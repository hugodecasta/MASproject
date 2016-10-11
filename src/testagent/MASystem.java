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
    public static int width, height;
    AgentManager manager;
    int speed;
    MASFrame frame;
    long startTime,endTime,pauseTime,time;
    boolean play;
    
    public MASystem(SimulationParameter params)
    {
        init(params);
        initFrame();
        run();
    }
    
    public void init(SimulationParameter params)
    {
        this.width = params.width;
        this.height = params.height;
        manager = new AgentManager(params.nbAgents,params.killAgent,params.pathMaxLength);
        startTime = System.currentTimeMillis();
        initManger(params.nbFood);
        this.speed = params.speed;
        YourAlgo.setAlgoUsed(params.usedAlgo);
        pause();
    }
    
    public void play()
    {
        play = true;
    }
    
    public void pause()
    {
        pauseTime = System.currentTimeMillis();
        play = false;
    }
    
    private void initFrame()
    {
        frame = new MASFrame(width,height,this);
    }
    private void initManger(int nbMax)
    {
        manger = new ArrayList<>();
        //TODO
        // food aléa
        // food spot
        int foodSize = 30;
        for(int i=0;i<nbMax;++i)
        {
            int x = (int)(Math.random()*width);
            int y = (int)(Math.random()*height);
            x = x >width-foodSize?width-foodSize:x;
            y = y >height-foodSize?height-foodSize:y;
            x = x < foodSize?foodSize:x;
            y = y < foodSize?foodSize:y;
            manger.add(new Food(x,y,foodSize));
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
        while(true)
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
            //System.out.println(this);
        }
    }
    Color info = new Color(0,0,0,100);
    
    public void loop()
    {       
    }
    @Override
    public void draw(int x, int y, double w, double h, Graphics g)
    {
        if(remainingFood()>0 && play)
            manager.run();
        
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
        g.drawString("FOOD : "+remainingFood(), (int)((x*w)+15), (int)((y*w)+25));
        
        if(play && remainingFood() > 0)
        {
            time += speed;
        }
        /*if(remainingFood() > 0)
            endTime = System.currentTimeMillis();*/
        //int second = (int)((endTime - startTime)/1000);
        int second = (int)(time/1000);
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
