package pl.edu.pw.ee;

public class Node implements Comparable<Node> {
    private int value;
    private int asciiValue;
    private Node left = null;
    private Node right = null;

    public int getKey() {
        return asciiValue;
    }

    public int getValue() {
        return value;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public Node(int value, int asciiValue) {
        this.value = value;
        this.asciiValue = asciiValue;
    }

    public Node(int value, int asciiValue, Node left, Node right) {
        this.value = value;
        this.asciiValue = asciiValue;
        this.left = left;
        this.right = right;
    }

    @Override
    public int compareTo(Node o) {
        return value - o.value;
    }

    @Override
    public String toString() {
        return new String("Value: " + value + " Ascii: " + asciiValue);
    }
}
