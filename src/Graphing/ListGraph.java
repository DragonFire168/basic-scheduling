package Graphing;

/**
 * Created by phillip on 11/21/15.
 */
public class ListGraph  implements Graph{
    @Override
    public int Weight(int v1, int v2) {
        return 0;
    }

    @Override
    public Vertex getVertex(int v1) {
        return null;
    }

    @Override
    public void addEdge(Edge e) {

    }

    @Override
    public void compile() {

    }

    @Override
    public int[] getCriticalPath() {
        return new int[0];
    }

    @Override
    public int minCompletionWeight() {
        return 0;
    }
}