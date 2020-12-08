package com.company;

public class Main {

    public static void main(String[] args) {
        Graph g = GraphGenerator.randomEuclideanGraph(10, 100, 100);
        System.out.println(Algorithm.bruteTSP(g));
        System.out.println(Algorithm.dynamicTSP(g));
        //Test.runPerformance(true);
    }
}
