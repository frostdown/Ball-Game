public class Player
{
	
	private int score;			   //player score
	private boolean gameover=false;	
	public int scoreConstant = 10; //This constant value is used in score calculation. You don't need to change this. 
	private int playerlives;
	private int score2earnlife;
	public int bouncehit;
	public int shrinkhit;
	public int ballhit;
	public int mouseclicks;
	public int sucessclicks;
	
	public Player()
	{
		score = 0; //initialize the score to 0
		bouncehit = 0;
		shrinkhit = 0;
		ballhit = 0;
		mouseclicks = 0;
		sucessclicks = 0;
	}

	/* get player score*/
	public int getScore ()
	{
		return score;
	}
	
	public int getlives()
	{
		return playerlives;
	}
	
	public void setlives(int lives)
	{
		playerlives = lives;
	}
	
	public void setscore2earnlife(int score)
	{
		score2earnlife = score;
	}

	/*check if the game is over*/
	public boolean isGameOver ()
	{
		return gameover;
	}

	/*update player score*/
	public void addScore (int plus)
	{
		score += plus;
	}

	/*update "game over" status*/
	public void gameIsOver ()
	{
		gameover = true;
	}
}