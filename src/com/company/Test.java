package com.company;

public class Test {
    public static void runValidation() {
        //Validity testing
        double radius = 100;
        int circleVertexCount = 10;

        double rCost = 100;
        int rMin = 4;
        int rMax = 15;
        Graph circularGraph = GraphGenerator.shuffledCircularGraph(circleVertexCount, radius);
        Graph[] randomGraphs = new Graph[rMax - rMin];
        for (int i = 0; i < randomGraphs.length; i++)
            randomGraphs[i] = GraphGenerator.randomCompleteGraph(i + rMin, rCost);

        System.out.println("----Validation testing----");
        System.out.println("--Circular Graph");
        System.out.println("Cost Matrix:");
        circularGraph.printWeightMatrix();
        System.out.println();
        System.out.println("Expected Cost for 10 Vertexes: " + Helpers.circularGraphIndividualCost(circleVertexCount, radius) * circleVertexCount);
        System.out.println("Brute: " + Algorithm.bruteTSP(circularGraph));
        System.out.println("Dynamic:" + Algorithm.dynamicTSP(circularGraph));
        System.out.println();
        System.out.println("--Random Graph Comparisons");
        for (int i = 0; i < randomGraphs.length; i++) {
            System.out.println("N = " + (i + rMin));
            System.out.println("Brute: " + Algorithm.bruteTSP(randomGraphs[i]));
            System.out.println("Dynamic: " + Algorithm.dynamicTSP(randomGraphs[i]));
        }
        System.out.println();
    }
}
