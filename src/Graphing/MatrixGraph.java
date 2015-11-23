package Graphing;

import java.util.*;

/**
 * Created by DragonFire168 on 11/20/15.
 */
public class MatrixGraph implements Graph{

    private int[][] graph;
    private List<Edge> edgelist;
    private int time;
    private int [] critpath;
    private HashSet<Integer> critset;

    public MatrixGraph() {
        graph = null;
        critpath=null;
        edgelist = new ArrayList<>();
        time=-1;
    }

    public MatrixGraph(List<Edge> edges) {
        graph = null;
        critpath=null;
        edgelist = new ArrayList<>(edges);
        time=-1;
    }

    @Override
    public int weight(int v1, int v2) {
        return graph[v1][v2];
    }

    @Override
    public Vertex getVertex(int v1) {
        int[] adjverts = graph[v1];
        HashSet<Integer> front = new HashSet<>();
        HashSet<Integer> back = new HashSet<>();

        for (int v : adjverts) {
            if (v>0) {front.add(v);}
            if (v<0) {back.add(v);}
        }
        return new Vertex(v1, this, back, front);
    }

    @Override
    public void addEdge(Edge e) {
        if (graph != null) {
            throw new IllegalStateException("Cannot add edges once state has been compiled");
        }
        edgelist.add(e);
    }

    @Override
    public void compile() {
        //We need to get the largest valued vertex so we know how big to make the matrix
        Edge maxedge = edgelist.get(0);
        LargestVertexEdgeComparator comp = new LargestVertexEdgeComparator();
        for (Edge e : edgelist) {
            if (comp.compare(maxedge, e) < 0) {maxedge = e;}
        }

        int maxvert = maxedge.v1 > maxedge.v2?maxedge.v1:maxedge.v2; //Get the largest value for the largest edge
        graph = new int[maxvert+1][maxvert+1]; //Make the matrix (we use plus one so we can use the vertex values as indexes)

        for (Edge e : edgelist) {
            graph[e.v1][e.v2] = e.weight; //Add the connection for v1-->v2
            graph[e.v2][e.v1] = -1 * e.weight; //Add the reverse connection for v2-->v1
        }

        //Find the critical path
        Vertex f = getVertex(maxvert); //We have the value of the final vertex, get the vertex object for it
        List<Edge> criticalPath = f.getMaxPathToStart(); //Find the max path to start

        //Code to reverse list taken from http://stackoverflow.com/questions/10766492/what-is-the-simplest-way-to-reverse-an-arraylist
        Collections.reverse(criticalPath); //This is the list of edges from finish to start, reverse them
        this.critpath = new int[criticalPath.size()+1]; //Make the array
        for (int i = 0; i < criticalPath.size(); i++) {
            critpath[i] = criticalPath.get(i).v1; //Add the starts of each edge to the array
        }
        critpath[critpath.length - 1] = criticalPath.get(criticalPath.size() - 1).v2; //Assign finishing node

        critset = new HashSet<>();
        for (int v : critpath) {
            critset.add(v);
        }

        time = Edge.sumEdges(criticalPath); //Get the minimum time it will take to complete the network
    }

    @Override
    public int[] getCriticalPath() {
        return critpath;
    }

    @Override
    public Set<Integer> getCriticalSet() {
        return critset;
    }

    @Override
    public int minCompletionWeight() {
        return time;
    }
}
