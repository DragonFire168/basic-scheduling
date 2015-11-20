package Graphing;

/**
 * Created by DragonFire168 on 11/18/15.
 */
public interface Graph {

    int Weight(int v1, int v2);

    Vertex getVertex(int v1);

    void addEdge(Edge e);

    void compile();

    int[] getCriticalPath();

    int minCompletionWeight();
}
