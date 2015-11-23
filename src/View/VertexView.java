package View;

import Graphing.Graph;
import Graphing.Vertex;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


/**
 * Created by phillip on 11/23/15.
 */
public class VertexView {

    Graph g;
    Vertex v;
    PrintStream out;
    Scanner in;
    boolean stop, stay;

    HashMap<String, MenuEntry> menu;

    VertexView (Graph g, Vertex v) {
        this.g = g;
        this.v = v;
        in = new Scanner(System.in);
        out = System.out;
        stay = true;
        stop = false;

        menu = new HashMap<>();

        menu.put("arrive", new MenuEntry(new Runnable() {
            @Override
            public void run() {
                printArrival();
            }
        }, "Shows the earliest time all tasks leading to this vertex can be completed"));

        menu.put("leave", new MenuEntry(new Runnable() {
            @Override
            public void run() {
                printDeparture();
            }
        }, "Shows when the vertex must be left by inorder to complete the graph on time"));

        menu.put("slack", new MenuEntry(new Runnable() {
            @Override
            public void run() {
                printSlack();
            }
        }, "Show the amount of time this vertex can be idled at"));

        menu.put("float", new MenuEntry(new Runnable() {
            @Override
            public void run() {
                printFloat();
            }
        }, "Show the amount of 'wiggle room' for the task between two vertices"));

        menu.put("walk", new MenuEntry(new Runnable() {
            @Override
            public void run() {
                walk();
            }
        }, "Go to a neighbor of this vertex"));

        menu.put("graph", new MenuEntry(new Runnable() {
            @Override
            public void run() {
                stay = false;
                stop = true;
            }
        }, "Go back to the graph"));

        menu.put("back", new MenuEntry(new Runnable() {
            @Override
            public void run() {
                stop = true;
            }
        }, "Go back to the last vertex or to the graph"));

        menu.put("h", new MenuEntry(new Runnable() {
            @Override
            public void run() {
                MenuEntry.printMenuMap(menu);
            }
        }, "Print the help"));
    }

    public boolean beginInteraction() {
        out.println();
        while (!stop) {
            out.printf("Please enter a vertex command (h for help)[%d]> ", v.getValue());
            String command = in.next();
            menu.get(command).action.run();
        }
        return stay;
    }

    public void printArrival() {
        out.printf("The earliest arrival time to this vertex is %d\n", v.arrival());
    }

    public void printDeparture() {
        out.printf("The latest departure time to this vertex is %d\n", v.mustLeave());
    }

    public void printSlack() {
        out.printf("There is %d units of slack at this vertex\n", v.slackTime());
    }

    public void printFloat() {
        int other = selectNeighbor();
        out.printf("The float time between %d and %d is %d", v.getValue(), other, v.floatTime(other));
    }

    public void walk() {
        Vertex next = g.getVertex(selectNeighbor());
        VertexView nextv = new VertexView(g, next);
        stay = nextv.beginInteraction();
        stop = !stay;
    }

    private int selectNeighbor() {
        Set<Integer> front = v.getFrontVertices(), back = v.getBackVertices();
        Set<Integer> all = new HashSet<>(front);
        all.addAll(back);
        out.printf("Forward: %s    Backward: %s\n", front, back);
        int ret = -1;
        while (!all.contains(ret)) {
            out.print("Please enter the other vertex: ");
            ret = in.nextInt();
            if (!all.contains(ret)) {
                out.printf("%d is not a neighboring vertex");
            }
        }
        return ret;
    }

}
