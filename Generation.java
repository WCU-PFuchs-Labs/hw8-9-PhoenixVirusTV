import java.util.*;

public class Generation {
    private GPTree[] trees;
    private DataSet data;
    private NodeFactory factory;
    private Random rand;

    public Generation(int size, int maxDepth, String fileName) {
        data = new DataSet(fileName);
        Binop[] ops = { new Plus(), new Minus(), new Mult(), new Divide() };
        factory = new NodeFactory(ops, data.getNumIndep());
        rand = new Random();
        trees = new GPTree[size];
        for (int i = 0; i < size; i++) {
            trees[i] = new GPTree(factory, maxDepth, rand); // must match GPTree constructor
        }
    }

    // Evaluate fitness for all trees and sort them
    public void evalAll() {
        for (GPTree t : trees) {
            t.evalFitness(data);
        }
        Arrays.sort(trees); // GPTree must implement Comparable<GPTree>
    }

    // Return best tree (lowest fitness)
    public GPTree getBestTree() {
        return trees[0];
    }

    // Return best fitness
    public double getBestFitness() {
        return trees[0].getFitness();
    }

    // Return top 10 trees as ArrayList
    public ArrayList<GPTree> getTopTen() {
        ArrayList<GPTree> top10 = new ArrayList<>();
        for (int i = 0; i < Math.min(10, trees.length); i++) {
            top10.add(trees[i]);
        }
        return top10;
    }

    // Evolve the generation (for TestGP)
    public void evolve() {
        GPTree[] newGen = new GPTree[trees.length];
        for (int i = 0; i < trees.length; i += 2) {
            GPTree parent1 = trees[rand.nextInt(trees.length)].clone();
            GPTree parent2 = trees[rand.nextInt(trees.length)].clone();
            parent1.crossover(parent2);
            newGen[i] = parent1;
            if (i + 1 < trees.length) newGen[i + 1] = parent2;
        }
        trees = newGen;
    }
}
