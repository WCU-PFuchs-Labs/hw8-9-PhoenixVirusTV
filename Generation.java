import java.util.*;

// Assume GPTree is your tree representation of expressions
public class Generation {
    private GPTree[] trees;
    private DataSet data;
    private NodeFactory factory;
    private Random rand;

    public Generation(int size, int maxDepth, String fileName) {
        data = new DataSet(fileName);
        rand = new Random();

        // Set up operators
        Binop[] ops = { new Plus(), new Minus(), new Mult(), new Divide() };
        factory = new NodeFactory(ops, data.getNumIndepVars()); // <-- make sure DataSet has getNumIndepVars()

        trees = new GPTree[size];

        // Initialize trees randomly and evaluate fitness
        for (int i = 0; i < size; i++) {
            trees[i] = new GPTree(factory, maxDepth, rand); // GPTree constructor needs Random
            trees[i].evaluate(data); // <-- compute fitness here
        }
    }

    // Return the tree with the lowest fitness
    public GPTree getBestTree() {
        GPTree best = trees[0];
        for (GPTree tree : trees) {
            if (tree.getFitness() < best.getFitness()) { // lower fitness = better
                best = tree;
            }
        }
        return best;
    }

    // Return top 10 fitness values sorted ascending
    public double[] getTopTenFitness() {
        double[] fitnessValues = new double[trees.length];
        for (int i = 0; i < trees.length; i++) {
            fitnessValues[i] = trees[i].getFitness();
        }
        Arrays.sort(fitnessValues);
        return Arrays.copyOf(fitnessValues, Math.min(10, fitnessValues.length));
    }
}
