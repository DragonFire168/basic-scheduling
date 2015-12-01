import Graphing.Edge;
import Graphing.MatrixGraph;
import View.CollectEdgesCLView;
import View.GraphInteractionView;

import java.util.ArrayList;

/**
 * Created by phillip on 12/1/15.
 */
public class CSCI220MatrixScheduling {
    public static void main(String[] args) {
        ArrayList<Edge> edges = CollectEdgesCLView.getEdges(); //Get the edges from the user
        MatrixGraph graph = new MatrixGraph(edges); //Added the edges to the graph
        graph.compile(); //Make the graph
        new GraphInteractionView(graph).beginInteraction(); //Begin the interaction
    }
}
