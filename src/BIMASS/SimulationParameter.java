/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BIMASS;

/**
 *
 * @author p1608557
 */
public class SimulationParameter
{
    public int width=300,height=300;
    public int nbAgents=5,nbFood=1;
    public int speed=30;
    public float maxFoodPercent=0;
    public int pathMaxLength=200;
    public boolean killAgent=false;
    public boolean agentIsSized=false;
    public int usedAlgo=1;
    public boolean foodLife = false;
    public boolean foodLifeSize = false;
    public boolean foodCollision = false;
    public int foodSize=100;
    public float alpha = 1.5f;
    
    public SimulationParameter()
    {
        
    }
}
