import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.*;

/**
 * Use JOGL to draw a simple cube
 * with each face being a different color.  Rotations
 * can be applied with the arrow keys, the page up
 * key, and the page down key.  The home key will set
 * all rotations to 0.  Initial rotations about the
 * x, y, and z axes are 15, -15, and 0.  
 *
 * This program is meant as an example of using modeling
 * transforms, with glPushMatrix and glPopMatrix.
 *
 * Note that this program does not use lighting.
 */
public class Corkscrew extends GLJPanel implements GLEventListener, KeyListener {
    
    /**
     * A main routine to create and show a window that contains a
     * panel of type UnlitCube.  The program ends when the
     * user closes the window.
     */
    public static void main(String[] args) {
        JFrame window = new JFrame("Corkscrew -- ARROW KEYS ROTATE");
        Corkscrew panel = new Corkscrew();
        window.setContentPane(panel);
        window.pack();
        window.setLocation(50,50);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        panel.requestFocusInWindow();
    }
    
    /**
     * Constructor for class UnlitCube.
     */
    public Corkscrew() {
        super( new GLCapabilities(null) ); // Makes a panel with default OpenGL "capabilities".
        setPreferredSize( new Dimension(500,500) );
        addGLEventListener(this); // A listener is essential! The listener is where the OpenGL programming lives.
        addKeyListener(this);
    }
    
    //-------------------- methods to draw the cube ----------------------
    
    double rotateX = 15;    // rotations of the cube about the axes
    double rotateY = -15;
    double rotateZ = 0;
    
    private void corkscrew(GL2 gl2, double r, double g, double b, double rotations, double density, double size) {
        gl2.glColor3d(r,g,b);
        gl2.glScaled(size,size,size);
        gl2.glEnable(GL2.GL_POINT_SMOOTH);
        for(int i=0; i<rotations; i++) {
        	double shift = i/rotations;
        	
        	for(int j=0; j<density; j++) {
        		float subshift = (float)((j/density)/rotations);
        		float normalized_frac = (float)(shift+subshift);
        		double x = normalized_frac-0.5;
        		double y = 0.1*Math.cos(j * 2 * Math.PI / density);
        		double z = 0.1*Math.sin(j * 2 * Math.PI / density);
        		
        		gl2.glPointSize(10.0f*(normalized_frac+0.01f));
                gl2.glBegin(GL2.GL_POINTS);
                gl2.glVertex3d(x, y, z);
                gl2.glEnd();
        	}
        }
        gl2.glEnd();
    }
    
    
    //-------------------- GLEventListener Methods -------------------------

    /**
     * The display method is called when the panel needs to be redrawn.
     * The is where the code goes for drawing the image, using OpenGL commands.
     */
    public void display(GLAutoDrawable drawable) {    
        
        GL2 gl2 = drawable.getGL().getGL2(); // The object that contains all the OpenGL methods.
         
        gl2.glClear( GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT );
        
        gl2.glLoadIdentity();
        gl2.glRotated(rotateZ,0,0,1);
        gl2.glRotated(rotateY,0,1,0);
        gl2.glRotated(rotateX,1,0,0);

        corkscrew(gl2, 1, 1, 0, 3, 50, 1.5);

        
    } // end display()

    public void init(GLAutoDrawable drawable) {
           // called when the panel is created
        GL2 gl2 = drawable.getGL().getGL2();
        gl2.glMatrixMode(GL2.GL_PROJECTION);
        gl2.glOrtho(-1, 1 ,-1, 1, -1, 1);
        gl2.glMatrixMode(GL2.GL_MODELVIEW);
        gl2.glClearColor( 0, 0, 0, 1 );
        gl2.glEnable(GL2.GL_DEPTH_TEST);
    }

    public void dispose(GLAutoDrawable drawable) {
            // called when the panel is being disposed
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
            // called when user resizes the window
    }
    
    // ----------------  Methods from the KeyListener interface --------------

    public void keyPressed(KeyEvent evt) {
        int key = evt.getKeyCode();
        final int rotation = 10;
        if ( key == KeyEvent.VK_LEFT )
            rotateY -= rotation;
         else if ( key == KeyEvent.VK_RIGHT )
            rotateY += rotation;
         else if ( key == KeyEvent.VK_DOWN)
            rotateX += rotation;
         else if ( key == KeyEvent.VK_UP )
            rotateX -= rotation;
         else if ( key == KeyEvent.VK_PAGE_UP )
            rotateZ += rotation;
         else if ( key == KeyEvent.VK_PAGE_DOWN )
            rotateZ -= rotation;
         else if ( key == KeyEvent.VK_HOME )
            rotateX = rotateY = rotateZ = 0;
        repaint();
    }

    public void keyReleased(KeyEvent evt) {
    }
    
    public void keyTyped(KeyEvent evt) {
    }
    
}
