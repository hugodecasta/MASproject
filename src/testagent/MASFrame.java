/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testagent;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Panel;
import java.awt.RenderingHints;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author p1608557
 */
public class MASFrame extends JFrame
{
    MASPanel panel;
    double size;
    
    public MASFrame(int width,int height, double size, Drawable system)
    {
        super("MAS test 1");
        this.size = size;
        this.setPreferredSize(new Dimension((int)(size*width+100), (int)(size*height+100)));
        //this.setResizable(false);
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        
        panel = new MASPanel(width,height,system);
        this.add(panel);
        this.pack();
        this.setVisible(true);
    }
    
    class MASPanel extends JPanel
    {
        Drawable system;
        
        public MASPanel(int width,int height,Drawable system)
        {
            this.setPreferredSize(new Dimension(width, height));
            this.system = system;
        }
        
        @Override 
        public void paint(Graphics gr){
            super.paint(gr);
            Graphics2D g2 =(Graphics2D) gr;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                          RenderingHints.VALUE_ANTIALIAS_ON);
            this.setBackground(Color.WHITE);
            system.draw(20,20,size,size,g2);
        }
    }
}
