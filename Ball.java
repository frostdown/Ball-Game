import java.applet.*;
import java.awt.*;
import java.util.Random;


public class Ball
{
    /*Properties of the basic ball. These are initialized in the constructor using the values read from the config.xml file*/
	protected  int pos_x;			
	protected int pos_y; 				
	protected int radius;
	protected int first_x;			
	protected int first_y;					
	protected int x_speed;			
	protected int y_speed;			
	protected int maxspeed;
	Color color;
	
    GameWindow gameW;
	Player player;
	
	/*constructor*/
	public Ball (int radius, int initXpos, int initYpos, int speedX, int speedY, int maxBallSpeed, Color color, Player player,  GameWindow gameW)
	{	
		this.radius = radius;

		pos_x = initXpos;
		pos_y = initYpos;

		first_x = initXpos;
		first_y = initYpos;

		x_speed = speedX;
		y_speed = speedY;

		maxspeed = maxBallSpeed;

		this.color = color;

		this.player = player;
		this.gameW = gameW;

	}

	/*update ball's location based on it's speed*/
	public void move ()
	{
		pos_x += x_speed;
		pos_y += y_speed;
		isOut();
	}

	/*when the ball is hit, reset the ball location to its initial starting location*/
	public void ballWasHit ()
	{	
		Random rand = new Random();
		this.x_speed = rand.nextInt(4) - 2;
		this.y_speed = rand.nextInt(4) - 2;
		resetBallPosition();
	}

	/*check whether the player hit the ball. If so, update the player score based on the current ball speed. */	
	public boolean userHit (int maus_x, int maus_y)
	{
		
		double x = maus_x - pos_x;
		double y = maus_y - pos_y;

		double distance = Math.sqrt ((x*x) + (y*y));
		
		if (Double.compare(distance-this.radius , player.scoreConstant + Math.abs(x_speed)) <= 0)  {
			player.addScore ((int)(player.scoreConstant * Math.abs(x_speed) + player.scoreConstant));
			
			return true;
		}
		else return false;
	}

    /*reset the ball position to its initial starting location*/
	protected void resetBallPosition()
	{
		pos_x = first_x;
		pos_y = first_y;
	}
	
	/*check if the ball is out of the game borders. if so, game is over!*/ 
	protected boolean isOut ()
	{
		if ((pos_x < gameW.x_leftout) || (pos_x > gameW.x_rightout) || (pos_y < gameW.y_upout) || (pos_y > gameW.y_downout)) {	
			resetBallPosition();
			int lives = player.getlives();
			if (lives > 0){
			player.setlives((lives - 1));
			Random rand = new Random();
			do 
			{
			this.x_speed = rand.nextInt(8) - 4;
			this.y_speed = rand.nextInt(8) - 4;
			}while(this.x_speed == 0 && this.y_speed == 0 );
			}
			else
			{
				player.gameIsOver();
			}
		
			return true;
		}	
		else return false;
	}

	/*draw ball*/
	public void DrawBall (Graphics g)
	{
		g.setColor (color);
		g.fillOval (pos_x - radius, pos_y - radius, 2 * radius, 2 * radius);
	}

}

