import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Transforms2D extends JPanel {

	private class Display extends JPanel {
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D)g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.translate(300,300);  // Moves (0,0) to the center of the display.
			int whichTransform = transformSelect.getSelectedIndex();

			// TODO Apply transforms here, depending on the value of whichTransform!
			switch(whichTransform) {
			case 1:
				g2.scale(0.5, 0.5);
				break;
			case 2:
				g2.rotate(Math.PI/4);
				break;
			case 3:
				g2.scale(-0.5,1);
				g2.rotate(Math.PI);
				break;
			case 4:
				g2.shear(0.25,0);
				break;
			case 5:
				g2.scale(1,0.5);
				g2.translate(0,-450);
				break;
			case 6:
				g2.rotate(Math.PI/2);
				g2.shear(0.25,0);
				break;
			case 7:
				g2.scale(0.5,1);
				g2.rotate(Math.PI);
				break;
			case 8:
				g2.translate(-50,100);
				g2.rotate(Math.PI/6);
				g2.scale(1,0.5);
				break;
			case 9:
				g2.translate(150,0);
				g2.shear(0,0.25);
				g2.rotate(Math.PI);
				break;
			}

			
			Polygon hendecagon = new Polygon();
			int vertices = 11;
			for (int i = 0; i < vertices; i++)
				hendecagon.addPoint(
						(int) (150 * Math.cos(i * 1.75 * Math.PI / vertices)),
						(int) (150 * Math.sin(i * 1.75 * Math.PI / vertices))
					); 	// okres ustawiony na mniej niż, aby wielokąt nie był foremny i 
						// żeby lepiej było widać przekształcenia
			g2.setColor(Color.black);
			g2.drawPolygon(hendecagon);
			g2.setColor(Color.red);
			g2.fillPolygon(hendecagon);
		}
	}

	private Display display;
	private BufferedImage pic;
	private JComboBox<String> transformSelect;

	public Transforms2D() throws IOException {
		pic = ImageIO.read(getClass().getClassLoader().getResource("shuttle.jpg"));
		display = new Display();
		display.setBackground(Color.YELLOW);
		display.setPreferredSize(new Dimension(600,600));
		transformSelect = new JComboBox<String>();
		transformSelect.addItem("None");
		for (int i = 1; i < 10; i++) {
			transformSelect.addItem("No. " + i);
		}
		transformSelect.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				display.repaint();
			}
		});
		setLayout(new BorderLayout(3,3));
		setBackground(Color.GRAY);
		setBorder(BorderFactory.createLineBorder(Color.GRAY,10));
		JPanel top = new JPanel();
		top.setLayout(new FlowLayout(FlowLayout.CENTER));
		top.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		top.add(new JLabel("Transform: "));
		top.add(transformSelect);
		add(display,BorderLayout.CENTER);
		add(top,BorderLayout.NORTH);
	}


	public static void main(String[] args) throws IOException {
		JFrame window = new JFrame("2D Transforms");
		window.setContentPane(new Transforms2D());
		window.pack();
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		window.setLocation( (screen.width - window.getWidth())/2, (screen.height - window.getHeight())/2 );
		window.setVisible(true);
	}

}