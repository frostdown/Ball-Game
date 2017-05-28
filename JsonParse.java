import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonParse {

	private static String filePath;
	
	public JsonParse( ){
		filePath = "/home/kristopher/Desktop/BallGame_skeleton/config.JSON";
	}
	
	public Player initiatePlayer(Player player) {

		try {
			// read the json file
			FileReader reader = new FileReader(filePath);

			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
/*
			// get a String from the JSON object
			String firstName = (String) jsonObject.get("firstname");
			System.out.println("The first name is: " + firstName);

			// get an array from the JSON object
			JSONArray lang= (JSONArray) jsonObject.get("languages");
			
			// take the elements of the json array
			for(int i=0; i<lang.size(); i++){
				System.out.println("The " + i + " element of the array: "+lang.get(i));
			}
			Iterator i = lang.iterator();

			// take each value from the json array separately
			while (i.hasNext()) {
				JSONObject innerObj = (JSONObject) i.next();
				System.out.println("language "+ innerObj.get("lang") + 
						" with level " + innerObj.get("knowledge"));
			}*/
			// handle a structure into the json object
			JSONObject structure = (JSONObject) jsonObject.get("Player");
			int score2earnlife = Integer.parseInt((String) structure.get("score2EarnLife"));
			
			player.setscore2earnlife(score2earnlife);
			
			int lives = Integer.parseInt((String) structure.get("numLives"));
			player.setlives(lives);

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ParseException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
		
		return player;

	}
	public GameWindow initiateGameWindow(GameWindow window) {

		try {
			// read the json file
			FileReader reader = new FileReader(filePath);

			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
			
			JSONObject structure = (JSONObject) jsonObject.get("GameWindow");
			int xLeft = Integer.parseInt((String) structure.get("x_leftout"));
			int xRight = Integer.parseInt((String) structure.get("x_rightout"));
			int yupout = Integer.parseInt((String) structure.get("y_upout"));
			int ydownout = Integer.parseInt((String) structure.get("y_downout"));
			
			window.setleft(xLeft);
			window.setright(xRight);
			window.setup(yupout);
			window.setdown(ydownout);
			
			
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ParseException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
		return window;

    }
	public List<Object> getballs(List<Object> balls, Player player, GameWindow gW){
		try {
			// read the json file
			FileReader reader = new FileReader(filePath);

			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
			
		
		// get an array from the JSON object
		JSONArray ball= (JSONArray) jsonObject.get("Ball");
		Iterator i = ball.iterator();

		// take each value from the json array separately
		while (i.hasNext()) {
			JSONObject innerObj = (JSONObject) i.next();
			JSONArray ballcolor = (JSONArray) innerObj.get("color");
			
            int id = Integer.parseInt((String) innerObj.get("id"));
            int radius = Integer.parseInt((String) innerObj.get("radius"));
            int xpos = Integer.parseInt((String) innerObj.get("initXpos"));
            int ypos = Integer.parseInt((String) innerObj.get("initYpos"));
            int xspeed = Integer.parseInt((String) innerObj.get("speedX"));
            int yspeed = Integer.parseInt((String) innerObj.get("speedY"));
            int maxspeed = Integer.parseInt((String) innerObj.get("maxBallSpeed"));
            Iterator z = ballcolor.iterator();
            int r  = Integer.parseInt((String) z.next());
            int g = Integer.parseInt((String) z.next());
            int b = Integer.parseInt((String) z.next());
            Color color = new Color(r,g,b);
            
		   
            id = id + 0;
          if(innerObj.get("type").equals("basicball"))
          {
        	  Ball newball = new Ball(radius, xpos, ypos, xspeed, yspeed, maxspeed, color, player, gW);
        	  balls.add(newball);
          }
          else if(innerObj.get("type").equals("bounceball"))
          {
        	  int bouncecount = Integer.parseInt((String) innerObj.get("bounceCount"));
        	  BounceBall newball = new BounceBall(radius, xpos, ypos, xspeed, yspeed, maxspeed, color, player, gW, bouncecount);
        	  balls.add(newball);
          }
          else if(innerObj.get("type").equals("shrinkball"))
          {
        	  int shrinkrate = Integer.parseInt((String) innerObj.get("shrinkRate"));
        	  ShrinkBall newball = new ShrinkBall(radius, xpos, ypos, xspeed, yspeed, maxspeed, color, player, gW, shrinkrate);
        	  balls.add(newball);
          }
          
          
		}
		
		
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ParseException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
		
		return balls;
	}
}
