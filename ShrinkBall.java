import java.awt.Color;
import java.util.Random;

public class ShrinkBall extends Ball {

	protected int shrinkrate;
	protected int timeshit;
	protected int originalsize;
	
	public ShrinkBall(int radius, int initXpos, int initYpos, int speedX, int speedY, int maxBallSpeed, Color color,
			Player player, GameWindow gameW, int newshrinkrate) {
		super(radius, initXpos, initYpos, speedX, speedY, maxBallSpeed, color, player, gameW);
		// TODO Auto-generated constructor stub
		
		shrinkrate = newshrinkrate;
		timeshit = 0;
		originalsize = radius;
	}
	
	public void washit()
	{
		this.timeshit += 1;
		this.radius = ((this.radius ) * (100 - this.shrinkrate))/100;
		this.player.addScore ((int)((player.scoreConstant * Math.abs(x_speed) + player.scoreConstant)* (2*this.timeshit)));
		if(this.timeshit > 3)
		{
			Random rand = new Random();
			this.resetBallPosition();
			this.x_speed = rand.nextInt(4) - 2;
			this.y_speed = rand.nextInt(4) - 2;
			this.radius = this.originalsize;
			this.timeshit = 0;
		}
	}

}
