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

    public static int countZeroElements(int[] array) {
        int count = 0;
        for (int j : array)
            if (j == 0)
                count++;
        return count;
    }

    public static void printArray(int[] array){
        System.out.print("[");
        for (int j : array)
            System.out.print(j + ",");
        System.out.println("]");
    }

    //returns the index of the key or -1 if not found
    public static int indexOf(int[] array, int key) {
        for (int i = 0; i < array.length; i++)
            if (array[i] == key)
                return i;
        return -1;
    }

    //array compare
    public static boolean sameArray(int[] array1, int[] array2) {
        if (array1.length != array2.length)
            return false;
        for (int i = 0; i < array1.length; i++)
            if (array1[i] != array2[i])
                return false;
        return true;
    }

    //quick maths
    public static double circularGraphIndividualCost(int numVertexes, double radius) {
        return 2 * radius * Math.cos((Math.PI * (0.5 * numVertexes - 1)) / numVertexes);
    }

    public static double nanoToSeconds(long nanoSeconds) {
        return nanoSeconds / 1_000_000_000.0;
    }
}
