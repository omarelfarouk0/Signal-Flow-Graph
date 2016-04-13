//
//public class Node {
//
//    private Shapes shape;
//    private String name;
//
//    public Node(Shapes shape, String name) {
//        this.shape = shape;
//        this.name = name;
//    }
//
//    public Shapes getShape() {
//        return shape;
//    }
//
//    public void setShape(Shapes shape) {
//        this.shape = shape;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//}

import java.util.ArrayList;

public class Node {

    private ArrayList<Node> connections;
    private ArrayList<Double> edges;
    private Shapes shape;
    private String name;
    private boolean visited;

    public Node(String name) {
        connections = new ArrayList<Node>();
        edges = new ArrayList<>();
        this.name = name;
        this.visited = false;

    }

    public Node(Shapes shape, String name) {
        this.shape = shape;
        this.name = name;
        connections = new ArrayList<>();
        edges = new ArrayList<>();
        this.visited = false;
    }

    public void adderNode(Node a) {
        connections.add(a);
    }

    public void adderEdge(Double value) {
        edges.add(value);
    }

    public ArrayList<Node> getConnections() {
        return connections;
    }

    public void setConnections(ArrayList<Node> connections) {
        this.connections = connections;
    }

    public ArrayList<Double> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<Double> edges) {
        this.edges = edges;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public Shapes getShape() {
        return shape;
    }

    public void setShape(Shapes shape) {
        this.shape = shape;
    }

}
