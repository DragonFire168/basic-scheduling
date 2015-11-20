package Graphing;

import java.util.Comparator;

/**
 * Created by DragonFire on 11/20/15.
 */

/**
 * Determines largest edge by largest value vertex.
 */
public class LargestVertexEdgeComparator implements Comparator<Edge> {
    @Override
    public int compare(Edge e, Edge e1) {
        int maxe = e.v1 > e.v2?e.v1:e.v2;
        int maxe1 = e1.v1 > e.v2?e1.v1:e1.v2;
        return maxe - maxe1;
    }
}
