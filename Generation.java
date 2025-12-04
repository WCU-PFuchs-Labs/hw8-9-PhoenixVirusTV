import java.util.*;

public class Generation {
    private GPTree[] trees;
    private DataSet data;
    private NodeFactory factory;
    private Random rand;

    public Generation(int size, int maxDepth, String fileName) {
        data = new DataSet(fileName);

        Binop[] ops = { new Plus(), new Minus(), new Mult(), new Divide() };
        factory = new NodeFactory(ops, data.getNumIndep()); // Must exist in DataSet

        rand = new Random();
        trees = new GPTree[size];

        // Build all trees and compute fitness
        for (int i = 0; i < size; i++) {
            trees[i] = new GPTree(factory, maxDepth, rand);
            trees[i].evaluateFitness(data);   // <-- IMPORTANT: correct method call
        }
    }

    // Return best tree (lowest fitness)
    public GPTree getBestTree() {
        GPTree best = trees[0];
        for (GPTree t : trees) {
            if (t.getFitness() < best.getFitness()) {
                best = t;
            }
        }
        return best;
    }

    // Return array of top ten fitness values (sorted)
    public double[] getTopTenFitness() {
        double[] fitness = new double[trees.length];
        for (int i = 0; i < trees.length; i++) {
            fitness[i] = trees[i].getFitness();
        }

        Arrays.sort(fitness);
        return Arrays.copyOf(fitness, Math.min(10, fitness.length));
    }
}
