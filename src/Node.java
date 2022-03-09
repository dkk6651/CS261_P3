import java.util.*;

public class Node {
    public int id;
    public List<Edge> edges;

    Node(int id){
        this.id = id;
    }

    public void addEdge(Edge edge){
        edges.add(edge);
    }
}
