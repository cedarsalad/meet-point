import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * Class to draw the ball and make it move
 * @author Mushu
 *
 */
public class Ball implements Runnable {
	private int x, y; // coordinates of the center of the ball
	private int diameter; //diameter
	private int xMeet, yMeet; // meeting point;
	private Color color; // ball color
	private JPanel jPanel; // JPanel where the ball will be trace
	/* The other ball to meet */
	private Ball partner;

	private boolean met; //Should be false up until the balls met

	public Ball(int x, int y, int diameter, Color color, JPanel jPanel) {
		this.x = x;
		this.y = y;
		this.diameter = diameter;
		this.color = color;
		this.jPanel = jPanel;
	}

	/* Set meeting point */
	void setMeetPoint(int xMeet, int yMeet) {
		this.xMeet = xMeet;
		this.yMeet = yMeet;
	}

	/*
	 * Move the ball from the starting point to the end point
	 * Implement the code to make the ball move (remember lab 5?)
	 * TODO
	 */
	void path(int xArrival, int yArrival) {
		if (x>xArrival)
			x--;
		else if (x<xArrival)
			x++;
		
		if (y>yArrival) 
			y--;
		else if (y<yArrival)
			y++;
	}

	public Ball getPartner() {
		return partner;
	}

	public void setPartner(Ball partner) {
		this.partner = partner;
	}

	/*
	 * The ball goes from starting position to the meeting point.
	 * Wait for his partner, then wait for about 3 seconds, then return to the 
	 * starting position.
	 * Use the MeetPoint in order to bring the ball to the meeting point.
	 * Don't forget to synchronize the thread in order to wait
	 * TODO
	 */
	public void run() {
		while (x != xMeet || y != yMeet) {
			path(xMeet, yMeet);
			jPanel.repaint();
			jPanel.revalidate();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	// draw circle base using the graphics
	public void drawCircle(Graphics g) {
		if (g == null)
			return;
		g.setColor(color);
		g.fillOval(x - diameter / 2, y - diameter / 2, diameter, diameter);
	}
}