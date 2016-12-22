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
public class ExperimentThread extends Thread
{
    MASystem system;
    ExperimentParameter params;
    
    public ExperimentThread(MASystem system,ExperimentParameter params)
    {
        this.system = system;
        this.params = params;
    }
    
    @Override
    public void run()
    {
        system.experiement(params);
    }
}
