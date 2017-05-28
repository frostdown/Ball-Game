import java.awt.*;
import java.util.List;
import java.util.*;
import java.applet.*;
import java.awt.event.MouseEvent;
import javax.swing.event.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import org.json.simple.JSONArray; 
import org.json.simple.JSONObject; 
import org.json.simple.parser.JSONParser; 
import org.json.simple.parser.ParseException; 


/*<applet code="Main" height=400 width=400></applet>*/


public class Main extends Applet implements Runnable
{

	public JsonParse parser;
/* Configuration arguments. These should be initialized with the values read from the config.JSON file*/					
    private int numBalls;
/*end of config arguments*/
    private int refreshrate = 15;	           //Refresh rate for the applet screen. Do not change this value. 
	private boolean isStoped = true;		     
    Font f = new Font ("Arial", Font.BOLD, 18);
	
	private Player player;			           //Player instance.
	
	private List myBalls;                      //Ball instance. You need to replace this with an array of balls.     
	Thread th;						           //The applet thread. 
	  
    Cursor c;				
    private GameWindow gwindow;                 // Defines the borders of the applet screen. A ball is considered "out" when it moves out of these borders.
	private Image dbImage;
	private Graphics dbg;

	
	class HandleMouse extends MouseInputAdapter 
	{

    	public HandleMouse() 
    	{
            addMouseListener(this);
        }
		
    	public void mouseClicked(MouseEvent e) 
    	{	
    		
        	if (!isStoped) {
        		player.mouseclicks += 1;
        		for(Object obj: myBalls){
        		
        			if (((Ball) obj).userHit (e.getX(), e.getY())) {
					((Ball) obj).ballWasHit ();
					if (obj instanceof ShrinkBall)
						{
							((ShrinkBall) obj).washit();
							player.shrinkhit += 1;
						}
					else if( obj instanceof BounceBall)
					{
						player.bouncehit += 1;
					}
					else
					{
						player.ballhit += 1;
					}
					
	        	    }
        		}
        	}
        		    else if (isStoped && e.getClickCount() == 2) {
				
        		    	isStoped = false;
				        init ();
        		    }
			    }
        
        	
    	

    	public void mouseReleased(MouseEvent e) 
    	{
           
    	}
        
    	public void RegisterHandler() 
    	{

    	}
    }
	
	HandleMouse hm = new HandleMouse();
	
	//JSON reader; you need to complete this function
	public void JSONReader()
	{

					
	}
	
	/*initialize the game*/
	public void init ()
	{	
		//reads info from JSON doc
		this.JSONReader();

		c = new Cursor (Cursor.CROSSHAIR_CURSOR);
		this.setCursor (c);	
				
		setBackground (Color.black);
		setFont (f);

		if (getParameter ("refreshrate") != null) {
			refreshrate = Integer.parseInt(getParameter("refreshrate"));
		}
		else refreshrate = 15;

		
		player = new Player ();
		/* The parameters for the GameWindow constructor (x_leftout, x_rightout, y_upout, y_downout) 
		should be initialized with the values read from the config.JSON file*/	
		gwindow = new GameWindow(0,800,0,1000);
		this.setSize(gwindow.x_rightout, gwindow.y_downout); //set the size of the applet window.
		myBalls = new ArrayList();
		parser = new JsonParse();
		parser.initiatePlayer(player);
		parser.initiateGameWindow(gwindow);
		parser.getballs(myBalls, player, gwindow);
		numBalls = myBalls.size();
		/*The skeleton code creates a single basic ball. Your game should support arbitrary number of balls. 
		* The number of balls and the types of those balls are specified in the config.JSON file.
		* The ball instances will be stores in an Array or Arraylist.  */
		/* The parameters for the Ball constructor (radius, initXpos, initYpos, speedX, speedY, maxBallSpeed, color) 
		should be initialized with the values read from the config.JSON file. Note that the "color" need to be initialized using the RGB values provided in the config.JSON file*/

		
	}
	
	/*start the applet thread and start animating*/
	public void start ()
	{		
		if (th==null){
			th = new Thread (this);
		}
		th.start ();
	}
	
	/*stop the thread*/
	public void stop ()
	{
		th=null;
	}

    
	public void run ()
	{	
		/*Lower this thread's priority so it won't interfere with other processing going on*/
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);

        /*This is the animation loop. It continues until the user stops or closes the applet*/
		while (true) {
			if (!isStoped) {
				for(Object obj: myBalls){
				if (obj instanceof BounceBall)
				{
					((BounceBall) obj).bmove();
				}
				else
				{
					((Ball) obj).move();
				}
				}
				}
			
            /*Display it*/
			repaint();
            
			try {
				
				Thread.sleep (refreshrate);
			}
			catch (InterruptedException ex) {
				
			}			
		}
	}
	

	
	public void paint (Graphics g)
	{
		/*if the game is still active draw the ball and display the player's score. If the game is active but stopped, ask player to double click to start the game*/ 
		if (!player.isGameOver()) {
			g.setColor (Color.yellow);
			
			g.drawString ("Score: " + player.getScore(), 10, 40);
			g.drawString("Lives:" +player.getlives(), 10, 70); // The player lives need to be displayed
			
			for(Object obj: myBalls){
			((Ball) obj).DrawBall(g);
			}
			
			if (isStoped) {
				g.setColor (Color.yellow);
				g.drawString ("Doubleclick on Applet to start Game!", 40, 200);
			}
		}
		/*if the game is over (i.e., the ball is out) display player's score*/
		else {
			g.setColor (Color.yellow);
			String results;
			String bounce = "Bounce";
			String regularball = "Regular";
			String Shrink = "Shrink";
			if(player.shrinkhit > player.bouncehit)
			{
				results = Shrink;
			}
			else if(player.bouncehit > player.ballhit)
			{
				results = bounce;
			}
			else
			{
				results = regularball;
			}
			
			g.drawString ("Game over!", 130, 100);
			g.drawString ("You scored " + player.getScore() + " Points!", 90, 140);
			g.drawString("Statistics: ", 400, 160);
			g.drawString("Number of Clicks: " + player.mouseclicks, 400, 180); // The number of clicks need to be displayed
			g.drawString("% of Successful Clicks: " + "%" + ((double)(player.ballhit + player.bouncehit + player.shrinkhit) /(double)player.mouseclicks) * 100,400,200); // The % of successful clicks need to be displayed
			g.drawString("Ball most hit: " + results, 400, 240); // The nball that was hit the most need to be displayed
				
			g.drawString ("Doubleclick on the Applet, to play again!", 20, 220);

			isStoped = true;	
		}
	}

	
	public void update (Graphics g)
	{
		
		if (dbImage == null)
		{
			dbImage = createImage (this.getSize().width, this.getSize().height);
			dbg = dbImage.getGraphics ();
		}

		
		dbg.setColor (getBackground ());
		dbg.fillRect (0, 0, this.getSize().width, this.getSize().height);

		
		dbg.setColor (getForeground());
		paint (dbg);

		
		g.drawImage (dbImage, 0, 0, this);
	}
}


