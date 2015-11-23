package Graphing;

import java.util.*;

/**
 * Created by DragonFire168 on 11/18/15.
 */
public class Vertex {

    protected Graph graph;
    protected int value;
    protected Set<Integer> backNodes, frontNodes;
    protected HashMap<String, Integer> cache;

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
        cache = new HashMap<>(4);
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
        if (!cache.containsKey("arrive")) {
            cache.put("arrive", Edge.sumEdges(getMaxPathToStart()));
        }
        return cache.get("arrive");
    }

    public int mustLeave() {
        if (!cache.containsKey("leave")) {
            int finish = Edge.sumEdges(getMaxPathToFinish());
            cache.put("leave", graph.minCompletionWeight() - finish);
        }

        return cache.get("leave");
    }

    public int slackTime() {
        //We check to see if we're on the critical path to avoid the relatively expensive path finding methods
        return graph.getCriticalSet().contains(value)?0:mustLeave()-arrival();
    }

    public int floatTime(int v2) {
        if (!(backNodes.contains(v2) || frontNodes.contains(v2))) {
            throw new IllegalArgumentException("v" + v2 + " is not adjacent to v" + value + ". Float time cannot be calculated.");
        }
        Set<Integer> crtiset = graph.getCriticalSet();
        if (crtiset.contains(value) && crtiset.contains(v2)) {
            return 0; //If both vertices, thus the edge, is on the critical path, we know the float time is zero
        }
        Vertex other = graph.getVertex(v2); //Get the vertex object for the other end
        int a=0, l=0, w=0; //Declare the variable need for the calculations
        if (frontNodes.contains(v2)) { //Determine which vertex is in front, and set the variables accordingly
            a = arrival();
            l = other.mustLeave();
            w = graph.weight(value,v2);
        } else {
            a = other.arrival();
            l = mustLeave();
            w = graph.weight(v2, value);
        }
        return l - a - w; //Return the calculated float time
    }

    public Set<Integer> getBackVertices () {
        return new HashSet<>(backNodes);
    }

    public Set<Integer> getFrontVertices() {
        return new HashSet<>(frontNodes);
    }

    public int getValue() {
        return value;
    }
}
