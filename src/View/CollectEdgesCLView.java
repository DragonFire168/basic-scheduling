package View;

import Graphing.Edge;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by DragonFire on 11/20/15.
 */

public class CollectEdgesCLView {

    public static ArrayList<Edge> getEdges() {
        System.out.println("Enter each edge in the format of <first vertex> <second vertex> <weight> (999 to finish): ");
        Scanner input = new Scanner(System.in);
        ArrayList<Edge> ret = new ArrayList<>();
        EdgesIterator in = new EdgesIterator(input, 999);
        for (Edge edge : in) {
            ret.add(edge);
        }

        return ret;
    }
}

class EdgesIterator implements Iterable<Edge>, Iterator<Edge> {

    private Scanner input;
    private int end;
    private Edge current, nextEle;
    private boolean eof;

    EdgesIterator(Scanner input, int end) {
        this.input = input;
        this.end = end;
        current = null;
        eof = false;
        nextEle = null;
    }

    private void parseEdge() {
        if (nextEle != null) {return;} //We do not want to overwirte an edge that has not been returned

        int first = input.nextInt();
        if (first == end) {
            eof = true;
            return;
        }

        nextEle = new Edge(first, input.nextInt(), input.nextInt());
    }

    @Override
    public Iterator<Edge> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        parseEdge();
        return !eof;
    }

    @Override
    public Edge next() {
        parseEdge();
        if (eof) {
            //Exception discovered as proper implmentation from http://stackoverflow.com/questions/2176212/java-iterator-implementation-next-and-hasnext-enforcing-order
            //from Joachim Sauer's comment on uckelman's answer
            throw new NoSuchElementException();
        }
        current = nextEle;
        nextEle = null;
        return current;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
