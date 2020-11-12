import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GraphApp extends JPanel {

	private static final long serialVersionUID = -7418972846315343197L;
	boolean sine = false;
    boolean negsine =false;
    //use constants for better readability
    private static final double W = 300, H = 350, AMPLITUDE = H/3;
    private static final int MARGIN =30, GAP = 15,DOT_SIZE = 3, SPEED = 5;

    //starting point
    private double x = MARGIN;
    private final double y = H/2;
    private final int dX = 1; //x increment

    //you need to use doubles to avoid rounding error and have use non integer coordinates
    private final List<Point2D.Double> points;
    private final List<Point2D.Double> points2;

     final Timer timer;

     public void reset() {
    	 timer.stop();
    	 x = MARGIN;
    	 points.clear();
    	 points2.clear();
     }
     
    public GraphApp(boolean sine, boolean negsine) {
    	this.sine=sine;
    	this.negsine=negsine;
        setPreferredSize(new Dimension((int)W, (int)H));
        points = new ArrayList<>();
        points2 = new ArrayList<>();
        timer = new Timer(SPEED, e->addPoints()); //timer to add sine points
       // timer.start();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        Graphics2D g3 = (Graphics2D)g;
        
        Shape xAxis = new Line2D.Double(MARGIN-30, H/2, W-MARGIN, H/2);
        g2.draw(xAxis);
        Shape yAxis = new Line2D.Double(W/10, MARGIN, W/10, H-MARGIN);
        g2.draw(yAxis);
        g.drawString("y", (int)(W/10-GAP),MARGIN);
        g.drawString("x", (int)(W-GAP), (int)(H/2+GAP));

        g2.setColor(Color.blue);
       
     
     	if(sine) {
        for(Point2D.Double p : points){
            Shape point = new Ellipse2D.Double(p.getX(), p.getY(),DOT_SIZE , DOT_SIZE);
            g2.draw(point);
            
        }
      }
      if(negsine) {
        g3.setColor(Color.ORANGE);
        for(Point2D.Double p : points2){
            Shape point = new Ellipse2D.Double(p.getX(), p.getY(),DOT_SIZE , DOT_SIZE);
            g3.draw(point);
            
        }
        
      }
      

    }

    private void addPoints() {

        double angel = - Math.PI + 2* Math.PI * ((x-MARGIN)/(W- 2*MARGIN));//angle in radians
        double newY = y + AMPLITUDE  * Math.sin(angel);
        double newY2 = y + AMPLITUDE * (-1) * Math.sin(angel);
        points.add(new Point2D.Double(x, newY));
        points2.add(new Point2D.Double(x, newY2));
        
        x += dX;
        if(x >= W-MARGIN) {
            timer.stop();
        }
        repaint();
    }
}