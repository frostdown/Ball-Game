import java.awt.Color;
import java.util.Random;

public class BounceBall extends Ball {
	
	protected int bounceCount;
	protected int timesbounce;

	public BounceBall(int radius, int initXpos, int initYpos, int speedX, int speedY, int maxBallSpeed, Color color,
			Player player, GameWindow gameW, int newbounceCount) {
		super(radius, initXpos, initYpos, speedX, speedY, maxBallSpeed, color, player, gameW);
		// TODO Auto-generated constructor stub
		bounceCount = newbounceCount;
		timesbounce = 0;
	}
	
	public void bmove()
	{
		pos_x += x_speed;
		pos_y += y_speed;
	
		if ((pos_x < gameW.x_leftout) || (pos_x > gameW.x_rightout) || (pos_y < gameW.y_upout) || (pos_y > gameW.y_downout)) {	
			
			
			if(this.bounceCount > this.timesbounce)
			{
				this.x_speed = -(this.x_speed);
				this.y_speed = -(this.y_speed);
				this.timesbounce += 1;
			}
			else
			{
			int lives = player.getlives();
			if (lives > 0){
			player.setlives((lives - 1));
			Random rand = new Random();
			this.x_speed = rand.nextInt(4) - 2;
			this.y_speed = rand.nextInt(4) - 2;
			resetBallPosition();
			this.timesbounce = 0;
			}
			else
			{
				player.gameIsOver();
			}
			}
		}
	}
}
