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
    public static void find(Agent agent)
    {
        if(random==null)
            random = new Random();
        leviCrade(agent);
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
}
