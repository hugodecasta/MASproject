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
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *
 * @author p1608557
 */
public class MASFrame extends JFrame implements ActionListener
{
    MASPanel panel;
    JPanel GUI;
    JButton initB,playPauseB;
    MASystem system;
    
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
        
        GUI = new JPanel();
        GUI.setPreferredSize(new Dimension((int)guiSize+22,(int)tHeight));
        
        JPanel initPanel = new JPanel();
        initPanel.setBackground(Color.red);
        initB = new JButton("INIT");
        initB.addActionListener(this);
        playPauseB = new JButton("play");
        playPauseB.addActionListener(this);
        initPanel.add(initB);
        initPanel.add(playPauseB);
        
        JPanel cases = new JPanel();
        cases.setLayout(new GridLayout(3,2));
        cases.setBackground(Color.blue);
        ButtonGroup group = new ButtonGroup();
        JRadioButton rand_rad = new JRadioButton("random");
        rand_rad.setSelected(true);
        JRadioButton levy_rad = new JRadioButton("Levy");
        JRadioButton levys_rad = new JRadioButton("levy simu");
        JRadioButton baf_rad = new JRadioButton("back and forward");
        group.add(rand_rad);
        group.add(levy_rad);
        group.add(levys_rad);
        group.add(baf_rad);
        
        cases.add(rand_rad);
        cases.add(levy_rad);
        cases.add(levys_rad);
        cases.add(baf_rad);
        
        JCheckBox cb = new JCheckBox("kill agent ?");
        cases.add(cb);
       
        GUI.add(initPanel);
        GUI.add(cases);
        
        this.add(panel,BorderLayout.CENTER);
        this.add(GUI,BorderLayout.EAST);
        
        this.pack();
        this.setVisible(true);
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
            system.init(new SimulationParameter());
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
