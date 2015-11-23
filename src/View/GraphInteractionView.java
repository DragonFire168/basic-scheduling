package View;

import Graphing.Graph;
import Graphing.Vertex;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by phillip on 11/20/15.
 */

public class GraphInteractionView {
    private Graph g;
    private Scanner in;
    private PrintStream out;
    private HashMap<String, MenuEntry> menu;
    private boolean stop;

    public GraphInteractionView(Graph g) {
        this.g = g;
        this.in = new Scanner(System.in);
        this.out = System.out;
        /*
        Settled on using anonymous runnable classes from trying to find a way to put a function in the list, came upon
        this idea from a cursory search on delegates and first class functions in java
         */
        menu.put("stat", new MenuEntry(new Runnable() {
            @Override
            public void run() {
                printGraphMakeUp();
            }
        }, "Shows the statistics for the graph"));

        menu.put("crit", new MenuEntry(new Runnable() {
            @Override
            public void run() {
                printCriticalPath();
            }
        }, "Shows the critical path for the graph"));

        menu.put("edge", new MenuEntry(new Runnable() {
            @Override
            public void run() {
                printEdge();
            }
        }, "Displays the weight for a given edge"));

        menu.put("h", new MenuEntry(new Runnable() {
            @Override
            public void run() {
                MenuEntry.printMenuMap(menu);
            }
        }, "Show the help"));
        menu.put("q", new MenuEntry(new Runnable() {
            @Override
            public void run() {
                stop = true;
            }
        }, "Quits the program"));

        menu.put("vertx", new MenuEntry(new Runnable() {
            @Override
            public void run() {
                openVertex();
            }
        }, "Open a vertex to view its properties"));
    }

    public void beginInteraction() {
        printGraphMakeUp();
        printCriticalPath();
        out.println();
        while (!stop) {
            out.print("Please enter a graph command (h for help): ");
            String command = in.next();
            menu.get(command).action.run();
        }
    }

    public void printGraphMakeUp() {
        out.printf("Graph has %d vertices and %d edges\n", g.getNumberOfVertices(), g.getNumberOfEdges());
    }

    public void printCriticalPath() {
        out.print("Critical path is");
        for (int i : g.getCriticalPath()) {
            out.print(" " + i);
        }
        out.println();
    }

    public void printEdge() {
        out.println("Enter the endpoints for the edge:");
        int first = in.nextInt();
        int second = in.nextInt();
        int w = g.weight(first, second);
        if (w == 0) {
            out.println("The vertices are not connected");
        } else {
            out.printf("The edge (%d, %d) has a weight of %d\n", first, second, w);
        }
    }

    private int askVertex() {
        int ret;
        while (true) {
            out.print("Please enter a vertex: ");
            ret = in.nextInt();
            try {
                g.getVertex(ret);
                return ret;
            } catch (IndexOutOfBoundsException e) {
                out.print("Not a valid vertex");
            }
        }
    }

    public void openVertex() {
        Vertex v = g.getVertex(askVertex());
        VertexView view = new VertexView(g, v);
        out.println();
        view.beginInteraction();
    }
}
