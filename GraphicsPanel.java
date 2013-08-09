/**
 * @author Maxx Boehme
 * @version 1
 *
 * Class used to show board to the user
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GraphicsPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	private static final boolean PRETTY = true;
    
    private int width, height;    // dimensions of window frame
    private BufferedImage image;  // remembers drawing commands
    private Graphics2D g2;        // graphics context for painting

    public static final int DELAY = 200;  // delay between repaints in millis
    private Game g;

    // construct a drawing panel of given width and height enclosed in a window
    public GraphicsPanel(int width, int height) {
    	super(new FlowLayout(FlowLayout.CENTER, 0, 0));
        this.width = width;
        this.height = height;
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        
        this.setBackground(Color.BLACK);
        this.setPreferredSize(new Dimension(width, height));
        this.add(new JLabel(new ImageIcon(image)));
        this.g2 = (Graphics2D)image.getGraphics();
        this.g2.setColor(Color.BLACK);
        if (PRETTY) {
            this.g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                     RenderingHints.VALUE_ANTIALIAS_ON);
            this.g2.setStroke(new BasicStroke(1.1f));
        }
        this.setDoubleBuffered(true);
        
        new Timer(DELAY, this).start();
    }
    
    public void game(Game g){
    	this.g = g;
    }

    // used for an internal timer that keeps repainting
    public void actionPerformed(ActionEvent e) {
    	if(this.g != null) {
    		this.g.paint();
    	}
        this.repaint();
    }
    
    // clears the screen
    public void clear(){
    	this.g2.clearRect(0, 0, width, height);
    }

    // obtain the Graphics object to draw on the panel
    public Graphics2D getG() {
        return this.g2;
    }
    
    // makes the program pause for the given amount of time,
    // allowing for animation
    public void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {}
    }
}
