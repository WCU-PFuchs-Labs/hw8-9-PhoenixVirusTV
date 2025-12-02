import java.io.IOException;
import java.util.*;

public class Generation {
    private DataSet data;
    private NodeFactory nf;
    private Random rand;
    private GPTree[] trees;

    // Constructor: creates the dataset and the initial random trees
    public Generation(int size, int maxDepth, String fileName) throws IOException {
        data = new DataSet(fileName);                // Read CSV file
        Binop[] ops = { new Plus(), new Minus(), new Mult(), new Divide() };
        nf = new NodeFactory(ops, data.getNumIndepVars()); // Factory knows number of variables
        rand = new Random();
        trees = new GPTree[size];
        for(int i = 0; i < size; i++) {
            trees[i] = new GPTree(nf, maxDepth, rand);
        }
    }

    // Evaluate fitness for all trees and sort them
    public void evalAll() {
        for(GPTree t : trees) t.evalFitness(data);
        Arrays.sort(trees);
    }

    // Return top 10 trees (lowest fitness)
    public ArrayList<GPTree> getTopTen() {
        ArrayList<GPTree> top10 = new ArrayList<>();
        for(int i = 0; i < Math.min(10, trees.length); i++) {
            top10.add(trees[i]);
        }
        return top10;
    }

    // Print best tree fitness
    public void printBestFitness() {
        System.out.printf("%.2f\n", trees[0].getFitness());
    }

    // Print best tree
    public void printBestTree() {
        System.out.println(trees[0]);
    }

    // Simple evolve: crossover random trees to make next generation
    public void evolve(int maxDepth) {
        GPTree[] nextGen = new GPTree[trees.length];
        for(int i = 0; i < trees.length / 2; i++) {
            GPTree parent1 = trees[rand.nextInt(trees.length)];
            GPTree parent2 = trees[rand.nextInt(trees.length)];
            GPTree child1 = (GPTree) parent1.clone();
            GPTree child2 = (GPTree) parent2.clone();
            child1.crossover(child2, rand);
            nextGen[2*i] = child1;
            nextGen[2*i+1] = child2;
        }
        trees = nextGen;
    }
}
