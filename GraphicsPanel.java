/*********************
 * Author: Maxx Boehme
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;

public class GraphicsPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	private static final boolean PRETTY = true;  // true to anti-alias
    
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
    
    // take the current contents of the panel and write them to a file
    public void save(String filename) {
        String extension = filename.substring(filename.lastIndexOf(".") + 1);
        
        // create second image so we get the background color
        BufferedImage image2 = new BufferedImage(this.width, this.height,
                                                 BufferedImage.TYPE_INT_RGB);
        Graphics g = image2.getGraphics();
        g.setColor(this.getBackground());
        g.fillRect(0, 0, this.width, this.height);
        g.drawImage(this.image, 0, 0, this);
        
        // write file
        try {
            javax.imageio.ImageIO.write(image2, extension, new java.io.File(filename));
        } catch (java.io.IOException e) {
            System.err.println("Unable to save image:\n" + e);
        }
    }
}
