package gameClient;

import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import dataStructure.edge_data;
import dataStructure.node_data;

public class MyManageGame 
{
   private  MyGameGUI my;
   
   public MyManageGame (MyGameGUI m)
   {
	   this.my=m;
   }
   
	public void Auto()
	{
		String info = this.my.getGame().toString();
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

		int nv=this.my.getG().nodeSize();
		int nodes[]=new int[nv];
		int j=0;
		for(Iterator<node_data> v=this.my.getG().getV().iterator();v.hasNext();) 
		{
			int p=v.next().getKey();
			nodes[j]=p;
			j++;
		}
		while(rs>0)
		{
			int random=(int)(Math.random()*nodes.length);
			this.my.getGame().addRobot(nodes[random]);
			rs--;
		}
		my.addRobots(this.my.getGame());
	}
	
	public int nextNode(int src) 
	{
		double min=Double.POSITIVE_INFINITY;
		int dest=0;int key=0;
		boolean isGetDest=false;
		for(int i=0;i<this.my.getFruits().size();i++)
		{
			if(this.my.getFruits().get(i).edge(this.my.getG()).getTag()==0&&this.my.getGa().shortestPathDist(src, this.my.getFruits().get(i).edge(this.my.getG()).getSrc())<min)
			{
					key=i;
				 	isGetDest=true;
					min=this.my.getGa().shortestPathDist(src,this.my.getFruits().get(i).edge(this.my.getG()).getSrc());
					if(src==this.my.getFruits().get(i).edge(this.my.getG()).getSrc()) 
					{
						dest=this.my.getFruits().get(i).edge(this.my.getG()).getDest();
					}
					else
						dest=this.my.getFruits().get(i).edge(this.my.getG()).getSrc();
			}
		}
		if(!isGetDest)
		{
			System.out.println("is get dest");
			if(src==this.my.getFruits().get(0).edge(this.my.getG()).getSrc()) 
			{
				dest=this.my.getFruits().get(0).edge(this.my.getG()).getDest();
			}
			else
				dest=this.my.getFruits().get(0).edge(this.my.getG()).getSrc();
		}
		
		this.my.getFruits().get(key).edge(this.my.getG()).setTag(1);
		this.my.getG().getEdge(this.my.getFruits().get(key).edge(this.my.getG()).getDest(), this.my.getFruits().get(key).edge(this.my.getG()).getSrc()).setTag(1);
		List<node_data> node=this.my.getGa().shortestPath(src, dest);
		return node.get(1).getKey();
	}
	
	public  void moveRobotsAuto()
	{
		List<String> log = this.my.getGame().move();
		if(log!=null) {
			long t = this.my.getGame().timeToEnd();
			for(int i=0;i<log.size();i++)
			{
				try {
					int rid=this.my.getRobots().get(i).id;
					int src=this.my.getRobots().get(i).src;
					int dest=this.my.getRobots().get(i).dest;
					if(dest==-1) 
					{	
						dest = nextNode(src);
						this.my.getGame().chooseNextEdge(rid, dest);
						System.out.println("Turn to node: "+dest+"  time to end:"+(t/1000));
						System.out.println((new JSONObject(log.get(i))).getJSONObject("Robot"));
					}
				} 
				catch (JSONException e) {e.printStackTrace();}
			}
		}
		for(Iterator<node_data> verIter=this.my.getG().getV().iterator(); verIter.hasNext();)
		{
			int src=verIter.next().getKey();
			try 
			{
				for(Iterator<edge_data> edgeIter=this.my.getG().getE(src).iterator();edgeIter.hasNext();)
				{
					edgeIter.next().setTag(0);
				}	
			}
			catch(NullPointerException e)
			{}
		}
		this.my.getGa().init(this.my.getG());
	}
}
