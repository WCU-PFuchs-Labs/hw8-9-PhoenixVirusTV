import java.util.*;

public class Generation {
    public GPTree[] trees; // make public so test can access if needed
    private DataSet data;
    private NodeFactory factory;
    private Random rand;

    public Generation(int size, int maxDepth, String fileName) {
        data = new DataSet(fileName);

        Binop[] ops = { new Plus(), new Minus(), new Mult(), new Divide() };
        factory = new NodeFactory(ops, data.getNumIndepVars());
        rand = new Random();

        trees = new GPTree[size];
        for (int i = 0; i < size; i++) {
            trees[i] = new GPTree(factory, maxDepth);
            trees[i].computeFitness(data);
        }

        // Sort trees by fitness ascending (best first)
        Arrays.sort(trees, Comparator.comparingDouble(GPTree::getFitness));
    }

    // Helper method for tests
    public GPTree getBestTree() {
        return trees[0]; // after sorting, best is first
    }

    // Helper method for tests
    public double[] getTopTenFitness() {
        int n = Math.min(10, trees.length);
        double[] topTen = new double[n];
        for (int i = 0; i < n; i++) {
            topTen[i] = trees[i].getFitness();
        }
        return topTen;
    }
}
