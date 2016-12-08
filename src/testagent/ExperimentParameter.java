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
public class ExperimentParameter
{
    public SimulationParameter sim;
    public int testinParameterId = -1;
    public int stepNumber = 1;
    public int unitIteration = 10;
    public String[] parameterNames = {"Food experiment", "agent experiment", "alpha experiment", "food size"};
    
    public ExperimentParameter()
    {
    }
}