package com.company;

public class Test {
    public static void runValidation() {
        //Validity testing
        double radius = 100;
        int circleVertexCount = 10;
        Graph circularGraph = GraphGenerator.shuffledCircularGraph(circleVertexCount, radius);

/*
        double rCost = 100;
        int rMin = 4;
        int rMax = 15;
        Graph[] randomGraphs = new Graph[rMax - rMin];
        for (int i = 0; i < randomGraphs.length; i++)
            randomGraphs[i] = GraphGenerator.randomCompleteGraph(i + rMin, rCost);
*/
        System.out.println("---- Validation testing ----");
        System.out.println("NOTE: My dynamic graph implementation is so terrible it can't reasonably perform with N > 7");
        System.out.println("-- Circular Graph");
        System.out.println("Cost Matrix:");
        circularGraph.printWeightMatrix();
        System.out.println();
        System.out.println("Expected Cost for "+ circleVertexCount + " Vertex Circular Graph: " + Helpers.circularGraphIndividualCost(circleVertexCount, radius) * circleVertexCount);
        System.out.println("Brute: " + Algorithm.bruteTSP(circularGraph));
        System.out.println("Greedy: " + Algorithm.greedyTSP(circularGraph));
/*
        System.out.println();
        System.out.println("-- Random Graph Comparisons");
        for (int i = 0; i < randomGraphs.length; i++) {
            System.out.println("N = " + (i + rMin));
            System.out.println("Brute: " + Algorithm.bruteTSP(randomGraphs[i]));
            System.out.println("Dynamic: " + Algorithm.dynamicTSP(randomGraphs[i]));
        }
        System.out.println();
*/
    }

    public static void runPerformance(boolean verbose) {
        double rCost = 100;
        int rMin = 4;
        int rMax = 16; //exclusive
        int rRange = rMax - rMin;
        Graph[] randomGraphs = new Graph[rRange];
        for (int i = 0; i < randomGraphs.length; i++)
            randomGraphs[i] = GraphGenerator.randomEuclideanGraph(i + rMin, rCost, rCost);

        long[] bruteTimes = new long[rRange];
        double[] bruteCosts = new double[rRange];

        long startTime;
        long maxTime = 60_000_000_000L; //1 minute

        //Testing Loops
        int j;
        for (j = 0; j < rRange; j++) {
            if (verbose)
                System.out.println("Brute: N=" + (j + rMin));
            startTime = System.nanoTime();
            Path p = Algorithm.bruteTSP(randomGraphs[j]);
            bruteTimes[j] = System.nanoTime() - startTime;
            bruteCosts[j] = p.getCost();
            if (bruteTimes[j] > maxTime)
                break;
        }

        int nStart = 50;
        int n = nStart;
        int nScale = 2;
        int nTotalSteps = 9; //run out of memory at higher values
        long[] greedyBigTimes = new long[nTotalSteps];
        for (int i = 0; i < nTotalSteps; i++) {
            if (verbose)
                System.out.println("Greedy: N=" + n);
            Graph g = GraphGenerator.randomCompleteGraph(n, rCost);
            startTime = System.nanoTime();
            Algorithm.greedyTSP(g);
            greedyBigTimes[i] = System.nanoTime() - startTime;
            n *= nScale;
            if (greedyBigTimes[i] > maxTime)
                break;
        }
        n = nStart;

        int numTrials = 10;
        long[] greedyTimes = new long[j];
        double[] greedyCosts = new double[j];
        double[] sqr = new double[j];

        for (int k = 0; k < greedyTimes.length; k++) {
            if (verbose)
                System.out.println("Greedy: N=" + (k + rMin));
            for (int i = 0; i < numTrials; i++) {
                startTime = System.nanoTime();
                Path p = Algorithm.greedyTSP(randomGraphs[k]);
                greedyTimes[k] = System.nanoTime() - startTime;
                greedyCosts[k] += p.getCost();
            }
            greedyCosts[k] /= numTrials;
            sqr[k] = greedyCosts[k] / bruteCosts[k];
        }

        //Printing Results
        System.out.println("---- Results in nanoseconds of N vertex count graphs ----");
        String headerFormat = "%3s| %12s | %12s | %12s\n";
        String rowFormat = "%3d| %12d | %12d | %12f\n";

        System.out.format(headerFormat, "N", "Brute", "Greedy", "AvgSQR");
        for (int i = 0; i < j; i++)
            System.out.format(rowFormat, i + rMin, bruteTimes[i], greedyTimes[i], sqr[i]);
        System.out.println();
        System.out.println("---- Greedy results in nanoseconds for big N values ----");
        System.out.format("%6s| %12s\n", "N", "Greedy");
        for (int i = 0; i < nTotalSteps; i++) {
            System.out.format("%6s| %12d\n", n, greedyBigTimes[i]);
            n *= nScale;
        }
        n = nStart;
    }
}
