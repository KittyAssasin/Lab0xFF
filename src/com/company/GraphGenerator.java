package com.company;

import java.util.Random;

public class GraphGenerator {

    public static Graph randomCompleteGraph(int numVertexes, double maxWeight) {
        Random r = new Random();
        Graph g = new Graph(numVertexes);

        for (int i = 0; i < numVertexes; i++)
            for (int k = 0; k < numVertexes; k++)
                if (k != i)
                    g.setEdge(i, k, r.nextDouble() * maxWeight);

        return g;
    }

    public static Graph randomEuclideanGraph(int numVertexes, double maxX, double maxY) {
        Random r = new Random();
        Graph g = new Graph(numVertexes);

        double[][] vertexPositions = new double[numVertexes][2];
        for (int i = 0; i < numVertexes; i++) {
            vertexPositions[i][0] = r.nextDouble() * maxX;
            vertexPositions[i][1] = r.nextDouble() * maxY;
        }

        for (int i = 0; i < numVertexes; i++)
            for (int k = 0; k < numVertexes; k++)
                if (i != k)
                    g.setEdge(i, k, getHypotenuse(vertexPositions[i][0], vertexPositions[i][1]
                            , vertexPositions[k][0], vertexPositions[k][1]));

        return g;
    }

    private static double getHypotenuse(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2.0) + Math.pow(y1 - y2, 2.0));
    }

    public static Graph shuffledCircularGraph(int numVertexes, double radius) {
        Random r = new Random();
        Graph g = new Graph(numVertexes);

        double[][] vertexPositions = new double[numVertexes][2];
        int[] shuffledVertexes = new int[numVertexes];
        for (int i = 0; i < numVertexes; i++) {
            shuffledVertexes[i] = i;
        }
        for (int i = 0; i < numVertexes; i++) {
            int randIndex = r.nextInt(numVertexes);
            int tmp = shuffledVertexes[randIndex];
            shuffledVertexes[randIndex] = shuffledVertexes[i];
            shuffledVertexes[i] = tmp;
        }

        //thank you John Ledbetter on stackoverflow.com for this answer to
        //"Trying to plot coordinates around the edge of a circle" by user1503606 on 11/28/12
        //Note: attempts to generate coordinates using geometric principles proved way more difficult, silly me
        for (int i = 0; i < numVertexes; i++) {
            vertexPositions[shuffledVertexes[i]][0] = radius + radius * Math.cos(2 * Math.PI * i / numVertexes);
            vertexPositions[shuffledVertexes[i]][1] = radius + radius * Math.sin(2 * Math.PI * i / numVertexes);
        }

        //math some triangle hypotenuses
        for (int i = 0; i < numVertexes; i++)
            for (int k = 0; k < numVertexes; k++)
                if (i != k)
                    g.setEdge(i, k, getHypotenuse(vertexPositions[i][0], vertexPositions[i][1]
                            , vertexPositions[k][0], vertexPositions[k][1]));

        return g;
    }
}
