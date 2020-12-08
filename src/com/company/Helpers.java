package com.company;

public class Helpers {

    //path is defined as the order of vertexes to visit, not including "home" (0)
    //this assumes a complete graph
    public static double getTSPCost(Graph g, int[] path) {
        double totalCost = g.getEdgeWeight(0, path[0]); //cost of moving to the first vertex from "home"
        for (int i = 1; i < path.length; i++)
            totalCost += g.getEdgeWeight(path[i-1], path[i]);
        totalCost += g.getEdgeWeight(path[path.length-1], 0); //cost of moving back
        return totalCost;
    }

    //checks if int array contains a value of 0
    public static boolean hasZeroElement(int[] array) {
        for (int j : array)
            if (j == 0)
                return true;
        return false;
    }

    //returns the index of the key or -1 if not found
    public static int indexOf(int[] array, int key) {
        for (int i = 0; i < array.length; i++)
            if (array[i] == key)
                return i;
        return -1;
    }

    //quick maths
    public static double circularGraphIndividualCost(int numVertexes, double radius) {
        return 2 * radius * Math.cos((Math.PI * (0.5 * numVertexes - 1)) / numVertexes);
    }
}
