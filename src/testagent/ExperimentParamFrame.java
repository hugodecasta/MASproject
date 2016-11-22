/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testagent;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

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
    
    public ExperimentParamFrame(MASFrame frame)
    {
        super("Choix du paramètre d'expérimentation");
        this.frame = frame;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                
        double tHeight = screenSize.getHeight()/3;
        double tWidth = screenSize.getWidth()/3;
        
        this.setPreferredSize(new Dimension((int)tWidth, (int)tHeight));
        this.setResizable(true);
        
        panel = new JPanel();
        panel.setPreferredSize(new Dimension((int)tWidth, (int)tHeight));
        
        launchButton = new JButton("LAUNCH");
        launchButton.addActionListener(this);
        panel.add(launchButton,BorderLayout.NORTH);
        
        exp = new ExperimentParameter();
        experRadioGroup = new ButtonGroup();
        int id = 0;
        for(String name : exp.parameterNames)
        {
            addRadio(name, id, id==0);
            id++;
        }
        
        /*affichage = new JLabel("<html>VOICI L'AFFICHAGE<br></html>");
        addText("coucou je test je test<br>même à la ligne");
        panel.add(affichage,BorderLayout.CENTER);*/
        
        this.add(panel);
        this.pack();
        this.setVisible(true);
    }
    
    public void addText(String text)
    {
        affichage.setText(affichage.getText()+"<html>"+text+"</html>");
    }
    
    HashMap<String,Integer> radios;
    private void addRadio(String name,int paramId,boolean selected)
    {
        if(radios==null)
            radios = new HashMap<>();
        radios.put(name, paramId);
        JRadioButton new_radio = new JRadioButton(name);
        new_radio.setActionCommand(name);
        experRadioGroup.add(new_radio);
        panel.add(new_radio,BorderLayout.NORTH);
        new_radio.setSelected(selected);
        new_radio.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == launchButton)
        {
            int paramId = radios.get(experRadioGroup.getSelection().getActionCommand());
            System.out.println(paramId);
            exp.testinParameterId = paramId;
            frame.launchExperiment(exp);
        }
    }
}
