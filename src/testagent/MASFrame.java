/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testagent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Panel;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javafx.scene.layout.Border;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;

/**
 *
 * @author p1608557
 */
public class MASFrame extends JFrame implements ActionListener
{
    MASPanel panel;
    JPanel GUI,absParamPanel,cursorParamPanel;
    JButton initB,playPauseB;
    MASystem system;
    ButtonGroup algoRadioGroup;
    JCheckBox killAgentBox,foodRepartitionBox;
    JSlider foodSlider,agentSlider,tailleEnvSlider,sleepSlider,pathSlider,taillePatchSlider;
    
    public MASFrame(int width,int height, MASystem system)
    {
        super("MAS test 1");
        this.system = system;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double sWidth = screenSize.getWidth();
        double sHeight = screenSize.getHeight();
        double tHeight = sHeight-sHeight*0.1;
        double guiSize = tHeight/3;
        double tWidth = tHeight+guiSize;
        
        
        this.setPreferredSize(new Dimension((int)tWidth, (int)tHeight));
        this.setResizable(false);
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        
        System.out.println(tHeight);
        panel = new MASPanel(width,height,system);
        panel.setPreferredSize(new Dimension((int)tHeight,(int)tHeight));
        
        
        JPanel initPanel = new JPanel();
        //initPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        initB = new JButton("INIT");
        initB.addActionListener(this);
        playPauseB = new JButton("play");
        playPauseB.addActionListener(this);
        initPanel.add(initB);
        initPanel.add(playPauseB);
        
        absParamPanel = new JPanel();
        absParamPanel.setLayout(new GridLayout(3,2));
        //absParamPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        algoRadioGroup = new ButtonGroup();
        addRadio("random", -1,true);
        addRadio("Levy", 1);
        addRadio("Levy simulation", 2);
        addRadio("robot", 3);
        
        killAgentBox = new JCheckBox("kill agent ?");
        foodRepartitionBox = new JCheckBox("random food ?");
        foodRepartitionBox.setSelected(true);
        absParamPanel.add(killAgentBox);
        absParamPanel.add(foodRepartitionBox);
                
        cursorParamPanel = new JPanel();
        cursorParamPanel.setLayout(new GridLayout(2,3));
        //cursorParamPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        foodSlider = new JSlider(JSlider.VERTICAL,1, 100, 2);
        foodSlider.setName("food count");
        foodSlider.setMajorTickSpacing(10);
        foodSlider.setMinorTickSpacing(0);
        foodSlider.setPaintLabels(true);
        foodSlider.setPaintTicks(true);
        foodSlider.setSnapToTicks(true);
        agentSlider = new JSlider(JSlider.VERTICAL,1, 100, 2);
        pathSlider = new JSlider(JSlider.VERTICAL,10, 301, 100);
        
        taillePatchSlider = new JSlider(JSlider.VERTICAL,10, 301, 100);
        sleepSlider = new JSlider(JSlider.VERTICAL,10, 301, 100);
        tailleEnvSlider = new JSlider(JSlider.VERTICAL,10, 301, 100);
        cursorParamPanel.add(foodSlider);
        cursorParamPanel.add(agentSlider);
        cursorParamPanel.add(pathSlider);
        cursorParamPanel.add(taillePatchSlider);
        cursorParamPanel.add(sleepSlider);
        cursorParamPanel.add(tailleEnvSlider);
        
        GUI = new JPanel();
        //GUI.setBorder(BorderFactory.createLineBorder(Color.red));
       
        GUI.setPreferredSize(new Dimension((int)guiSize+22,(int)tHeight));
        GUI.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx= 0 ;
        c.gridy = 0;
        c.weighty = 1;
        c.weightx = 1;
        GUI.add(initPanel,c);
        c.gridy = 1;
        GUI.add(absParamPanel,c);
        c.ipady = (int)(tHeight/1.5);
        c.weighty = 10;
        c.gridy = 2;
        GUI.add(cursorParamPanel,c);
        
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.VERTICAL;
        this.add(panel,BorderLayout.CENTER);
        this.add(GUI,BorderLayout.EAST);
        
        this.pack();
        this.setVisible(true);
    }
    
    HashMap<String,Integer> radios;
    private void addRadio(String name,int algoId)
    {
        addRadio(name,algoId,false);
    }
    private void addRadio(String name,int algoId,boolean selected)
    {
        if(radios==null)
            radios = new HashMap<>();
        radios.put(name, algoId);
        JRadioButton new_radio = new JRadioButton(name);
        new_radio.setActionCommand(name);
        algoRadioGroup.add(new_radio);
        absParamPanel.add(new_radio);     
        new_radio.setSelected(selected);
    }
    
    boolean allowDraw = false;
    public void draw()
    {
        allowDraw = true;
        this.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == initB)
        {
            initMAS();
        }
        else if(e.getSource() == playPauseB)
        {
            if(system.play)
            {
                playPauseB.setText("play");
                system.pause();
            }
            else
            {
                playPauseB.setText("pause");
                system.play();
            }
        }
    }
    
    public void initMAS()
    {
        SimulationParameter simPar = new SimulationParameter();
        simPar.usedAlgo = radios.get(algoRadioGroup.getSelection().getActionCommand());
        simPar.killAgent = killAgentBox.isSelected();
        simPar.nbFood = foodSlider.getValue();
        system.init(simPar);
    }
    
    class MASPanel extends JPanel
    {
        Drawable system;
        int width;
        int height;
        
        public MASPanel(int width,int height,Drawable system)
        {
            this.width = width;
            this.height = height;
            //this.setPreferredSize(new Dimension(width, height));
            this.system = system;
        }
        
        @Override 
        public void paint(Graphics gr){
            super.paint(gr);
            Graphics2D g2 =(Graphics2D) gr;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                          RenderingHints.VALUE_ANTIALIAS_ON);
            this.setBackground(Color.WHITE);
            double newSizeW = (double)this.getWidth()/(double)width;
            double newSizeH = (double)this.getHeight()/(double)height;
            double newSize = Math.min(newSizeW, newSizeH);
            
            system.draw(0,0,newSize,newSize,g2);
        }
    }
}
