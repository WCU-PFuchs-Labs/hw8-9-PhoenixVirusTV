import java.util.*;

public class Generation {
    private GPTree[] trees;
    private NodeFactory factory;
    private DataSet data;
    private Random rand;
    private GPTree bestTree;
    private ArrayList<GPTree> topTen;

    public Generation(int size, int maxDepth, String fileName) {
        rand = new Random();
        data = new DataSet(fileName);

        // Operators
        Binop[] ops = { new Plus(), new Minus(), new Mult(), new Divide() };
        factory = new NodeFactory(ops, data.getNumIndepVars());

        // Initialize trees
        trees = new GPTree[size];
        for (int i = 0; i < size; i++) {
            trees[i] = new GPTree(factory, maxDepth, rand);
        }

        bestTree = null;
        topTen = new ArrayList<>();
    }

    // Evaluate all trees and compute fitness
    public void evalAll() {
        for (GPTree tree : trees) {
            tree.evalFitness(data);
        }

        // Sort trees by fitness
        Arrays.sort(trees);

        // Best tree is first
        bestTree = trees[0];

        // Update top ten
        topTen.clear();
        int n = Math.min(10, trees.length);
        for (int i = 0; i < n; i++) {
            topTen.add(trees[i].clone()); // clone to avoid mutation
        }
    }

    public GPTree getBestTree() {
        return bestTree;
    }

    public double getBestFitness() {
        return bestTree.getFitness();
    }

    public ArrayList<GPTree> getTopTen() {
        return topTen;
    }
}
