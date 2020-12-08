package com.company;

public class Graph {

    private double[][] weights;
    private boolean[][] connections;

    public Graph(Graph graph) {
        this.weights = graph.weights;
        this.connections = graph.connections;
    }

    public Graph(int numVertexes) {
        weights = new double[numVertexes][numVertexes];
        connections = new boolean[numVertexes][numVertexes];
    }

    public void setEdge(int vertex1, int vertex2, double value) {
        weights[vertex1][vertex2] = value;
        weights[vertex2][vertex1] = value;
        connections[vertex1][vertex2] = true;
        connections[vertex2][vertex1] = true;
    }

    public double getEdgeWeight(int vertex1, int vertex2) {
        return weights[vertex1][vertex2];
    }

    public void removeEdge(int vertex1, int vertex2) {
        weights[vertex1][vertex2] = 0;
        weights[vertex2][vertex1] = 0;
        connections[vertex1][vertex2] = false;
        connections[vertex2][vertex1] = false;
    }

    public boolean edgeExists(int vertex1, int vertex2) {
        return connections[vertex1][vertex2];
    }

    public boolean hasEdge(int vertex) {
        for (int i = 0; i < connections.length; i++)
            if (connections[vertex][i])
                return true;
        return false;
    }

    public int getVertexCount() {
        return weights.length;
    }

    public void printWeightMatrix() {
        for (int i = 0; i < weights.length; i++) {
            for (int k = 0; k < weights[0].length; k++)
                if (i == k)
                    System.out.format(" %4s", "-");
                else
                    System.out.format(" %4.2f", weights[i][k]);
            System.out.println();
        }
    }

    public void printConnectionMatrix() {
        for (int i = 0; i < connections.length; i++) {
            for (int k = 0; k < connections[0].length; k++)
                if (i == k)
                    System.out.format(" %4s", "-");
                else
                    System.out.format(" %4b", connections[i][k]);
            System.out.println();
        }
    }

    @Override
    public String toString() {
        String s = "Graph{";
        for (int i = 0; i < weights.length; i++) {
            s += i + "[";
            for (int k = 0; k < weights[0].length; k++)
                if (this.edgeExists(i, k))
                    s += k + ":" + weights[i][k] + ",";
            s += "]";
            if (i < weights.length - 1)
                s += ",";
        }
        s += "}";
        return s;
    }
}
