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
    public int width=500,height=500;
    public int nbAgents=2,nbFood=2;
    public int speed=30;
    public int pathMaxLength=200;
    public boolean killAgent=true;
    public int usedAlgo=1;
    public boolean foodSizeRandom=false;
    public int foodSize=30;
    public boolean experience = false;
    
    public SimulationParameter()
    {
        
    }
}
