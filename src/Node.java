import java.util.*;

public class Node implements Comparator<Node> {
    public int id;
    public int cost;

    public Node() {};
    public Node(int id, int cost){
        this.id = id;
        this.cost = cost;
    }

    @Override public int compare(Node node1, Node node2){
        return Integer.compare(node1.cost, node2.cost);
    }
}
