import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Dijkstra {

    public static void main(String[] args) {
        int startNode = 1;
        int count;
        Node[] nodes;

        try{
            BufferedReader fileReader = new BufferedReader(new FileReader(args[0]));
            if(args[1] != null){
                startNode = Integer.parseInt(args[1]);
            }
            String currentLine = fileReader.readLine();
            count = Integer.parseInt(currentLine);

            nodes = new Node[count];
            for(int i = 0; i < count; i++){
                Node node = new Node(i+1);
                nodes[i] = node;
            }

            while((currentLine = fileReader.readLine()) != null){
                String[] lineArray = currentLine.split(" ");
                for(int i = 1; i < lineArray.length; i++){
                    Edge edge = new Edge(Integer.parseInt(lineArray[0].replaceAll("\\D", "")), Integer.parseInt(String.valueOf(lineArray[i].toCharArray()[0])), Integer.parseInt(String.valueOf(lineArray[i].toCharArray()[2])));
                    nodes[Integer.parseInt(lineArray[0].replaceAll("\\D", "")) - 1].addEdge(edge);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
