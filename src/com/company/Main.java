package com.company;

public class Main {

    public static void main(String[] args) {
        //Test.runValidation();
        Graph g = GraphGenerator.randomCompleteGraph(6, 100);
        System.out.println("brute: " + Algorithm.bruteTSP(g));
        System.out.println("dynamic: " + Algorithm.dynamicTSP(g));
    }
}
