/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BIMASS;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

/**
 *
 * @author p1608557
 */
public class ExperimentResults
{
    ArrayList<UnitResult>results;
    ExperimentParameter exp;
    public ExperimentResults(ExperimentParameter exp)
    {
        results = new ArrayList<>();
        this.exp = exp;
    }
    
    public void addResult(int iterations, float value)
    {
        UnitResult res = new UnitResult();
        res.idTestParam = exp.testinParameterId;
        res.testParamValue = value;
        res.iterations = iterations;
        results.add(res);
    }
    
    public void saveExperimentResult(String fileName)
    {
        try
        {
            PrintWriter writer = new PrintWriter(fileName, "UTF-8");
            writer.println(exp.parameterNames[results.get(0).idTestParam]);
            writer.print("value;");
            for(UnitResult res : results)
            {
                writer.print(res.testParamValue+";");
            }
            writer.println();
            writer.print("nbIterations;");
            for(UnitResult res : results)
            {
                writer.print(res.iterations+";");
            }
            writer.close();
        }
        catch (Exception e)
        {
            System.err.println("Error while saving experiment results: "+e.getMessage());
        }
    }
    
    public void appendExperimentResult(String fileName,int iterations, float value)
    {
        File f = new File(fileName);
        try
        {
            if(!f.exists())
            {
                PrintWriter writer = new PrintWriter(fileName, "UTF-8");
                writer.close();
                Files.write(Paths.get(fileName), 
                        "value;nbIteration".getBytes(),
                        StandardOpenOption.APPEND);
            }
            Files.write(Paths.get(fileName), ("\n"+value+";"+iterations).getBytes(), StandardOpenOption.APPEND);
        }
        catch (Exception e)
        {
        }
    }
    
    @Override
    public String toString()
    {
        String ret = "";
        
        for(UnitResult res : results)
        {
            ret+="--EXPERIMENT ["+exp.parameterNames[res.idTestParam]+" : "+res.testParamValue+"]\n";
            ret+="\t- Iterations = "+res.iterations+"\n";
        }
        
        return ret;
    }
            
    class UnitResult
    {
        int iterations;
        int midPath;
        int idTestParam;
        float testParamValue;
    }
}
