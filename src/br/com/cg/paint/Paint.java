package br.com.cg.paint;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Paint extends JFrame implements MouseListener, ActionListener {

	private int pixelSize = 5, counter = 0;
	private int x1 = 0, y1 = 0, x2 = 0, y2 = 0;
	private int xOffset = -5, yOffset = -25;
	boolean twoPoints = false;

	public Paint() {
		setTitle("Paint Brush - Computer Graphics");
		setSize(1024, 720);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Container c = this.getContentPane();
		c.setLayout(new BorderLayout());
		c.addMouseListener(this);
		setVisible(true);
		JPanel panel = new JPanel();
		c.add(panel);
		JButton ddaButton = new JButton("DDA Rastering Algorithm");
		ddaButton.setSize(10,10);
		ddaButton.setVisible(true);
		ddaButton.addActionListener(this);
		JButton bresenhamButton = new JButton("Bresenham Rastering Algorithm");
		bresenhamButton.setSize(10,10);
		bresenhamButton.setVisible(true);
		bresenhamButton.addActionListener(this);
		panel.add(ddaButton);
		panel.add(bresenhamButton);

	

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (counter == 0) {
			x1 = e.getX() - xOffset;
			y1 = e.getY() - yOffset;
			counter++;
		} else {
			x2 = x1;
			y2 = y1;
			x1 = e.getX() - xOffset;
			y1 = e.getY() - yOffset;
			counter++;
			twoPoints = true;
			repaint();
		}

	}

	public void paint(Graphics g) {
		System.out.println("P" + "(" + x1 + "," + y1 + ")" + " " + "P\"" + "(" + x2 + "," + y2 + ")");
		if (twoPoints) {
			counter = 0;
			DDA(g, x1, y1, x2, y2);

		}
	}

	private void plotDDA(Graphics g, int x, int y) {
		g.setColor(Color.BLACK);
		g.fillOval(x, y, pixelSize, pixelSize);

	}

	public void DDA(Graphics g, int x1, int y1, int x2, int y2) {
		int dx = x2 - x1;
		int dy = y2 - y1;

		// calculate steps required for generating pixels
		int steps = Math.abs(dx) > Math.abs(dy) ? Math.abs(dx) : Math.abs(dy);

		// calculate increment in x & y for each steps
		float Xinc = dx / (float) steps;
		float Yinc = dy / (float) steps;

		// Put pixel for each step
		float X = x1;
		float Y = y1;
		for (int i = 0; i <= steps; i++) {
			plotDDA(g, Math.round(X), Math.round(Y)); // put pixel at (X,Y)
			X += Xinc; // increment in x at each step
			Y += Yinc; // increment in y at each step
	
		}
	}

	public void mousePressed(MouseEvent e) {
		// do nothing
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		Paint paint = new Paint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String paramString[] = e.paramString().split(",");
		String option = paramString[1].replace("cmd=", "");
		
		switch(option) {
			
		case "DDA Rastering Algorithm":
				System.out.println("=== Do Something ===");
				break;
				
		case "Bresenham Rastering Algorithm":
				System.out.println("=== Do Something ===");
				break;		
		}


		
	}

	
}

