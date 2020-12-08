package com.company;

public class Test {
    public static void runValidation() {
        //Validity testing
        double radius = 100;
        int circleVertexCount = 10;
        Graph circularGraph = GraphGenerator.shuffledCircularGraph(circleVertexCount, radius);

        double rCost = 100;
        int rMin = 4;
        int rMax = 15;
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

    public static void runPerformance(boolean verbose) {
        double rCost = 100;
        int rMin = 4;
        int rMax = 30;
        int rRange = rMax - rMin;
        Graph[] randomGraphs = new Graph[rRange];
        for (int i = 0; i < randomGraphs.length; i++)
            randomGraphs[i] = GraphGenerator.randomCompleteGraph(i + rMin, rCost);

        long[] bruteTimes = new long[rRange];
        long[] dynamicTimes = new long[rRange];
        long[] greedyTimes = new long[rRange];
        long startTime;
        long maxTime = 300_000_000_000L; //5 minutes

        //Testing Loops
        for (int i = 0; i < rRange; i++) {
            if (verbose)
                System.out.println("Brute: N=" + (i + rMin));
            startTime = System.nanoTime();
            Algorithm.bruteTSP(randomGraphs[i]);
            bruteTimes[i] = System.nanoTime() - startTime;
            if (bruteTimes[i] > maxTime)
                break;
        }

        for (int i = 0; i < rRange; i++) {
            if (verbose)
                System.out.println("Dynamic: N=" + (i + rMin));
            startTime = System.nanoTime();
            Algorithm.dynamicTSP(randomGraphs[i]);
            dynamicTimes[i] = System.nanoTime() - startTime;
            if (dynamicTimes[i] > maxTime)
                break;
        }

        for (int i = 0; i < rRange; i++) {
            if (verbose)
                System.out.println("Greedy: N=" + (i + rMin));
            startTime = System.nanoTime();
            Algorithm.greedyTSP(randomGraphs[i]);
            greedyTimes[i] = System.nanoTime() - startTime;
            if (greedyTimes[i] > maxTime)
                break;
        }



        //Printing Results
        System.out.println("---- Results in milliseconds with vertex min of " + rMin + " and max of " + rMax + " ----");
        String headerFormat = "%3s| %10s | %10s | %10s\n";
        String rowFormat = "%3d| %10d | %10d | %10d\n";

        System.out.format(headerFormat, "N", "Brute", "Dynamic", "Greedy");
        for (int i = 0; i < rRange; i++)
            System.out.format(rowFormat, i + rMin, bruteTimes[i] / 1000, dynamicTimes[i] / 1000,greedyTimes[i] / 1000);
    }
}
