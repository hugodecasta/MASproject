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
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

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
        YourAlgo.setLevyAlpha(params.alpha);
    }
    
    public void experiement(ExperimentParameter params)
    {
        ExperimentResults results = new ExperimentResults(params);
        experimentIsDone = false;
        forceStopPlaying = true;
        frame.setPanelEnable(frame.GUI,false);
        // 0: nbFood
        // 1: nbAgent
        
        int min=0, max=0;
        int step=1;
        int nbUnit = 1;
        switch(params.testinParameterId)
        {
            case 0:
                min = 1; max = 100; step = 1; //nbUnit = 1;
                break;
            case 1:
                min = 1; max = 100; step = 1; //nbUnit = 1;
                break;
            case 2:
                min = 1; max = 20; step = 1; //nbUnit = 1;
                break;
        }
        SimulationParameter simul = params.sim;
        System.out.println("Running experiment: "+min+" -> "+max+" by "+step+" (using "+nbUnit+" test unit(s))");
        
        float testingValue = 0;
        for(int i=min;i<=max;i+=step)
        {
            frame.allowDraw = false;
            switch(params.testinParameterId)
            {
                case 0:
                    simul.nbFood = i;
                    testingValue = simul.nbFood;
                    frame.foodSlider.setValue(i);
                    break;
                case 1:
                    simul.nbAgents = i;
                    testingValue = simul.nbAgents;
                    frame.agentSlider.setValue(i);
                    break;
                case 2:
                    simul.alpha = (float)i/10f;
                    testingValue = simul.alpha;
                    break;
            }
            int iterMoyenne = 0;
            for(int j=0;j<nbUnit;j++)
            {
                init(simul);
                boolean foodRemain = true;
                while(foodRemain)
                {
                    foodRemain = updateOnly();
                }
                iterMoyenne += manager.nbIteration;
                frame.draw();
            }
            results.addResult(iterMoyenne/nbUnit, testingValue);
        }
        results.saveExperimentResult("results.csv");
        System.out.println(results);
        frame.setPanelEnable(frame.GUI,true);
        experimentIsDone = true;
        frame.launchSystem();
    }
    boolean experimentIsDone = true;
    
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
        Image food = null;
        Image eatted = null;        
        try
        {
            food = ImageIO.read(new File("src/testagent/food.png"));
            eatted = ImageIO.read(new File("src/testagent/eatted.png"));
        }catch(Exception e)
        {
            System.err.append(e.getMessage());
        }
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
            Food nf = new Food(x,y,randomSize?randSize:foodSize);
            nf.setImages(food, eatted);
            manger.add(nf);
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
        System.out.println("start Run");
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
    
    public boolean isDone()
    {
        return remainingFood()==0;
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
        g.fillRect((int)(x*w),(int)(y*w),100,100);
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
        g.drawString("ITER : "+manager.nbIteration, (int)((x*w)+15), (int)((y*w)+65));
        g.drawString("ALPHA : "+YourAlgo.lAlpha, (int)((x*w)+15), (int)((y*w)+85));
    }
    
    public int remainingFood()
    {
        int ret = manger.size();
        for(Food a : manger)
            ret -= (a.pick?1:0);
        return ret;
    }
}
