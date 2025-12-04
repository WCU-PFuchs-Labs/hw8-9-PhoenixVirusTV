import java.util.*;
import java.io.*;

public class Generation {
    private GPTree[] trees;
    private DataSet data;
    private NodeFactory factory;
    private Random rand;

    /**
     * Constructor: creates a generation of GPTrees
     * @param size number of trees in the generation
     * @param maxDepth maximum depth of each tree
     * @param fileName CSV data file
     */
    public Generation(int size, int maxDepth, String fileName) {
        try {
            data = new DataSet(fileName);
        } catch (Exception e) {
            System.out.println("Error loading data file: " + e.getMessage());
            return;
        }

        Binop[] ops = { new Plus(), new Minus(), new Mult(), new Divide() };
        factory = new NodeFactory(ops, data.getNumIndepVars()); // Fixed method name
        rand = new Random();

        trees = new GPTree[size];
        for (int i = 0; i < size; i++) {
            trees[i] = new GPTree(factory, maxDepth, rand); // constructor with Random
        }
    }

    /**
     * Evaluate fitness for all trees in the generation and sort them
     */
    public void evalAll() {
        for (GPTree t : trees) {
            t.evalFitness(data);
        }
        Arrays.sort(trees); // requires GPTree implements Comparable
    }

    /**
     * Return top 10 GPTrees in increasing fitness order
     */
    public ArrayList<GPTree> getTopTen() {
        ArrayList<GPTree> topTen = new ArrayList<>();
        int n = Math.min(10, trees.length);
        for (int i = 0; i < n; i++) {
            topTen.add((GPTree) trees[i].clone());
        }
        return topTen;
    }

    /**
     * Print the best fitness in the generation
     */
    public void printBestFitness() {
        if (trees.length > 0)
            System.out.printf("Best Fitness: %.2f%n", trees[0].getFitness());
    }

    /**
     * Print the best GPTree
     */
    public void printBestTree() {
        if (trees.length > 0)
            System.out.println("Best Tree: " + trees[0]);
    }

    /**
     * Evolve the generation (Checkpoint 2)
     * Creates a new generation using crossover of randomly selected parents
     */
    public void evolve() {
        GPTree[] newTrees = new GPTree[trees.length];

        for (int i = 0; i < trees.length / 2; i++) {
            // Select two parents from top 50% for better fitness
            GPTree parent1 = trees[rand.nextInt(trees.length / 2)].clone();
            GPTree parent2 = trees[rand.nextInt(trees.length / 2)].clone();

            // Perform crossover
            parent1.crossover(parent2);

            // Add children to new generation
            newTrees[2 * i] = parent1;
            newTrees[2 * i + 1] = parent2;
        }

        trees = newTrees;
    }

    /**
     * Get the best GPTree (first in sorted array)
     */
    public GPTree getBestTree() {
        return trees.length > 0 ? trees[0] : null;
    }

    /**
     * Get the fitness of the best tree
     */
    public double getBestFitness() {
        return trees.length > 0 ? trees[0].getFitness() : Double.MAX_VALUE;
    }
}
