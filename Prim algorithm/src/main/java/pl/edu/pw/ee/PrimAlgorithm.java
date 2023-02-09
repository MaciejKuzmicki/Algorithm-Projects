package pl.edu.pw.ee;

import java.util.*;
import pl.edu.pw.ee.services.MinSpanningTree;

public class PrimAlgorithm implements MinSpanningTree {
    private int [][] graph;

    List<Edge> output = new ArrayList<>();

    @Override
    public String findMST(String pathToFile){
        boolean [] visited = new boolean[graph.length];
        for(int i = 0; i < visited.length; i++) {
            visited[i] = false;
        }
        PriorityQueue<Edge> edges = new PriorityQueue<>();
        addEdgesToPQ(0, visited, edges);
        while(edges.size() > 0) {
            Edge edge = edges.poll();
            if(!visited[edge.getStart()] || !visited[edge.getEnd()]) {
                output.add(edge);
                visited[edge.getStart()] = true;
                visited[edge.getEnd()] = true;
            }
            addEdgesToPQ(edge.getEnd(), visited, edges);
        }
        return null;
    }

    public void addEdgesToPQ(int index, boolean [] visited, PriorityQueue<Edge> edges) {
        for(int i = 0; i < graph.length; i++) {
            if(graph[index][i] > 0 && (!visited[index] || !visited[i])) {
                edges.add(new Edge(index, i, graph[index][i]));
            }
        }
    }

    public PrimAlgorithm(int [][] array) {
        this.graph = array;
    }

    public void show() {
        for(int i = 0 ; i < output.size(); i++) {
            System.out.println(output.get(i).toString());
        }
    }

}
