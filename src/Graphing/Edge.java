package Graphing;

/**
 * Created by DragonFire168 on 11/18/15.
 */
public class Edge {
    public int v1;
    public int v2;
    public int weight;

    public Edge(int v1, int v2, int weight) {
        this.v1 = v1;
        this.v2 = v2;
        this.weight = weight;
    }

    public static int sumEdges(Iterable<Edge> edges) {
        int ret = 0;
        for (Edge e:edges) {
            ret += Math.abs(e.weight);
        }
        return ret;
    }
}
