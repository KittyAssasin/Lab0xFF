package com.company;

import java.util.Arrays;

public class Algorithm {

    //this algorithm calculates the cost for every possible path and finds the shortest
    public static Path bruteTSP(Graph g) {
        int[] vertexList = new int[g.getVertexCount() - 1]; //not including 0 vertex
        for (int i = 1; i <= vertexList.length; i++)
            vertexList[i - 1] = i;

        Permutator p = new Permutator(vertexList);
        int[] cheapestPath = p.getFirst();
        double cheapestPathCost = Helpers.getTSPCost(g, cheapestPath);
        while (p.hasNext()) {
            int[] nextPath = p.getNext();
            double nextCost = Helpers.getTSPCost(g, nextPath);
            if (nextCost < cheapestPathCost) {
                cheapestPath = nextPath;
                cheapestPathCost = nextCost;
            }
        }

        return new Path(cheapestPath, cheapestPathCost);
    }

    //This algorithm takes only the shortest paths from each vertexes' perspective
    public static Path greedyTSP(Graph g) {
        int numVertexes = g.getVertexCount();
        int[] visitedVertexes = new int[numVertexes];
        visitedVertexes[0] = 1; //node 0 is the first, value of 0 means not visited
        int count = 2;
        double totalCost = 0;
        int currentVertex = 0;
        do {
            int cheapestVertex = 0;
            boolean firstFlag = true;
            for (int i = 1; i < numVertexes; i++)
                if (visitedVertexes[i] == 0)
                    if (firstFlag) {
                        cheapestVertex = i;
                        firstFlag = false;
                    } else
                        if (g.getEdgeWeight(currentVertex,i) < g.getEdgeWeight(currentVertex,cheapestVertex))
                            cheapestVertex = i;

            totalCost += g.getEdgeWeight(currentVertex, cheapestVertex);
            visitedVertexes[cheapestVertex] = count;
            count++;
            currentVertex = cheapestVertex;
        } while (Helpers.hasZeroElement(visitedVertexes));
        totalCost += g.getEdgeWeight(currentVertex, 0); //returning home

        //visitedVertexes is now index(node):value(order) and needs to be flipped
        int[] results = new int[numVertexes - 1];
        for (int i = 2; i <= numVertexes; i++)
            results[i-2] = Helpers.indexOf(visitedVertexes, i);

        return new Path(results, totalCost);
    }

    //initializer and public function
    public static Path dynamicTSP(Graph g) {
        int[] vertexes = new int[g.getVertexCount()];
        vertexes[0] = 1; //home (0) is the first vertex
        return dynamicTSPRecursive(g, 0, 0, vertexes);
    }

    //recursive internals
    private static Path dynamicTSPRecursive(Graph g, int startV, double cost, int[] vertexes) {
        int numRemaining = 0;
        int numVertexes = vertexes.length;
        for (int j : vertexes)
            if (j == 0)
                numRemaining++;

        //exit condition
        if (numRemaining == 0) {
            int[] results = new int[numVertexes - 1];
            for (int i = 2; i <= numVertexes; i++)
                results[i-2] = Helpers.indexOf(vertexes, i); //vertex:order -> order:vertex (not including 0)
            return new Path(results, cost + g.getEdgeWeight(startV, 0)); //cost of returning home
        }

        Path[] paths = new Path[numRemaining];
        int k = 0;
        for (int i = 1; i < numVertexes; i++)
            if (vertexes[i] == 0) {
                int[] visitedVertexes = Arrays.copyOf(vertexes, vertexes.length); //cant be shallow passing the array
                visitedVertexes[i] = numVertexes - numRemaining + 1;
                paths[k] = dynamicTSPRecursive(g, i, cost + g.getEdgeWeight(startV, i), visitedVertexes);
                k++;
            }

        Path cheapestPath = paths[0];
        for (int i = 1; i < paths.length; i++)
            if (paths[i].getCost() < cheapestPath.getCost())
                cheapestPath = paths[i];

        return cheapestPath;
    }
}
