package Graphing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    public List<Edge> getMaxPathToStart() {
        ArrayList<Edge> ret = new ArrayList<>();

        getMaxPathToStartHelper(ret, new HashSet<Integer>());
        return ret;
    }

    private void getMaxPathToStartHelper(ArrayList<Edge> path, HashSet<Integer> visited) {
        if (backNodes.isEmpty()) {return;}

        ArrayList<Edge> currentPath = null;
        int currentWeight = 0;
        visited.add(this.value);
        for (Integer v:backNodes) {
            if (visited.contains(v)) {
                throw new CycleException("A cycle was detected when traversing from v" + value + " to v" + v);
            }
            ArrayList<Edge> newPath = new ArrayList<>();
            Edge e = new Edge(v, this.value, graph.weight(v, value));
            newPath.add(e);
            Vertex vert = graph.getVertex(v);
            //noinspection unchecked
            vert.getMaxPathToStartHelper(newPath, (HashSet<Integer>) visited.clone());
            int w = Edge.sumEdges(newPath);
            if (w>currentWeight) {
                currentWeight = w;
                currentPath = newPath;
            }
        }

        path.addAll(currentPath);
    }

    public List<Edge> getMaxPathToFinish() {
        ArrayList<Edge> ret = new ArrayList<>();
        getMaxPathToFinishHelper(ret, new HashSet<Integer>());
        return ret;
    }

    private void getMaxPathToFinishHelper(ArrayList<Edge> path, HashSet<Integer> visited) {
        if (frontNodes.isEmpty()) {return;}

        ArrayList<Edge> currentPath = null;
        int currentWeight = 0;

        visited.add(value);
        for (Integer v:frontNodes) {
            if (visited.contains(v)) {
                throw new CycleException("A cycle was detected when traversing from v" + value + " to v" + v);
            }
            ArrayList<Edge> newPath = new ArrayList<>();
            Edge e = new Edge(this.value, v, graph.weight(value, v));
            newPath.add(e);
            Vertex vert = graph.getVertex(v);
            //noinspection unchecked
            vert.getMaxPathToFinishHelper(newPath, (HashSet<Integer>) visited.clone());
            int w = Edge.sumEdges(newPath);
            if (w>currentWeight) {
                currentWeight = w;
                currentPath = newPath;
            }
        }

        path.addAll(currentPath);
    }

    public int arrival() {
        return Edge.sumEdges(getMaxPathToStart());
    }

    public int mustLeave() {
        int finish = Edge.sumEdges(getMaxPathToFinish());
        return graph.minCompletionWeight() - finish;
    }

}
