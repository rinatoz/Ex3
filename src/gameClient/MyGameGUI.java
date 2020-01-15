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
import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import utils.Point3D;
import utils.StdDraw;

public class MyGameGUI  implements Runnable  
{
	public static double EPS=0.001;
	private game_service game;
	private DGraph g;
	private Graph_Algo ga;
	private ArrayList<Robot> robots;
	private ArrayList<myFruits> fruits;	
    private double maxX=Double.NEGATIVE_INFINITY; //check if to put them inside the func
	private double maxY=Double.NEGATIVE_INFINITY;
	private double minX=Double.POSITIVE_INFINITY;
	private double minY=Double.POSITIVE_INFINITY;
	private int scenario; //0-23
	private String type;

	public MyGameGUI()
		{
			StdDraw.enableDoubleBuffering();
             this.robots=new ArrayList<Robot>();
             this.fruits=new ArrayList<myFruits>();
         	 Object s[]=new Object[24];
    		 for(int i=0;i<s.length;i++)
    			s[i]=i;
             this.scenario=(Integer)JOptionPane.showInputDialog(null,"Choose a scenario between 0-23","scenario", JOptionPane.QUESTION_MESSAGE,null,s,null);
             this.game=Game_Server.getServer(scenario);
             String graph=game.getGraph();
             this.g=new DGraph();
             this.g.init(graph);
             this.ga=new Graph_Algo();
             ga.init(this.g);
             limit();
             drawS();
             initGUI();
         	String t[]=new String[2];
         	 t[0]="automatic";
         	 t[1]="mouse";
    		 this.type=(String)JOptionPane.showInputDialog(null,"play automatic/by mouse","choose type", JOptionPane.QUESTION_MESSAGE,null,t,null);
    		 if (type=="mouse")
    			 Mouse();
    		 else
    			 Auto();
    		 drawRobot();    		
		}
	
    public void initGUI()
	{	
    	StdDraw.clear();
    	drawS();
		fruits.clear();
		addFruit(game);
		drawFruits();
		robots.clear();
		addRobots(game);
		drawRobot();
		StdDraw.show();
	}
    
    public void limit()
    {

		for(Iterator<node_data> verticles=g.getV().iterator(); verticles.hasNext();)
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
				
				long time=game.timeToEnd();
				StdDraw.setPenColor(Color.BLACK);
				StdDraw.setPenRadius(0.020);
				String s="";
                
				StdDraw.text(minX+(maxX-minX)*0.85,minY+(maxY-minY)*0.95,"time to end: " +time/1000);
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
		
    public void Mouse()
	{
		String info = game.toString();
		JSONObject line;
		int rs=0;
		try {
			line = new JSONObject(info);
			JSONObject ttt = line.getJSONObject("GameServer");
			rs = ttt.getInt("robots");
		}
		catch (JSONException e) {
			e.printStackTrace();
			}
		int nodessize=g.nodeSize();
		Object nodes[]=new Object[nodessize];
		int j=0;
		for(Iterator<node_data> v=g.getV().iterator();v.hasNext();) 
		{
			int point=v.next().getKey();
			nodes[j]=point;
			j++;
		}
		int i=0;
		while(i<rs)
		{
			int v=(Integer)JOptionPane.showInputDialog(null,"start place to"+ i +" robot number","add robot",JOptionPane.QUESTION_MESSAGE,null,nodes,null);
			game.addRobot(v);
			i++;
		}
		addRobots(game);
	}

	private void Auto()
	{
		String info = this.game.toString();
		JSONObject line;
		int rs=0;
		try {
			line = new JSONObject(info);
			JSONObject ttt = line.getJSONObject("GameServer");
			rs = ttt.getInt("robots");
		}
		catch (JSONException e) {
			e.printStackTrace();
			}

		int nv=g.nodeSize();
		int nodes[]=new int[nv];
		int j=0;
		for(Iterator<node_data> v=g.getV().iterator();v.hasNext();) 
		{
			int p=v.next().getKey();
			nodes[j]=p;
			j++;
		}
		while(rs>0)
		{
			int random=(int)(Math.random()*nodes.length);
			game.addRobot(nodes[random]);
			rs--;
		}
		addRobots(game);
	}
	
	//////////////////////////////////////////////////////////////////	
	
	public static void main(String[] args) 
	{
		MyGameGUI my=new MyGameGUI();
	    my.run();
	}
	
	private int nextNode(int src) 
	{
		double min=Double.POSITIVE_INFINITY;
		int dest=0;int key=0;
		boolean isGetDest=false;
		for(int i=0;i<fruits.size();i++)
		{
			if(fruits.get(i).edge(g).getTag()==0&&this.ga.shortestPathDist(src, fruits.get(i).edge(g).getSrc())<min)
			{
					key=i;
				 	isGetDest=true;
					min=ga.shortestPathDist(src, fruits.get(i).edge(g).getSrc());
					if(src==fruits.get(i).edge(g).getSrc()) 
					{
						dest=fruits.get(i).edge(g).getDest();
					}
					else
						dest=fruits.get(i).edge(g).getSrc();
			}
		}
		if(!isGetDest)
		{
			System.out.println("is get dest");
			if(src==fruits.get(0).edge(g).getSrc()) 
			{
				dest=fruits.get(0).edge(g).getDest();
			}
			else
				dest=fruits.get(0).edge(g).getSrc();
		}
		
		fruits.get(key).edge(g).setTag(1);
		g.getEdge(fruits.get(key).edge(g).getDest(), fruits.get(key).edge(g).getSrc()).setTag(1);
		List<node_data> node=this.ga.shortestPath(src, dest);
		return node.get(1).getKey();
	}

	private  void moveRobots()
	{
		List<String> log = game.move();
		if(log!=null) {
			long t = game.timeToEnd();
			for(int i=0;i<log.size();i++)
			{
				try {
					int rid=robots.get(i).id;
					int src=robots.get(i).src;
					int dest=robots.get(i).dest;
					if(dest==-1) 
					{	
						dest = nextNode(src);
						game.chooseNextEdge(rid, dest);
						System.out.println("Turn to node: "+dest+"  time to end:"+(t/1000));
						System.out.println((new JSONObject(log.get(i))).getJSONObject("Robot"));
					}
				} 
				catch (JSONException e) {e.printStackTrace();}
			}
		}
		for(Iterator<node_data> verIter=g.getV().iterator(); verIter.hasNext();)
		{
			int src=verIter.next().getKey();
			try 
			{
				for(Iterator<edge_data> edgeIter=g.getE(src).iterator();edgeIter.hasNext();)
				{
					edgeIter.next().setTag(0);
				}	
			}
			catch(NullPointerException e)
			{}
		}
		this.ga.init(g);
	}

	@Override
	public void run() 
	{
		drawS();
		game.startGame();
		int index=0;
		while(game.isRunning())
		{
				synchronized(this) 
				{
					if (type.equals("automatic"))
					   moveRobots();
					if(index%4==0)
					{
						initGUI();
					}
					     
					index++;
				}
		}		
		String results = game.toString();
		System.out.println("Game Over: "+results);
	}




}