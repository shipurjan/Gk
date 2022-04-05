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
public class UnlitCube extends GLJPanel implements GLEventListener, KeyListener {
    
    /**
     * A main routine to create and show a window that contains a
     * panel of type UnlitCube.  The program ends when the
     * user closes the window.
     */
    public static void main(String[] args) {
        JFrame window = new JFrame("A Simple Unlit Cube -- ARROW KEYS ROTATE");
        UnlitCube panel = new UnlitCube();
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
    public UnlitCube() {
        super( new GLCapabilities(null) ); // Makes a panel with default OpenGL "capabilities".
        setPreferredSize( new Dimension(500,500) );
        addGLEventListener(this); // A listener is essential! The listener is where the OpenGL programming lives.
        addKeyListener(this);
    }
    
    //-------------------- methods to draw the cube ----------------------
    
    double rotateX = 15;    // rotations of the cube about the axes
    double rotateY = -15;
    double rotateZ = 0;
    
    private void polygon(GL2 gl2, double r, double g, double b, int n) {
    	//do zmiany funkcja
        gl2.glColor3d(r,g,b);
        gl2.glBegin(GL2.GL_TRIANGLE_FAN);
        gl2.glVertex3f(0.0f, 0.0f, 0.0f);
        for(int i=0; i < n; i++) {
        	gl2.glVertex3f(i*0.1f, i*0.1f, 0.0f); 
        }
        gl2.glEnd();
    }
    
    private void cube(GL2 gl2, double size) {
        gl2.glPushMatrix();
        gl2.glScaled(size,size,size); // scale unit cube to desired size
        final int n = 11;
        polygon(gl2,1, 0, 0, n); // red front face
        
        gl2.glPushMatrix();
        gl2.glRotated(90, 0, 1, 0);
        polygon(gl2,0, 1, 0, n); // green right face
        gl2.glPopMatrix();
        
        gl2.glPushMatrix();
        gl2.glRotated(-90, 1, 0, 0);
        polygon(gl2,0, 0, 1, n); // blue top face
        gl2.glPopMatrix();
        
        gl2.glPushMatrix();
        gl2.glRotated(180, 0, 1, 0);
        polygon(gl2,0, 1, 1, n); // cyan back face
        gl2.glPopMatrix();
        
        gl2.glPushMatrix();
        gl2.glRotated(-90, 0, 1, 0);
        polygon(gl2,1, 0, 1, n); // magenta left face
        gl2.glPopMatrix();
        
        gl2.glPushMatrix();
        gl2.glRotated(90, 1, 0, 0);
        polygon(gl2,1, 1, 0, n); // yellow bottom face
        gl2.glPopMatrix();
        
        gl2.glPopMatrix(); // Restore matrix to its state before cube() was called.
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

        cube(gl2,1);
        
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
