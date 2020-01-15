package gameClient;


import java.util.Iterator;

import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import utils.Point3D;

public class myFruits {
	double value;
	Point3D pos;
	boolean whichfruit;
	
	
	public myFruits(double value, int type, Point3D pos) {
		this.value=value;
		this.pos=pos;
		if (type==-1)
		{
		this.whichfruit=true; //banana
		}
		else
		{
			this.whichfruit=false; //apple
		}
	     
	}
	public myFruits() {
		
	}

	public edge_data edge(graph g)
	{
		double min=Double.POSITIVE_INFINITY;
		edge_data efinal=null;
		for(Iterator<node_data> verIter=g.getV().iterator();verIter.hasNext();)
		{ 
			int v=verIter.next().getKey();
			try 
			{
				for(Iterator<edge_data> edgeIter=g.getE(v).iterator();edgeIter.hasNext();)
				{
					edge_data e=edgeIter.next();
					double dis1=this.pos.distance2D(g.getNode(e.getSrc()).getLocation());
					double dis2=this.pos.distance2D(g.getNode(e.getDest()).getLocation());
					double dis3=g.getNode(e.getSrc()).getLocation().distance2D(g.getNode(e.getDest()).getLocation());
					if(Math.abs((dis1+dis2)-dis3)<=min)
					{
						min=Math.abs((dis1+dis2)-dis3);
						efinal=e;
					}
				}
			}
			catch(NullPointerException e)
			{}
		}
		if(efinal.getSrc()-efinal.getDest()>0&&this.whichfruit==false)
			return efinal;
		if(efinal.getSrc()-efinal.getDest()<0&&this.whichfruit!=true)
			return efinal;
		efinal=g.getEdge(efinal.getDest(), efinal.getSrc());		
		return efinal;
	}
}