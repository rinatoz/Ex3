package dataStructure;
import java.util.Collection;
/**
 * This interface represents a directional weighted graph.
 * The interface has a road-system or communication network in mind - and should support a large number of nodes (over 100,000).
 * The implementation should be based on an efficient compact representation (should NOT be based on a n*n matrix).
 *
 */


public interface graph
{
    node_data getNode(final int p0);
    
    edge_data getEdge(final int p0, final int p1);
    
    void addNode(final node_data p0);
    
    void connect(final int p0, final int p1, final double p2);
    
    Collection<node_data> getV();
    
    Collection<edge_data> getE(final int p0);
    
    node_data removeNode(final int p0);
    
    edge_data removeEdge(final int p0, final int p1);
    
    int nodeSize();
    
    int edgeSize();
    
    int getMC();
}
