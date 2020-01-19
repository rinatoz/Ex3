package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dataStructure.DGraph;
import dataStructure.edgeData;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.nodeData;
import dataStructure.node_data;
import utils.Point3D;

public class test_DGraph {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	@Test
	public void Constructor_test_DGraph() 
	{
		graph a=new DGraph(); 
		assertTrue(a.getV().isEmpty());		
	}
	
	@Test
	public void consturctor_test_DGraph_col_col()
	{
		Collection<node_data> nodes=new ArrayList<>(4);
		Point3D p1 = new Point3D(22, 22, 22);
		Point3D p2 = new Point3D(1, 1, 0);
		Point3D p3 = new Point3D(1, 4, 0);
		nodeData n1 = new nodeData(p1,5,"",0);
		nodeData n2 = new nodeData(p2,5,"", 0);
		nodeData n3 = new nodeData(p3,5,"", 0);
		nodes.add(n1);
		nodes.add(n2);
		nodes.add(n3);
		Collection<edge_data> edges=new ArrayList<>();
		graph a=new DGraph(nodes,edges);
        assertTrue(a.getV().size()==3);	
        assertTrue(a.edgeSize()==0);	
	}
	
	@Test
	public void getNode_test ()
	{
		Point3D p1 = new Point3D(22, 22, 22);
		nodeData n1 = new nodeData(p1,5,"",0);
		graph a=new DGraph();
		a.addNode(n1);
		assertEquals(a.getNode(n1.getKey()).getLocation(),n1.getLocation());
		assertEquals(a.getNode(n1.getKey()).getInfo().toString(),n1.getInfo().toString());
		assertEquals(a.getNode(n1.getKey()).getTag(),n1.getTag());
	}
	
	@Test
	public void getEdge_test ()
	{
		Collection<node_data> nodes=new ArrayList<>(4);
		Point3D p1 = new Point3D(22, 22, 22);
		Point3D p2 = new Point3D(1, 1, 0);
		Point3D p3 = new Point3D(1, 4, 0);
		nodeData n1 = new nodeData(p1,5,"",0);
		nodeData n2 = new nodeData(p2,5,"", 0);
		nodeData n3 = new nodeData(p3,5,"", 0);
		nodes.add(n1);
		nodes.add(n2);
		nodes.add(n3);
		Collection<edge_data> edges=new ArrayList<>();
		edge_data edg=new edgeData(n1.getKey(),n2.getKey(),2.2);
		edges.add(edg);
		graph a=new DGraph(nodes,edges);
		assertEquals(a.getEdge(n1.getKey(),n2.getKey()).toString(),edg.toString());
	}
	
	@Test
	public void addnode_test_DGraph() 
	{
		double weight=5;
		String i="info";
		int t=0;
		Point3D location=new Point3D(1,1,1);
		node_data node=new nodeData(location,weight,i,t);
		int key=node.getKey();
		graph a=new DGraph(); 
		a.addNode(node);
		assertTrue(a.getNode(key).toString().equals(node.toString()));	
	}
	
	@Test
	public void connect_test_DGraph() 
	{
		Point3D p1 = new Point3D(22, 22, 22);
		Point3D p2 = new Point3D(1, 1, 0);
		Point3D p3 = new Point3D(1, 4, 0);

		nodeData n1 = new nodeData(p1,5,"",0);
		nodeData n2 = new nodeData(p2,5,"", 0);
		nodeData n3 = new nodeData(p3,5,"", 0);
		graph g = new DGraph();
		g.addNode(n1);
		g.addNode(n2);
		g.addNode(n3);
		g.connect(n1.getKey(), n2.getKey(), 2);
		g.connect(n2.getKey(), n3.getKey(), 3);
		if (g.edgeSize()!=2) { fail(); }
		if (g.getEdge(n1.getKey(),n2.getKey()).getWeight()!=2) { fail(); }
		if (g.getEdge(n2.getKey(),n3.getKey()).getWeight()!=3) { fail(); }
	}
	
	@Test
	public void test_removeNode()
	{
		Point3D p1 = new Point3D(2, 2, 0);
		Point3D p2 = new Point3D(1, 1, 0);
		Point3D p3 = new Point3D(2, 2, 2);
		nodeData n1 = new nodeData(p1,5,"",0);
		nodeData n2 = new nodeData(p2,5,"", 0);
		nodeData n3 = new nodeData(p3,5,"", 0);
		graph g = new DGraph();
		g.addNode(n1);
		g.addNode(n2);
		g.addNode(n3);
		g.connect(n1.getKey(), n2.getKey(), 2);
		g.connect(n2.getKey(), n3.getKey(), 3);
		g.removeNode(n2.getKey());
		if (g.edgeSize()!=0) { fail(); }
		try {
			g.getEdge(n2.getKey(), n3.getKey());
			fail();
		}
		catch (Exception e) {}
		try {
			g.getEdge(n1.getKey(), n2.getKey());
		}
		catch (Exception e) {}
}
	
	@Test
	public void testRemoveEdge() {
		Point3D p1 = new Point3D(2, 2, 0);
		Point3D p2 = new Point3D(1, 1, 0);
		Point3D p3 = new Point3D(2, 2, 2);
		nodeData n1 = new nodeData(p1,5,"",0);
		nodeData n2 = new nodeData(p2,5,"", 0);
		nodeData n3 = new nodeData(p3,5,"", 0);
		graph g = new DGraph();
		g.addNode(n1);
		g.addNode(n2);
		g.addNode(n3);
		g.connect(n1.getKey(), n2.getKey(), 2);
		g.connect(n2.getKey(), n3.getKey(), 3);
		
		g.removeEdge(n2.getKey(), n3.getKey());
		if (g.edgeSize()!=1) { fail(); }
		try {
			g.getEdge(n2.getKey(), n3.getKey());
		}
		catch (Exception e) {}
		try {
			g.getEdge(n1.getKey(), n2.getKey());
		}
		catch (Exception e) {fail();}
	}
}
