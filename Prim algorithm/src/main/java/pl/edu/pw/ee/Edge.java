package pl.edu.pw.ee;

public class Edge implements Comparable<Edge> {
    private int start;
    private int end;
    private int weight;

    public Edge(int start, int end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    @Override
    public int compareTo(Edge o) {
        if(o.weight < this.weight) return 1;
        else return -1;
    }

    @Override
    public String toString() {
        return (char) (start + 65) + "_" + (char) (end+65) + "_" + weight;
    }
}
