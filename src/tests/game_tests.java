package tests;

import static org.junit.Assert.*;



import org.json.JSONException;
import org.junit.Test;

import Server.Game_Server;
import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.DGraph;
import gameClient.MyGameGUI;

public class game_tests {



@Test
public void autogetgame() throws JSONException
{
	  game_service game=Game_Server.getServer(1);
	  MyGameGUI my=new MyGameGUI(game,1);
     assertEquals(my.getGame().toString(),game.toString());	  
}

@Test
public void autogetgraph() throws JSONException
{
	  game_service game=Game_Server.getServer(1);
	  MyGameGUI my=new MyGameGUI(game,1);
     assertEquals(my.getG().toString(),game.getGraph().toString());	  
}
@Test
public void autogetALGOgraph() throws JSONException
{
	  game_service game=Game_Server.getServer(1);
	  MyGameGUI my=new MyGameGUI(game,1);
	  DGraph g=new DGraph();
	  g.init(game.getGraph());
	  Graph_Algo ga=new Graph_Algo();
	  ga.init(g);
     assertEquals(my.getGa().toString(),game.getGraph().toString());	  
}
 @Test
 public void autogetRobot() throws JSONException
 {
	  game_service game=Game_Server.getServer(1);
	  MyGameGUI my=new MyGameGUI(game,1);
	  my.addRobots(game);
      assertEquals(my.getGame().getRobots().size(),game.getRobots().size());	  
 }

 @Test
 public void autosetfruit() throws JSONException
 {
	  game_service game=Game_Server.getServer(1);
	  MyGameGUI my=new MyGameGUI(game,1);
      assertEquals(my.getGame().getFruits().size(),game.getFruits().size());
	  
 }
 @Test
 public  void moveRobots() throws JSONException
 {
	  game_service game=Game_Server.getServer(1);
	  MyGameGUI my=new MyGameGUI(game,1);
		my.getGame().startGame();
		while(game.isRunning())
		{
				my.getM1().update(game,my.getFruits(),my.getRobots());
				synchronized(this) 
				{
						my.getM1().moveRobotsAuto(null);

				}
				try
				{
					Thread.sleep(100);
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
		}		
		String results1 = game.toString();

			my.getGame().startGame();
			while(game.isRunning())
			{
					my.getM1().update(game,my.getFruits(),my.getRobots());
					synchronized(this) 
					{
							my.getM1().moveRobotsAuto(null);

					}
					try
					{
						Thread.sleep(100);
					}
					catch(InterruptedException e)
					{
						e.printStackTrace();
					}
			}		
			String results2 = game.toString();
			assertEquals(results1,results2);

 }

 @Test
 public void autosetgraph() throws JSONException
 {
 	  game_service game=Game_Server.getServer(1);
 	 game_service game1=Game_Server.getServer(1);
 	 String g=game1.getGraph();
 	 DGraph gg=new DGraph();
 	 gg.init(g);
 	  MyGameGUI my=new MyGameGUI(game,1);
 	  my.setG(gg);
      assertEquals(my.getG().toString(),game1.getGraph().toString());	  
 }
 @Test
 public void autosetalgograph() throws JSONException
 {
 	  game_service game=Game_Server.getServer(1);
 	 game_service game1=Game_Server.getServer(2);
 	 String g=game1.getGraph();
 	 DGraph gg=new DGraph();
 	 Graph_Algo ga=new Graph_Algo();
 	 gg.init(g);
 	 ga.init(gg);
 	  MyGameGUI my=new MyGameGUI(game,1);
 	  my.setG(gg);
      assertEquals(my.getGa().toString(),game1.getGraph().toString());	  
 }

 @Test
 public void autosetgame() throws JSONException
 {
 	  game_service game=Game_Server.getServer(1);
 	 game_service game1=Game_Server.getServer(2);
 	  MyGameGUI my=new MyGameGUI(game,1);
 	  my.setGame(game1);
      assertEquals(my.getGame().toString(),game1.toString());	  
 }
 
 @Test
 public void autosetRobot() throws JSONException
 {
	  game_service game=Game_Server.getServer(1);
	  game_service game1=Game_Server.getServer(2);
	  MyGameGUI my=new MyGameGUI(game,1);
	  my.setGame(game1);
      my.addRobots(game1);
      assertEquals(my.getGame().getRobots().size(),game1.getRobots().size());	  
 }
	}


