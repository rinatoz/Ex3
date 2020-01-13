package algorithms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Pattern;
import dataStructure.DGraph;
import dataStructure.edgeData;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.nodeData;
import dataStructure.node_data;
import utils.Point3D;
/**
 * This empty class represents the set of graph-theory algorithms
 * which should be implemented as part of Ex2 - Do edit this class.
 * @author 
 *
 */
public class Graph_Algo implements graph_algorithms{

	public graph g;
		
	public Graph_Algo()  
	{
		this.g=new DGraph();
	}
	public Graph_Algo(graph g)  
	{
		this.g=g;
	}
	
	@Override
	public void init(graph g) 
	{
	this.g=g;	
	}
	
	@Override
	public void init(String file_name) 
	{
	       String line = "";
	       int i=0;
	        try 
	        {
	        	BufferedReader br = new BufferedReader(new FileReader(file_name));
	        	line=br.readLine();
	        	if (line.toString().equals("[nodes:(not nodes yet)edges:(not edges yet)]")) //empty graph
	        	{
	        		this.g=new DGraph();
	        	}
	        	else
	        	{
	            String[] afterread = line.split("]"); //graph
	            
	            while (i<afterread.length)
	            {
	             afterread[i]= afterread[i].substring(8); 
	             String[] two =afterread[i].split(Pattern.quote("edges:("));
	             String[] nodes =two[0].split(Pattern.quote("("));
	             DGraph e=new DGraph();
	             for (int k=0;k<nodes.length;k++)
	             {
	            	 e.addNode(readS(nodes[k]));
	             }
                try
                 {
	             String[] edges =two[1].split(Pattern.quote("("));
	             for (int k1=0;k1<edges.length;k1++)
	             {
	            	 edgeData ed=readSt(edges[k1]);
	                 e.connect(ed.getSrc(), ed.getDest(), ed.getWeight());	            	 
	             }
	             }
	            catch(Exception E) {}
	             this.g=e;
	             
                i++;
	            }	
	        	}
	            br.close();
	          
	        } 
	        catch (Exception e) 
	        {
	            e.printStackTrace();
	            throw new RuntimeException ("couldnt read a file");
	        }
	}

	@Override
	public void save(String file_name) //V
	{
		try {
			File f=new File(file_name);
			PrintWriter pw = new PrintWriter(f);
			String intoFile="";
	        intoFile=intoFile+this.g.toString();
			pw.write(intoFile.toString());
			pw.close();
		}
		catch (Exception e) 
		{
			throw new RuntimeException("couldn't save the collection");
		}	
		
	}

	@Override
	public boolean isConnected()
	{
		Collection<node_data> col = g.getV();
		Iterator<node_data> it = col.iterator();
		while(it.hasNext()) {
			node_data n1 = it.next();
			Collection<node_data> col2 = g.getV();
			Iterator<node_data> it2 = col2.iterator();
			while(it2.hasNext()) {
				node_data n2 = it2.next();
				try
				{
				shortestPathDist(n1.getKey(), n2.getKey());
				}
				catch (Exception e)
				{
					return false;
				}

				}
			}
		return true;
	}

	@Override
	public double shortestPathDist(int src, int dest)
	{
		try
		{
		   node_data d=this.g.getNode(dest);
			Collection<node_data> vertices = this.g.getV(); // collection of nodes
			Queue<node_data> q = new LinkedList<>();
			for (node_data node : vertices) 
			{
				if (node.getKey() == src)
				{ // start point of path
					node.setWeight(0);     // weight of source is 0.
				} 
				else 
				{
					node.setWeight(Double.POSITIVE_INFINITY); // note infinity the other nodes
				}
               q.add(node);
			}

			while (!q.isEmpty()) 
			{	
				q=sorter(q);
				node_data tmp_src =q.poll();
				try {
				Collection<edge_data> edgesTmp = this.g.getE(tmp_src.getKey());
					for (edge_data edge : edgesTmp) 
					{
						node_data tmp_dst = this.g.getNode(edge.getDest());
							double tmp_w = tmp_src.getWeight() + edge.getWeight();
							if (tmp_dst.getWeight() > tmp_w)
							{
								tmp_dst.setWeight(tmp_w);
								tmp_dst.setInfo(""+tmp_src.getKey());
							}				
					}
				}
				catch (Exception e) {}
			}
			if (d.getWeight()==Double.POSITIVE_INFINITY)
				throw new RuntimeException ("there is no path to this dest from this src");
				
			return d.getWeight();
		}
		catch (Exception e)
		{
			throw new RuntimeException ("there is no path to this dest from this src");
		}


	}

//	@Override
	public List<node_data> shortestPath(int src, int dest) //V
	{
		shortestPathDist(src,dest);
		ArrayList<node_data> first=new ArrayList<>();
		ArrayList<node_data> sorted=new ArrayList<>();
		first.add(this.g.getNode(dest));
		int t=dest;
		while(this.g.getNode(t).getKey()!=src)
		{
			if(this.g.getNode(t).getInfo().equals(""))
				return null;
			int next=Integer.parseInt(this.g.getNode(t).getInfo());
			first.add(this.g.getNode(next));
			t=next;
		}
		
		for (int i=first.size()-1;i>=0;i--)
		{
			sorted.add(first.get(i));
		}

		return sorted;
	}
    
	@Override
	public List<node_data> TSP(List<Integer> targets) 
	{
		if(!this.isConnected())
			return null;
		List<node_data> TSP=new ArrayList<node_data>();
		int indexSrc=(int)(Math.random()*targets.size());//start from random vertex in the list
		int src=targets.get(indexSrc);
		targets.remove(indexSrc);
		int dest=0,indexDest=0;
		TSP.add(g.getNode(src));
		while(targets.size()>0) 
		{
			double min=Double.POSITIVE_INFINITY;
			for(int j=0;j<targets.size();j++) //find the vertex that is the closest to src
			{
				if(this.shortestPathDist(src, targets.get(j))<min) 
				{
					min=this.shortestPathDist(src, targets.get(j));
					dest=targets.get(j);
					indexDest=j;
				}
			}
			List<node_data> temp=this.shortestPath(src, dest);
			for(int j=1;j<temp.size();j++) //add the vertices from i to i+1 to the list
			{
				TSP.add(temp.get(j));
			}
			src=dest;
			targets.remove(indexDest);
		}
		return TSP;
	
	}

	@Override
	public graph copy()   
	{
		Collection<node_data> nodes=this.g.getV();
		Collection<edge_data> edges=new ArrayList<>();
		Iterator<node_data> itr=nodes.iterator();
		while(itr.hasNext())
		{
			try
			{
			edges.addAll(this.g.getE(itr.next().getKey()));
			}
			catch (Exception e) {}
		}
		graph g=new DGraph(nodes, edges);
		return g;
	}
	
	///////////////////////out helpful functions////////////////////////////////
	public nodeData readS(String s)
	{
		Point3D p;
	//	s=s.substring(1);
		s=s.replaceAll("key:","");
		s=s.replaceAll("location:","");
		s=s.replaceAll("info:","");
		s=s.replaceAll("weight:","");
		s=s.replaceAll("tag:","");
		String[] s1=s.split(",");
		int key=Integer.parseInt(s1[0]);
		nodeData d=new nodeData(key);
		double x= Double.parseDouble(s1[1]);
		double y= Double.parseDouble(s1[2]);
		double z= Double.parseDouble(s1[3]);
		p=new Point3D(x,y,z);
		d.setLocation(p);
		double weight=Double.parseDouble(s1[4]);
		d.setWeight(weight);
		d.setInfo(s1[5]);
		s1[6]=s1[6].replace(")", "");
		int tag=Integer.parseInt(s1[6]);
        d.setTag(tag);
		return d;
		
	}

	public edgeData readSt(String s)
	{
		edgeData d;
         try
        {
		s=s.replaceAll("src:","");
		s=s.replaceAll("dest:","");
		s=s.replaceAll("weight:","");
		s=s.replaceAll("info:","");
		s=s.replaceAll("tag:","");
		String[] s1=s.split(",");
	    int src=Integer.parseInt(s1[0]);
	    int dest=Integer.parseInt(s1[1]);
	    double weight=Double.parseDouble(s1[2]);
	    d=new edgeData(src,dest,weight);
		d.setInfo(s1[3]);
		s1[4]=s1[4].replaceAll("\\)","");
		int tag=Integer.parseInt(s1[4]);
        d.setTag(tag);
		return d;
		}
      catch (Exception e) 
      {
    	  throw new RuntimeException ("cant read edge's list");
      }
	}
	
    public String toString()
	{
		String s="";
        
		s=s+this.g.toString();
		return s;
	} 
	
    public Queue<node_data> sorter (Queue<node_data> q)
	{
      Queue<node_data> q1 = new LinkedList<node_data>();
      Queue<node_data> q2 = new LinkedList<node_data>();
		    while (!q.isEmpty())
		         q1.add(q.remove());
		          while (!q1.isEmpty())
		          {
		             node_data next = q1.remove();
		              while (!q2.isEmpty() && next.getWeight() < q2.peek().getWeight())
		                  if (next.getWeight() < q2.peek().getWeight()){
		                   q1.add(q2.remove());}
		                   q2.add(next);
		            }

		        return q2;
	}



}
