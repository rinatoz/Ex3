package tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dataStructure.edgeData;
import dataStructure.edge_data;
import dataStructure.nodeData;
import dataStructure.node_data;
import utils.Point3D;

public class test_node_and_edge {

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
	public void Constructor_test_nodeData() 
	{
		Point3D location=new Point3D(1,1,1);
		double weight=5;
		String i="info";
		int t=0;
		
		node_data a=new nodeData(location,weight,i,t);
		
		assertEquals(a.getWeight(),weight,0.0001);
		assertTrue(a.getLocation().equals(location));
		assertTrue(a.getInfo().equals(i));
		assertEquals(a.getTag(),t);	
	}
	@Test
	public void Constructor_test_edgeData() 
	{
		int src=1;
		int dest=2;
		double weight=5;
		String i="info";
		int t=0;
		
		edge_data a=new edgeData(src,dest,weight,i,t);
		assertEquals(a.getSrc(),src);
		assertEquals(a.getDest(),dest);
		assertEquals(a.getWeight(),weight,0.0001);
		assertTrue(a.getInfo().equals(i));
		assertEquals(a.getTag(),t);	
	}

}
