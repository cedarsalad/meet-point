import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Class to handle the meeting
 * @author Mushu
 *
 */
public class MeetPoint extends JPanel implements Runnable, MouseListener {
	private static final long serialVersionUID = 1L;
	private Ball ball1, ball2;
	private Square square;
	
	Point defCoord1 = new Point(22, 100);
	Point defCoord2 = new Point(470, 100);
	
	// meeting point
	private int xArrival, yArrival;
	private Thread threadBall1, threadBall2, mainThread;

	public MeetPoint() {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setContentPane(this);
		f.setSize(510, 500);
		f.setLocationRelativeTo(null);

		setBackground(Color.white);

		Random r = new Random();
		Color randomColor1 = new Color(r.nextFloat(),r.nextFloat(),r.nextFloat());
		Color randomColor2 = new Color(r.nextFloat(),r.nextFloat(),r.nextFloat());
		//CREATE OF EACH BALL (TODO)
		ball1 = new Ball(defCoord1.x, defCoord1.y, 40, randomColor1, this);
		ball2 = new Ball(defCoord2.x, defCoord2.y, 40, randomColor2, this);

		//GIVE EACH BALL A PARTNER (TODO)
		ball1.setPartner(ball2);
		ball2.setPartner(ball1);
		
		addMouseListener(this);
		f.setVisible(true);
	}

	//Mouse Listener
	//Set the meeting point based on the position of the click
	public void mouseClicked(MouseEvent evt) {
		if ((mainThread != null) && mainThread.isAlive())
			return;
		xArrival = evt.getX();
		yArrival = evt.getY();
		
		//create new square
		square = new Square(xArrival, yArrival, 50, Color.GREEN);
		
		//init and start mainThread
		mainThread = new Thread(this);
		mainThread.start();
	}

	public void mousePressed(MouseEvent evt) {}
	public void mouseReleased(MouseEvent evt) {}
	public void mouseEntered(MouseEvent evt) {}
	public void mouseExited(MouseEvent evt) {}

	//Painting all three shapes
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (ball1 != null)
			ball1.drawCircle(g);
		
		if (ball2 != null)
			ball2.drawCircle(g);
		
		if (square != null)
			square.drawSquare(g);
	}


	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 * 
	 * 	Run the main thread, in which will start the two other threads
	 *	Set Meeting point of the balls
	 *	Use thread.join() in order to run all threads at the sametime.
	 *	Don't forget to erase the square after they've met
	 *	(TODO)
	 */
	public void run() {
		ball1.setMeetPoint(xArrival, yArrival);
		ball2.setMeetPoint(xArrival, yArrival);
		threadBall1 = new Thread(ball1);
		threadBall2 = new Thread(ball2);
		threadBall1.start();
		threadBall2.start();
		while (true) {
		   try {
			   threadBall1.join();
			   threadBall2.join();
			   Thread.sleep(2000);
		      break;
		   } catch (InterruptedException e) {
		      e.printStackTrace();
		   }
		}
		square = null;
		this.repaint();
		ball1.setMeetPoint(defCoord1.x, defCoord1.y);
		ball2.setMeetPoint(defCoord2.x, defCoord2.y);
		threadBall1 = new Thread(ball1);
		threadBall2 = new Thread(ball2);
		threadBall1.start();
		threadBall2.start();
		while (true) {
		   try {
			   threadBall1.join();
			   threadBall2.join();
		      break;
		   } catch (InterruptedException e) {
		      e.printStackTrace();
		   }
		}
	}

	//Main
	public static void main(String[] args) {
		MeetPoint mg = new MeetPoint();
	}
}