import java.util.*;

// Assume GPTree is your tree representation of expressions
public class Generation {
    private GPTree[] trees;
    private DataSet data;
    private NodeFactory factory;
    private Random rand;

    public Generation(int size, int maxDepth, String fileName) {
        data = new DataSet(fileName);
        Binop[] ops = { new Plus(), new Minus(), new Mult(), new Divide() };
        factory = new NodeFactory(ops, data.getNumIndep());
        trees = new GPTree[size];
        rand = new Random();

        // Initialize trees randomly
        for (int i = 0; i < size; i++) {
            trees[i] = new GPTree(factory, maxDepth);
        }

        // Evaluate fitness for all trees
        for (GPTree tree : trees) {
            tree.evaluateFitness(data);
        }
    }

    // --- Add these two methods so tests can access the best tree and top 10 fitness ---
    public GPTree getBestTree() {
        GPTree best = trees[0];
        for (GPTree tree : trees) {
            if (tree.getFitness() < best.getFitness()) { // assuming lower fitness is better
                best = tree;
            }
        }
        return best;
    }

    public double[] getTopTenFitness() {
        double[] fitnessValues = new double[trees.length];
        for (int i = 0; i < trees.length; i++) {
            fitnessValues[i] = trees[i].getFitness();
        }
        Arrays.sort(fitnessValues);
        // Return top 10, or less if fewer trees
        return Arrays.copyOf(fitnessValues, Math.min(10, fitnessValues.length));
    }
}
