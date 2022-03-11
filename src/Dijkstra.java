/**File Name: Dijkstra.java
 * Author: Daniel Kee Kim
 * Date: March 11th, 2022
 * Description: Full implementation of Dijkstra's shortest path algorithm.
 * Utilizes Priority Queue to easily travers through shortest distance to farthest distance.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Dijkstra {

    /**
     * Vertex class used in Dijkstra's Algorithm
     */
    static class Vertex implements Comparator<Vertex>{
        //Necessary Variables
        public HashMap<Vertex, Integer> adj;
        public int id;
        public int dist;
        public Vertex path;

        /**
         * Default Constructor for Vertex
         * @param id integer that represents the id of the vertex
         * @param dist distance from source to this vertex
         */
        public Vertex(int id, int dist){
            this.id = id;
            this.dist = dist;
            this.adj = new HashMap<>();
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
            return Integer.compare(o1.dist, o2.dist);
        }
    }

    /**
     * Primary function that does Dijkstra's shortest path algorithm
     * @param s source vertex
     */
    public static void dijkstra(Vertex s){
        Queue<Vertex> queue = new PriorityQueue<>(new Vertex());
        Queue<Vertex> result = new PriorityQueue<>(new Vertex());

        s.dist = 0;
        queue.add(s);

        while(!queue.isEmpty()){
            Vertex v = queue.remove();
            if(!result.contains(v)){
                result.add(v);
                for(Vertex w : v.adj.keySet()){
                    int cvw = v.adj.get(w);
                    if(v.dist + cvw < w.dist){
                        w.dist = v.dist + cvw;
                        w.path = v;
                        queue.add(w);
                    }
                }
            }
        }

        for(Vertex v : result){
            if(v.dist == 0){
                System.out.printf("%d dist: %d path: null\n", v.id+1, v.dist);
            }
            else{
                System.out.printf("%d dist: %d path: %d\n", v.id+1, v.dist, v.path.id+1);
            }
        }
    }

    /**
     * main function
     * @param args input parameter (filename, optional:source vertex number)
     */
    public static void main(String[] args) {
        int startNode = 1;
        int count;
        Vertex[] list;

        try{
            BufferedReader fileReader = new BufferedReader(new FileReader(args[0]));
            if(args.length > 1){
                startNode = Integer.parseInt(args[1]);
            }
            String currentLine = fileReader.readLine();
            count = Integer.parseInt(currentLine.strip());
            list = new Vertex[count];
            for(int i = 0; i < count; i++){
                list[i] = new Vertex(i, 0);
                list[i].dist = Integer.MAX_VALUE;
            }

            while((currentLine = fileReader.readLine()) != null){
                String[] lineArray = currentLine.split(" ");
                int id = Integer.parseInt(lineArray[0].replaceAll("\\D", "")) - 1;
                for(int i = 1; i < lineArray.length; i++){
                    String[] temp = lineArray[i].split(":");
                    int target = Integer.parseInt(temp[0]) - 1;
                    int distance = Integer.parseInt(temp[1]);
                    list[id].adj.put(list[target], distance);
                }
            }
            dijkstra(list[startNode-1]);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
