/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testagent;

import java.util.Random;

/**
 *
 * @author p1608557
 */
public class YourAlgo
{
    static Random random;
    static int algoUsed;
    static float lAlpha;
    public static void find(Agent agent)
    {
        if(random==null)
            random = new Random();
        switch(algoUsed)
        {
            case 1:
                levy(agent);break;
            case 2:
                leviCrade(agent);break;
            case 3:
                pasBioInspire(agent);break;
            case 4:
                spiral(agent);break;
            default:
                randTen(agent);break;
        }
    }
    public static void setAlgoUsed(int algo)
    {
        algoUsed = algo;
    }
    //-------------------------------------------
    public static void setLevyAlpha(float alpha)
    {
        lAlpha = alpha;
    }
    //-------------------------------------------
    // Algo de deplacement 100% aleatoire
    public static void randTen(Agent agent)
    {
        int randXMove = random.nextInt(10-(-10)+1)+(-10);
        int randYMove = random.nextInt(10-(-10)+1)+(-10);
    
        agent.move(randXMove,randYMove);
    }
    // Algo de deplacement imitation levi
    public static void leviCrade(Agent agent)
    {
        int min = 5;
        int max = 300;
        int randXMove = random.nextInt(10-(-10)+1)+(-10);
        int randYMove = random.nextInt(10-(-10)+1)+(-10);
        int mut = random.nextInt(100)>98?max:min;
        agent.move((int)(((double)randXMove/10.0)*(double)mut),(int)(((double)randYMove/10.0)*(double)mut));
    }
    // Algo de deplacement pas bio inspire
    public static void pasBioInspire(Agent agent)
    {
        int bump = agent.move(0,agent.directionY);
        if(bump == 2 || bump == 0)
        {
            agent.directionY *= -1;
            bump = agent.move(agent.directionX,0);
        }
        
        if(bump == 1 || bump == 3)
        {
            agent.directionX *= -1;
        }
    }
    // Levy fonctionnel
    public static void levy(Agent agent)
    {
        double alpha = lAlpha;
        double a = random.nextGaussian();
        double b = random.nextGaussian();
        
        double transfo = 100.0;
        double mSum = 0.0;
        for(int i=0;i<transfo;++i)
        {
            double m = a/(Math.pow(Math.abs(b), 1/alpha));
            mSum += m;
        }
        double zn = 1.0/Math.pow(transfo, 1.0/alpha)*mSum;
        double pas = zn;
        
        double randXMove = random.nextDouble()*2-1;
        double randYMove = random.nextDouble()*2-1;
        
        agent.move((int)(randXMove*pas),(int)(randYMove*pas));
    }
    
    public static void spiral(Agent agent)
    {
        
    }
}
