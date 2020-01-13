package gameClient;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import Server.Fruit;
import Server.Game_Server;
import Server.RobotG;
import Server.game_service;
import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import utils.Point3D;
import utils.StdDraw;

public class MyGameGUI implements Runnable  
{
	public static double EPS=0.001;
	private game_service game;
	private DGraph g;
	private ArrayList<Robot> robots;
	private ArrayList<myFruits> fruits;
	private int mc;
	
	public MyGameGUI()
	{
		this.g=null;
		this.game=null;
		this.robots=new ArrayList<Robot>(); // robots list
		this.fruits=new ArrayList<myFruits>(); //fruits list
	}
	
public MyGameGUI(game_service game,DGraph g) 
	{
		this.game=game; //gmae server object
		this.g =g;   // graph object
		this.mc=g.getMC(); //num of changes 
		this.robots=new ArrayList<Robot>(); // robots list
		this.fruits=new ArrayList<myFruits>(); //fruits list
		limit();
		drawS();
		addFruit(game);
		addRobots(game);
		drawFruits();
		drawRobot();
	}

	public void limit() 
	{	
		double maxX=Double.NEGATIVE_INFINITY; //check if to put them inside the func
		double maxY=Double.NEGATIVE_INFINITY;
		double minX=Double.POSITIVE_INFINITY;
		double minY=Double.POSITIVE_INFINITY;
		for(Iterator<node_data> verticles=g.getV().iterator();verticles.hasNext();)
		{
			int p=verticles.next().getKey();
			if(g.getNode(p).getLocation().x()>maxX)
				maxX=g.getNode(p).getLocation().x();
			if(g.getNode(p).getLocation().y()>maxY)
				maxY=g.getNode(p).getLocation().y();
			if(g.getNode(p).getLocation().x()<minX)
				minX=g.getNode(p).getLocation().x();
			if(g.getNode(p).getLocation().y()<minY)
				minY=g.getNode(p).getLocation().y();	
		} 
		StdDraw.setCanvasSize(800,600);
		minX=minX-EPS;maxX=maxX+EPS;
		minY=minY-EPS/4;maxY=EPS/4+maxY;
		StdDraw.setXscale(minX,maxX);
		StdDraw.setYscale(minY,maxY);
	}
	
	public void drawS() 
	{	
		for(Iterator<node_data> verticles=g.getV().iterator();verticles.hasNext();) 
		{
			int point=verticles.next().getKey();
			StdDraw.setPenColor(Color.BLUE);
			StdDraw.setPenRadius(0.020);
			StdDraw.point(g.getNode(point).getLocation().x(),g.getNode(point).getLocation().y());
			StdDraw.text(g.getNode(point).getLocation().x(),g.getNode(point).getLocation().y()+0.0002, (""+point));
			
			try {
				for(Iterator<edge_data> edges=g.getE(point).iterator();edges.hasNext();) 
				{
					edge_data line=edges.next();
					int dest=g.getNode(line.getDest()).getKey();
					int src=point;
					double weight=line.getWeight();
					double tmp=weight*100;
					int fin=(int) tmp;
					weight=(fin/100.0);
					StdDraw.setPenColor(Color.DARK_GRAY);
					StdDraw.setPenRadius(0.005);
					StdDraw.line(g.getNode(src).getLocation().x(),g.getNode(src).getLocation().y(), g.getNode(dest).getLocation().x(),g.getNode(dest).getLocation().y());
					StdDraw.text(g.getNode(src).getLocation().x()+(g.getNode(dest).getLocation().x()-g.getNode(src).getLocation().x())/4,g.getNode(src).getLocation().y()+(g.getNode(dest).getLocation().y()-g.getNode(src).getLocation().y())/4,(""+weight));
					StdDraw.setPenColor(Color.YELLOW);
					StdDraw.setPenRadius(0.015);
					StdDraw.point(g.getNode(dest).getLocation().x()+(g.getNode(src).getLocation().x()-g.getNode(dest).getLocation().x())/10,g.getNode(dest).getLocation().y()+(g.getNode(src).getLocation().y()-g.getNode(dest).getLocation().y())/10);
				}
			}
			catch (Exception e) {}
		}
	}
	
	public void addRobots(game_service g) {
		
		List<String> list = g.getRobots();
		if(list!=null) {
			String _json = list.toString();

			try {
				JSONArray line= new JSONArray(_json);
				
				for(int i=0; i< line.length();i++) {
					
					JSONObject t= line.getJSONObject(i);
					JSONObject jrobots = t.getJSONObject("Robot");
					String loc = jrobots.getString("pos");
					String[] point = loc.split(",");
					double x = Double.parseDouble(point[0]);
					double y = Double.parseDouble(point[1]);	
					double z = Double.parseDouble(point[2]);
					Point3D p = new Point3D(x,y,z);
					int id = jrobots.getInt("id");
					int src = jrobots.getInt("src");
					int dest = jrobots.getInt("dest");
					Robot r = new Robot(id,src,dest,p);
					this.robots.add(r);				
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
	}
	}
	
	private void addFruit(game_service g) 
	{	
		List<String> list = g.getFruits();
		if(list!=null) {
			String json = list.toString();

			try {
				JSONArray line= new JSONArray(json);
				
				
				for(int i =0; i<line.length();i++) {
					JSONObject t = line.getJSONObject(i);
					JSONObject fru = t.getJSONObject("Fruit");
					String loc = fru.getString("pos");
					String[] point = loc.split(",");
					double x = Double.parseDouble(point[0]);
					double y = Double.parseDouble(point[1]);
					double z = Double.parseDouble(point[2]);
					Point3D p = new Point3D(x,y,z);
					double value = fru.getDouble("value");
					int type = fru.getInt("type");
					myFruits f = new myFruits(value,type,p);
					this.fruits.add(f);
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
}
	
    private void drawFruits()
	{
	for (int i=0;i<fruits.size();i++)
	{
			myFruits fruit=fruits.get(i);
			boolean type=fruit.whichfruit;
			Point3D pos=fruit.pos;
			if(type==false)
				StdDraw.picture(pos.x(), pos.y(),"apple.png", 0.0004, 0.0004);
			else
				StdDraw.picture(pos.x(), pos.y(),"banana.png", 0.0004, 0.0004);
	}
	}
	
    public void drawRobot()
	{
		for (int i=0;i<this.robots.size();i++)
		{
				Robot robot=this.robots.get(i);
				Point3D pos=robot.pos;
				StdDraw.picture(pos.x(), pos.y(),"robot.png", 0.0009, 0.0007);
		}
	}
		
	private static int nextNode(graph g, int src) {
		int ans = -1;
		Collection<edge_data> ee = g.getE(src);
		Iterator<edge_data> itr = ee.iterator();
		int s = ee.size();
		int r = (int)(Math.random()*s);
		int i=0;
		while(i<r) {itr.next();i++;}
		ans = itr.next().getDest();
		return ans;
	}
	
	private long timeToEnd() {
		return this.game.timeToEnd()/1000;
	}
	
	private long start() {
		return this.game.startGame();
	}
	
	private long stop() {
		return this.game.stopGame();
	}
	
	private boolean isRunning() {
		return game.isRunning();
	}
	
	private String gameResults(ArrayList<Robot> robots){
		String ans ="";
		for(int i=0;i<robots.size();i++) {
			ans=ans+"{Robot:"+i+"'s with:"+robots.get(i).coins + " coins } ";
		}
		return ans;
	}
	public static void main(String[] args) 
	{
		game_service game = Game_Server.getServer(9); // this is where we get the user input too know what game to play [0,23];
		String g = game.getGraph(); // graph as string.
		DGraph gg = new DGraph();
		gg.init(g);
		game.addRobot(1);
		game.addRobot(3);
		game.addRobot(5);
		MyGameGUI my=new MyGameGUI(game,gg);
		System.out.println(game.getRobots().size());
		//my.game.startGame();	

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}