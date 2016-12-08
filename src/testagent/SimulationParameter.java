/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testagent;

/**
 *
 * @author p1608557
 */
public class SimulationParameter
{
    public int width=10000,height=10000;
    public int nbAgents=10,nbFood=100;
    public int speed=30;
    public float maxFoodPercent=0;
    public int pathMaxLength=200;
    public boolean killAgent=false;
    public boolean agentIsSized=true;
    public int usedAlgo=1;
    public boolean foodLife = true;
    public boolean foodLifeSize = false;
    public boolean foodCollision = true;
    public int foodSize=500;
    public float alpha = 2.0f;
    
    public SimulationParameter()
    {
        
    }
}
