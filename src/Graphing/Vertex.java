package Graphing;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by DragonFire168 on 11/18/15.
 */
public class Vertex {

    protected Graph graph;
    protected int value;
    protected Set<Integer> backNodes, frontNodes;

    /**
     * A graph vertex
     * @param v The value of the vertex
     * @param graph The graph this vertex belongs too
     * @param bV The set of values of the vertices that are backwards from this one
     * @param fV The set of values of the vertices that are forwards from this one
     */
    public Vertex(int v, Graph graph, Set<Integer> bV, Set<Integer> fV) {
        value = v;
        this.graph = graph;
        backNodes = bV;
        frontNodes = fV;
    }

    public void getMaxPathToStart(ArrayList<Edge> path) {
        if (backNodes.isEmpty()) {return;}

        ArrayList<Edge> currentPath = null;
        int currentWeight = 0;
        for (Integer v:backNodes) {
            ArrayList<Edge> newPath = new ArrayList<>();
            Edge e = new Edge(v, this.value, graph.Weight(v, value));
            newPath.add(e);
            Vertex vert = graph.getVertex(v);
            vert.getMaxPathToStart(newPath);
            int w = Edge.sumEdges(newPath);
            if (w>currentWeight) {
                currentWeight = w;
                currentPath = newPath;
            }
        }

        path.addAll(currentPath);
    }

    public void getMaxPathToFinish(ArrayList<Edge> path) {
        if (frontNodes.isEmpty()) {return;}

        ArrayList<Edge> currentPath = null;
        int currentWeight = 0;
        for (Integer v:frontNodes) {
            ArrayList<Edge> newPath = new ArrayList<>();
            Edge e = new Edge(this.value, v, graph.Weight(value, v));
            newPath.add(e);
            Vertex vert = graph.getVertex(v);
            vert.getMaxPathToFinish(newPath);
            int w = Edge.sumEdges(newPath);
            if (w>currentWeight) {
                currentWeight = w;
                currentPath = newPath;
            }
        }

        path.addAll(currentPath);
    }

    public int arrival() {
        ArrayList<Edge> path = new ArrayList<>();
        getMaxPathToStart(path);
        return Edge.sumEdges(path);
    }

    public int mustLeave() {
        ArrayList<Edge> path = new ArrayList<>();
        getMaxPathToFinish(path);
        int finish = Edge.sumEdges(path);
        return graph.minCompletionWeight() - finish;
    }

}
