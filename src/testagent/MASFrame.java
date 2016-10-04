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
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author p1608557
 */
public class MASFrame extends JFrame
{
    MASPanel panel;
    JPanel GUI;
    
    public MASFrame(int width,int height, Drawable system)
    {
        super("MAS test 1");
        
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
        GUI.setBackground(Color.red);
        GUI.setPreferredSize(new Dimension((int)guiSize+22,(int)tHeight));
        
        /*double buttonBorder = 10;
        JButton btn = new JButton("coucou");
        GUI.add(btn,BorderLayout.CENTER);*/
        
        this.add(panel,BorderLayout.CENTER);
        this.add(GUI,BorderLayout.EAST);
        //this.add(panel,c);
        /*JPanel p1 = new JPanel();
        p1.setBackground(Color.red);
        this.add(p1,c);
        JPanel p2 = new JPanel();
        p2.setBackground(Color.blue);
        this.add(p2,c);
        */
        
        this.pack();
        this.setVisible(true);
        System.out.println(panel.getHeight()+" - "+panel.getWidth());
    }
    
    boolean allowDraw = false;
    public void draw()
    {
        allowDraw = true;
        this.repaint();
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
