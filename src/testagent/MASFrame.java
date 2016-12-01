/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testagent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.Border;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author p1608557
 */
public class MASFrame extends JFrame implements ActionListener
{
    MASPanel panel;
    JPanel GUI,algoPanel,optionPanel,cursorParamPanel;
    JButton initB,playPauseB,experimentB;
    MASystem system;
    ButtonGroup algoRadioGroup;
    JCheckBox killAgentBox,foodSizeBox;
    JSlider foodSlider,agentSlider,tailleEnvSlider,sleepSlider,pathSlider,taillePatchSlider;
    
    public MASFrame(int width,int height, MASystem system)
    {
        super("Bio-Inspired Multi-Agent Simulation System");
        this.system = system;
        this.system.setFrame(this);
        this.setAlwaysOnTop(true);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double sWidth = screenSize.getWidth();
        double sHeight = screenSize.getHeight();
        double tHeight = sHeight-sHeight*0.1;
        double guiSize = tHeight/3;
        double tWidth = tHeight+guiSize;
        
        
        this.setPreferredSize(new Dimension((int)tWidth, (int)tHeight));
        this.setResizable(false);
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        
        panel = new MASPanel(width,height,system);
        panel.setPreferredSize(new Dimension((int)tHeight,(int)tHeight));
        
        
        JPanel initPanel = new JPanel();
        initB = new JButton("INIT");
        initB.addActionListener(this);
        playPauseB = new JButton("play");
        playPauseB.addActionListener(this);
        initPanel.add(initB);
        initPanel.add(playPauseB);
        
        JPanel experimentPanel = new JPanel();
        experimentB = new JButton("EXPERIMENT");
        experimentB.addActionListener(this);
        experimentPanel.add(experimentB);
        
        algoPanel = new JPanel();
        algoPanel.setLayout(new BorderLayout());
        algoPanel.setBorder(BorderFactory.createMatteBorder(1,0,1,0,Color.GRAY));
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(2,2));
        //absParamPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        algoRadioGroup = new ButtonGroup();
        addRadio(p,"random", -1,true);
        addRadio(p,"Levy", 1);
        addRadio(p,"Levy simulation", 2);
        addRadio(p,"robot", 3);
        
        algoPanel.add(new JLabel("Algorithm choice", SwingConstants.CENTER),BorderLayout.NORTH);
        algoPanel.add(p,BorderLayout.CENTER);
        
        optionPanel = new JPanel();
        optionPanel.setLayout(new BorderLayout());
        JPanel pp = new JPanel();
        pp.setLayout(new GridLayout(1,2));
        killAgentBox = addOption(pp,"One eat per agent");
        foodSizeBox= addOption(pp,"random food size");
        optionPanel.add(new JLabel("Simulation Parameters", SwingConstants.CENTER),BorderLayout.NORTH);
        optionPanel.add(pp,BorderLayout.CENTER);
                
        cursorParamPanel = new JPanel();
        cursorParamPanel.setLayout(new GridLayout(2,3));
        foodSlider = addSlider("food count",1,101,2,1,10);
        agentSlider = addSlider("agent count",1,101,2,1,10);
        pathSlider = addSlider("path size",100,310,200,10,50);
        sleepSlider = addSlider("sleep time",1,101,30,10,10);
        taillePatchSlider = addSlider("food size",10,100,30,5,5);
        tailleEnvSlider = addSlider("MAS size",100,1000,500,10,100);
        
        GUI = new JPanel();
        //GUI.setBorder(BorderFactory.createLineBorder(Color.red));
       
        GUI.setPreferredSize(new Dimension((int)guiSize+22,(int)tHeight));
        GUI.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx= 0 ;
        c.gridy = 0;
        c.weighty = 1/8;
        c.weightx = 1;
        GUI.add(initPanel,c);
        c.gridy = 1;
        GUI.add(experimentPanel,c);
        c.gridy = 2;
        GUI.add(algoPanel,c);
        c.gridy = 3;
        GUI.add(optionPanel,c);
        c.weighty = 1/2;
        c.ipady = (int)(tHeight/1.5);
        c.weighty = 10;
        c.gridy = 4;
        GUI.add(cursorParamPanel,c);
        
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.VERTICAL;
        this.add(panel,BorderLayout.CENTER);
        this.add(GUI,BorderLayout.EAST);
        
        this.pack();
        this.setVisible(true);
        updateGUI();
        initMAS();
        launchSystem();
    }
    
    HashMap<String,Integer> radios;
    private void addRadio(JPanel p,String name,int algoId)
    {
        addRadio(p,name,algoId,false);
    }
    private JSlider addSlider(String name,int min,int max,int init,int minor,int major)
    {
        JSlider use = new JSlider(JSlider.VERTICAL,min, max, init);
        use.setName(name);
        use.setMajorTickSpacing(major);
        use.setMinorTickSpacing(minor);
        use.setPaintLabels(true);
        use.setPaintTicks(true);
        use.setSnapToTicks(true);
        use.addChangeListener(new myChangeListener());
        
        JPanel p = new JPanel();
        //p.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLUE));
        p.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weighty = 0.2;
        p.add(new JLabel(name),gbc);
        gbc.gridy = 1;
        gbc.weighty = 0.8;
        p.add(use,gbc);
        
        cursorParamPanel.add(p);
        return use;
    }
    class myChangeListener implements ChangeListener
    {
        public myChangeListener() {
        }
        @Override
        public void stateChanged(ChangeEvent e) {
            actionPerformed(null);
        }

    }
    private JCheckBox addOption(JPanel p,String name)
    {
        JCheckBox modif = new JCheckBox(name);
        modif.addActionListener(this);
        p.add(modif);
        return modif;
    }
    private void addRadio(JPanel p,String name,int algoId,boolean selected)
    {
        if(radios==null)
            radios = new HashMap<>();
        radios.put(name, algoId);
        JRadioButton new_radio = new JRadioButton(name);
        new_radio.setActionCommand(name);
        algoRadioGroup.add(new_radio);
        p.add(new_radio);     
        new_radio.setSelected(selected);
        new_radio.addActionListener(this);
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
        boolean eNull = e==null;
        if(!eNull && e.getSource() == initB)
        {
            initMAS();
        }
        else if(!eNull && e.getSource() == playPauseB)
        {
            if(system.play)
            {
                system.pause();
            }
            else
            {
                system.play();
            }
        }
        else if(!eNull && e.getSource() == experimentB)
        {
            launchGuiExperiment();
        }
        else
        {
            initNeeded = true;
        }
        updateGUI();
    }
    
    public void launchSystem()
    {
        system.run();
    }
    
    boolean initNeeded;
    public void updateGUI()
    {
        playPauseB.setText(system.play?"pause":"play");
        playPauseB.setEnabled(!initNeeded && !system.isDone());
        initB.setEnabled(!system.play && (initNeeded || system.isDone()));
        setPanelEnable(algoPanel,!system.play && system.experimentIsDone);
        setPanelEnable(optionPanel,!system.play && system.experimentIsDone);
        setPanelEnable(cursorParamPanel,!system.play && system.experimentIsDone);
    }
    
    public void setPanelEnable(JPanel p, boolean b)
    {
        for(Component c : p.getComponents())
        {
            if(c.getClass()==JPanel.class)
                setPanelEnable((JPanel)c, b);
            c.setEnabled(b);
        }
    }
    public void launchGuiExperiment()
    {
        ExperimentParamFrame eF = new ExperimentParamFrame(this);
    }
    public void launchExperiment(ExperimentParameter exp)
    {
        SimulationParameter sim = createSimParam();        
        exp.sim = sim;
        
        if(sim.usedAlgo!=1 && exp.testinParameterId == 2)
        {
            experimentError("Alpha can only be tested with the Levy algorithm selected");
            return;
        }
        else if(sim.killAgent)
        {
            experimentError("No experiment can be run with the \"One eat per agent\" parameter selected");
            return;
        }
            

        ExperimentThread thread = new ExperimentThread(system,exp);
        thread.start();
        System.out.println("je fonctionne en ||");
    }
    public void experimentError(String error)
    {
        JOptionPane.showMessageDialog(this,
            error,
            "Non testable parameters",
            JOptionPane.ERROR_MESSAGE);
    }
    public void experimentEnded()
    {
    }
    
    @Override
    public void paint(Graphics gr)
    {
        super.paint(gr);
    }
    
    SimulationParameter createSimParam()
    {
        SimulationParameter simPar = new SimulationParameter();
        
        simPar.usedAlgo = radios.get(algoRadioGroup.getSelection().getActionCommand());
        
        simPar.killAgent = killAgentBox.isSelected();
        simPar.foodSizeRandom = foodSizeBox.isSelected();
        
        simPar.nbFood = foodSlider.getValue();
        simPar.foodSize = taillePatchSlider.getValue();
        simPar.nbAgents = agentSlider.getValue();
        simPar.width = tailleEnvSlider.getValue();
        simPar.height = tailleEnvSlider.getValue();
        simPar.speed = sleepSlider.getValue();
        simPar.pathMaxLength = pathSlider.getValue();
        return simPar;
    }
    public void initMAS()
    {
        SimulationParameter simPar = createSimParam();
        panel.setSize(simPar.width,simPar.height);
        system.init(simPar);
        initNeeded = false;
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
        
        public void setSize(int width,int height)
        {
            this.width = width;
            this.height = height;
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
            allowDraw = false;
        }
    }
}
