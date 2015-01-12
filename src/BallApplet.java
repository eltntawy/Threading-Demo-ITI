
import java.applet.Applet;
import java.util.Date;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.*;
import java.awt.event.* ;

public class BallApplet extends Applet {

	Image image;
	Graphics graphics;

	Ball ballArray [] = {
		new Ball(1000,1000), 
		new Ball(10,-1000),
		new Ball(-200,250) ,
		new Ball(300,-600),
		new Ball(-400,400),
		new Ball(500,-500) ,
		new Ball(-600,600),
		new Ball(700,-700),
		new Ball(-800,800),
		new Ball(900,-900) ,
		new Ball(-100,1000),
		new Ball(1010,-1010),
		new Ball(-920,120) ,
		new Ball(830,-130),
		new Ball(-640,140),
		new Ball(550,-150) ,
		new Ball(-160,160),
		new Ball(270,-170),
		new Ball(-480,180),
		new Ball(590,-190) ,
		new Ball(-600,200)
	};
	
	Thread th = null;
	boolean fristStart = false;
	boolean isRunning = false ;

	public void init() {

		Button btnStart = new Button("Start");
		Button btnStop = new Button("Stop");
		
		setSize(1000, 500);
		 

		
		

		btnStart.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {		
		
				isRunning = true;
				th = new Thread (new MyThread());
				th.start();	

		}
		});

		btnStop.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				
				//th = new Thread (new MyThread());
				//th.start();
				//th.stop();
				isRunning = false;
				
			}
		});


		
		
		add(btnStart);
		add(btnStop);

	}

	class Ball {
		int x=50,y=50;
		public static final int radius = 20;

		int xDir = -1;
		int yDir = -1;

		public Ball (int x , int y) {
			this.x = x ;
			this.y = y;
		}

		int getX() {return x; }
		int getY() {return y;}

	}

	public void paint (Graphics g) {


		boolean is= false;

		
		for(int i = 0 ; i < ballArray.length ; i++) {
			if(is) {
        		g.setColor(Color.red);	
        	} else {
        		g.setColor(Color.green);
        	}

        	g.fillOval(ballArray[i].getX(), ballArray[i].getY() , Ball.radius, Ball.radius);
        	is = !is;
        }
	}
	

	public void update (Graphics g) {
		
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();


	    graphics.setColor(getBackground());
	    graphics.fillRect(0,  0,  getWidth(),  getHeight());
	    graphics.setColor(getForeground());
	    paint(graphics);
	    g.drawImage(image, 0, 0, this);
	}

	class MyThread extends Thread {

	public void run () {
		//while(isRunning) {
		///while(true) {
			//getHeight()
			//getWidth()
			
		if(isRunning) {
			synchronized (this) {
				notifyAll();
				isRunning = true;
			}
		} else {
			
			try {
				synchronized (this) {
					wait();
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		synchronized (this) {
			for(int i = 0 ; i < ballArray.length ; i++) {
				
				Ball b1 = ballArray[i];
				
				if( b1.x == 0  || b1.x == getWidth()) {
					b1.xDir *=-1;
				}
				if(b1.y == 0 || b1.y == getHeight()) {
					b1.yDir *=-1;
				}

				
				if(b1.xDir > 0) {
					b1.x--;
				} else {
					b1.x++;
				}
			

			
				if(b1.yDir > 0 ) {
					b1.y--;
				} else {
					b1.y++;
				}
			
				if ( b1.x > getWidth() ) {
					b1.x = getWidth()-1;
				}
				if(b1.y > getHeight()) {
					b1.y = getHeight()-1;
				}

				if(b1.x < 0 ) {
					b1.x = 0;
				}
				if (b1.y < 0) {
					b1.y = 0;
				}

				

				for(int j = 0 ; j < ballArray.length ; j++) {
					for(int k = j ; k < ballArray.length ; k++) {

						b1 = ballArray[j];
						Ball b2 = ballArray[k];
						
						double dx = (b2.x + b2.radius) - (b1.x + b1.radius);
						double dy = (b2.y + b2.radius) - (b1.y + b1.radius);
						double distance = Math.sqrt(dx * dx + dy * dy);
						
						//System.out.println(b2.x + " " + b2.y +" - " +b1.x +" "+ b1.y);

						if(distance <= Ball.radius-5 ) {
							
							
							b1.xDir *= -1;
							b2.xDir *= -1;
							
							
							b1.yDir *= -1;
							b2.yDir *= -1;
							
						}
					}
				}
		}
		
			
			repaint();

		}
		try {
			Thread.sleep(10);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(isRunning)
			new MyThread().start();
		}
	
	
	//}
}

	boolean between (int x, int y , int size ) {

		if( x < y -10 && x <= y +10) {
			return true;
		}
		return false;
	}

}