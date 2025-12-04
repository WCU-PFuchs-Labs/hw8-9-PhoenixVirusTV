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
        trees = new GPTree[size];
        rand = new Random();

        // Initialize trees randomly and evaluate fitness
        for (int i = 0; i < size; i++) {
            trees[i] = new GPTree(factory, maxDepth, rand);
            trees[i].evaluateFitness(data);
        }
    }

    // Return the best tree
    public GPTree getBestTree() {
        GPTree best = trees[0];
        for (GPTree tree : trees) {
            if (tree.getFitness() < best.getFitness()) {
                best = tree;
            }
        }
        return best;
    }

    // Return top 10 fitness values, sorted ascending
    public double[] getTopTenFitness() {
        double[] fitnessValues = new double[trees.length];
        for (int i = 0; i < trees.length; i++) {
            fitnessValues[i] = trees[i].getFitness();
        }
        Arrays.sort(fitnessValues);
        return Arrays.copyOf(fitnessValues, Math.min(10, fitnessValues.length));
    }
}
