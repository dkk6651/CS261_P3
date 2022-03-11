import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Dijkstra {
    int count;
    Integer[] dist;
    Set<Integer> visited;
    PriorityQueue<Node> priorityQueue;
    List<List<Node>> edgeList;

    class Vertex{
        public List<Vertex> adj;
        public boolean known;
        public int dist;
        public Vertex path;
    }

    public void dijkstra (Vertex s){
        Queue<Vertex> queue = new PriorityQueue<Vertex>();
        Queue<Vertex> result = new PriorityQueue<Vertex>();

        s.dist = 0;
        queue.add(s);

        while(!queue.isEmpty()){
            Vertex v = queue.remove();
            if(!result.contains(v)){
                result.add(v);
                for(Vertex w : v.adj){
                    int cvw = w.dist;
                    w.dist = v.dist + cvw;
                    w.path = v;
                    queue.add(w);
                }
            }
        }
    }

    public static void main(String[] args) {
        int startNode = 0;
        int count;
        List<List<Node>> edgeList = new ArrayList<List<Node>>();

        try{
            BufferedReader fileReader = new BufferedReader(new FileReader(args[0]));
            if(args.length > 1){
                startNode = Integer.parseInt(args[1]);
            }
            String currentLine = fileReader.readLine();
            count = Integer.parseInt(currentLine.strip());
            for(int i = 0; i < count; i++){
                List<Node> item = new ArrayList<Node>();
                edgeList.add(item);
            }

            while((currentLine = fileReader.readLine()) != null){
                String[] lineArray = currentLine.split(" ");
                for(int i = 1; i < lineArray.length; i++){
                    edgeList.get(Integer.parseInt(lineArray[0].replaceAll("\\D", "")) - 1).add(new Node(Integer.parseInt(String.valueOf(lineArray[i].toCharArray()[0])) - 1, Integer.parseInt(String.valueOf(lineArray[i].toCharArray()[2]))));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
