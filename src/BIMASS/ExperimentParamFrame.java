/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BIMASS;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

/**
 *
 * @author p1608557
 */
public class ExperimentParamFrame extends JFrame implements ActionListener
{
    JButton launchButton;
    ButtonGroup experRadioGroup;
    MASFrame frame;
    ExperimentParameter exp;
    JPanel panel;
    JLabel affichage;
    JSpinner stepSpinner, unitSpinner;
    
    public ExperimentParamFrame(MASFrame frame)
    {
        //------------
        super("Experiment parameters panel");
        ExperimentParameter temp_params = new ExperimentParameter();
        this.frame = frame;
        //------------
        
        //------------
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double tHeight = screenSize.getHeight()/4;
        double tWidth = screenSize.getWidth()/3.5;
        
        //------------
        this.setPreferredSize(new Dimension((int)tWidth, (int)tHeight));
        this.setResizable(true);
        this.setAlwaysOnTop(true);
        //------------
        
        //------------
        panel = new JPanel(new GridLayout(3,1));
        panel.setPreferredSize(new Dimension((int)tWidth, (int)tHeight));
        //------------
        
        //------------
        launchButton = new JButton("LAUNCH");
        launchButton.addActionListener(this);
        panel.add(launchButton,BorderLayout.NORTH);
        //------------
        
        //------------
        exp = new ExperimentParameter();
        experRadioGroup = new ButtonGroup();
        int id = 0;
        JPanel algoPanel = new JPanel(new BorderLayout());
        algoPanel.add(new JLabel("Parameter to Experiment", SwingConstants.CENTER),BorderLayout.NORTH);
        JPanel algoPanelChoice = new JPanel();
        for(String name : exp.parameterNames)
        {
            addRadio(algoPanelChoice, name, id, id==0);
            id++;
        }
        algoPanel.add(algoPanelChoice);
        panel.add(algoPanel);
        algoPanel.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.GRAY));
        //------------
        
        //------------
        SpinnerModel model1 = new SpinnerNumberModel();
        SpinnerModel model2 = new SpinnerNumberModel();
        stepSpinner = new JSpinner(model1);
        unitSpinner = new JSpinner(model2);
        JPanel spinnerPanel = new JPanel(new GridLayout(1,4));
        spinnerPanel.add(new JLabel("Step", SwingConstants.CENTER));
        stepSpinner.setValue(temp_params.stepNumber);
        spinnerPanel.add(stepSpinner);
        spinnerPanel.add(new JLabel("Units", SwingConstants.CENTER));
        unitSpinner.setValue(temp_params.unitIteration);
        spinnerPanel.add(unitSpinner);
        panel.add(spinnerPanel);
        //------------
        
        //------------
        this.add(panel);
        this.pack();
        this.setVisible(true);
        //------------
    }
    
    public void addText(String text)
    {
        affichage.setText(affichage.getText()+"<html>"+text+"</html>");
    }
    
    HashMap<String,Integer> radios;
    private void addRadio(JPanel algoPanel, String name,int paramId,boolean selected)
    {
        if(radios==null)
            radios = new HashMap<>();
        radios.put(name, paramId);
        JRadioButton new_radio = new JRadioButton(name);
        new_radio.setActionCommand(name);
        experRadioGroup.add(new_radio);
        algoPanel.add(new_radio,BorderLayout.NORTH);
        new_radio.setSelected(selected);
        new_radio.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == launchButton)
        {
            launchExperiment();
        }
    }
    public void launchExperiment()
    {
        
            int paramId = radios.get(experRadioGroup.getSelection().getActionCommand());
            System.out.println(paramId);
            exp.testinParameterId = paramId;
            exp.stepNumber = (int)stepSpinner.getValue();
            exp.unitIteration = (int)unitSpinner.getValue();
            frame.launchExperiment(exp);
            this.dispose();
    }
}
