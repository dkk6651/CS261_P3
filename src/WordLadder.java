/**File name: WordLadder.java
 * Author: Daniel Kee Kim
 * Date: March 11th, 2022
 * Description: Utilizes dijkstra's algorithm for word ladder.
 * Takes in input file and processes it into a priority queue making it easy to list all outputs based on layer number.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class WordLadder {

    /**
     * Vertex class used in Dijkstra's Algorithm
     */
    static class Vertex implements Comparator<Vertex>{
        //Necessary Variables
        public HashMap<Vertex, Integer> adj;
        public int id;
        public int dist;
        public int layer = -1;
        public String word;
        public Vertex path;
        public boolean visited = false;

        /**
         * Default Constructor for Vertex
         * @param id integer that represents the id of the vertex
         * @param dist distance from source to this vertex
         */
        public Vertex(int id, int dist, String word){
            this.id = id;
            this.dist = dist;
            this.adj = new HashMap<Vertex, Integer>();
            this.word = word;
        }

        /**
         * Empty Constructor
         */
        public Vertex() {}

        /**
         * Comparator function used for implementing Comparator
         * @param o1 source vertex
         * @param o2 destination vertex
         * @return in based on comparison (-1, 0, 1)
         */
        @Override
        public int compare(Vertex o1, Vertex o2) {
            return Integer.compare(o1.layer, o2.layer);
        }
    }

    /**
     * Word Ladder processing function
     * Traverses all possible vertices and creates graph based on layer number
     * @param s source vertex
     * @return priority queue based on layer number of all vertices
     */
    public static PriorityQueue<Vertex> wordLadder(Vertex s){
        int layer = 0;
        PriorityQueue<Vertex> priorityQueue = new PriorityQueue<>(new Vertex());

        Vertex temp = s;
        temp.layer = layer;
        layer++;
        priorityQueue.add(temp);
        for(Vertex v : temp.adj.keySet()){
            v.layer = layer;
            v.word = v.word + "<" + temp.word;
        }
        LinkedList<Vertex> queue = new LinkedList<>(temp.adj.keySet());

        while(!queue.isEmpty()){
            temp = queue.remove();
            layer = temp.layer+1;
            priorityQueue.add(temp);
            for(Vertex v : temp.adj.keySet()){
                if(v.layer == -1 && !queue.contains(v)){
                    v.layer = layer;
                    v.word = v.word + "<" + temp.word;
                    queue.add(v);
                }
            }
        }

        return priorityQueue;
    }

    /**
     * main function for word ladder
     * @param args input parameter (filename, optional:source word, optional:destination word)
     */
    public static void main(String[] args) {
        int count;
        String startString = null;
        String destString = null;
        Vertex start = null;
        Vertex dest = null;
        WordLadder.Vertex[] list;

        try{
            BufferedReader fileReader = new BufferedReader(new FileReader(args[0]));
            if(args.length > 2) destString = args[2];
            if(args.length > 1) startString = args[1];

            String currentLine = fileReader.readLine();
            count = Integer.parseInt(currentLine.replaceAll("\\D", ""));
            list = new WordLadder.Vertex[count];

            for(int i = 0; i < count; i++){
                list[i] = new WordLadder.Vertex(i, 0, "");
                list[i].dist = Integer.MAX_VALUE;
            }

            while((currentLine = fileReader.readLine()) != null){
                String[] lineArray = currentLine.split(" ");
                int id = Integer.parseInt(lineArray[0].replaceAll("\\D", "")) - 1;
                list[id].word = lineArray[1];
                for(int i = 2; i < lineArray.length; i++){
                    String[] temp = lineArray[i].split(":");
                    int target = Integer.parseInt(temp[0]) - 1;
                    int distance = Integer.parseInt(temp[1]);
                    list[id].adj.put(list[target], distance);
                }
                if(list[id].word.equals(startString)) start = list[id];
                if(list[id].word.equals(destString)) dest = list[id];
            }

            if(start == null) start = list[0];

            PriorityQueue<Vertex> queue = wordLadder(start);

            if(dest == null){
                for(Vertex v : queue){
                    System.out.printf("%d: %s\n", v.layer, v.word);
                }
            }
            else{
                assert dest != null;
                System.out.printf("%d: %s\n", dest.layer, dest.word);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
