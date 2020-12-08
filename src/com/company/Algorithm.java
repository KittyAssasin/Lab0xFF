package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

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
        Path p = new Path(vertexes, 0);
        ArrayList<Path> LUT = new ArrayList<>();

        Path minPath =  dynamicTSPRecursive(g, 0, 1, p, LUT);

        int numVertexes = minPath.getVertexCount();
        int[] minVertexes = minPath.getVertexes();
        int[] results = new int[numVertexes - 1];
        for (int i = 1; i < numVertexes; i++) //vertex:order -> order:vertex (not including 0)
            results[i-1] = Helpers.indexOf(minVertexes, i);
        return new Path(results, minPath.getCost());
    }

    //recursive internals
    private static Path dynamicTSPRecursive(Graph g, int startV, int currentStep, Path currentPath, ArrayList<Path> LUT) {
        //lookup table check for this next path
        int[] nextVertexes = Arrays.copyOf(currentPath.getVertexes(), currentPath.getVertexCount());
        nextVertexes[startV] = currentStep;
        for (Path p : LUT) {
            if (Helpers.sameArray(p.getVertexes(), nextVertexes))
                return p;
        }

        int numVertexes = currentPath.getVertexCount();
        int[] vertexes = Arrays.copyOf(currentPath.getVertexes(), currentPath.getVertexCount());
        double currentCost = currentPath.getCost();

        //exit condition
        if (!Helpers.hasZeroElement(vertexes)) {
            double solutionCost = currentCost + g.getEdgeWeight(startV, 0);
            Path solution = new Path(vertexes, solutionCost);
            LUT.add(solution);
            return solution;
        }

        //generating paths and recursively calling
        Path cheapestPath = new Path(null, Double.MAX_VALUE);
        for (int i = 0; i < numVertexes; i++) {
            if (vertexes[i] == 0) {
                int[] nextVs = Arrays.copyOf(currentPath.getVertexes(), currentPath.getVertexCount());
                nextVs[i] = currentStep;
                Path newPath = dynamicTSPRecursive(g, i, currentStep + 1, new Path(nextVs,
                        currentCost + g.getEdgeWeight(Helpers.indexOf(nextVs, currentStep - 1), i)), LUT);
                cheapestPath = (newPath.getCost() < cheapestPath.getCost() ? newPath : cheapestPath);
            }
        }

        return cheapestPath;
    }
}
