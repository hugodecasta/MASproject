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
    public int width=300,height=300;
    public int nbAgents=10,nbFood=1;
    public int speed=30;
    public int pathMaxLength=200;
    public boolean killAgent=true;
    public int usedAlgo=1;
    public boolean foodSizeRandom=false;
    public int foodSize=150;
    public float alpha = 1f;
    
    public SimulationParameter()
    {
        
    }
}
