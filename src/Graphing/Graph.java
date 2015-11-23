package Graphing;

/**
 * Created by DragonFire168 on 11/18/15.
 */

import java.util.Set;

/**
 * A graph for a scheduling network, allows for the retrieval of vertices and weights from the network.
 */
public interface Graph {

    /**
     * Gets the weight of an edge, if the returned weight is positive, the direction is from v1 to v2. If it is negative,
     * than it is from v2 to v1. Throws an IllegalStateException if the graph has not been compiled yet.
     * @param v1 The value of the first vertex
     * @param v2 The value of the second vertex
     * @return The weight of the edge
     */
    int weight(int v1, int v2);

    /**
     * Gets a vertex object from the graph, throws an IllegalStateException if the graph has not been compiled yet.
     * @param v1 The value of the vertex to retrieve
     * @return The vertex object
     */
    Vertex getVertex(int v1);

    /**
     * Adds an edge to the graph. If called after the graph is compiled, throws an illegal state exception.
     *
     * @param e An edge to add to the graph
     */
    void addEdge(Edge e);

    /**
     * Compiles the list of already provided edges into a usable form
     */
    void compile();

    /**
     * Returns an array of the values of the vertices that are along the critical path. Not available until the graph
     * has been compiled.
     * @return The vertices along the critical path from start to finish
     */
    int[] getCriticalPath();

    /**
     * Returns a set of the values to the vertices that are along the critical path.
     *
     * @return Set of vertices along the critical path
     */
    Set<Integer> getCriticalSet();

    /**
     * The minimum amount of time(weight) it will take to complete the network from start to finish.
     *
     * @return Minimum completion weight
     */
    int minCompletionWeight();
}
