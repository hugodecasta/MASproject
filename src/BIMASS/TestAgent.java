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
public class TestAgent {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        MASystem agentSystem = new MASystem(new SimulationParameter());
        MASFrame frame = new MASFrame(10, 10, agentSystem);
    }
    
}
