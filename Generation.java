import java.util.*;

public class Generation {
    private GPTree[] trees;
    private DataSet data;
    private NodeFactory factory;
    private Random rand;

    public Generation(int size, int maxDepth, String fileName) {
        data = new DataSet(fileName);

        Binop[] ops = { new Plus(), new Minus(), new Mult(), new Divide() };
        factory = new NodeFactory(ops, data.getNumIndepVars());

        trees = new GPTree[size];
        rand = new Random();

        for (int i = 0; i < size; i++) {
            trees[i] = new GPTree(factory, maxDepth, rand);
            trees[i].evaluateFitness(data); // <-- use your actual method
        }
    }

    public GPTree getBestTree() {
        GPTree best = trees[0];
        for (GPTree tree : trees) {
            if (tree.getFitness() < best.getFitness()) {
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
        return Arrays.copyOf(fitnessValues, Math.min(10, fitnessValues.length));
    }
}
