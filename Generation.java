import java.util.ArrayList;
import java.util.Random;

/**
 * Generation - manages a population of GPTrees for symbolic regression.
 */
public class Generation {
    private GPTree[] trees;
    private DataSet data;
    private NodeFactory factory;
    private Random rand;
    private GPTree bestTree;
    private ArrayList<GPTree> topTen;

    /**
     * Constructs a generation with given size and max tree depth.
     */
    public Generation(int size, int maxDepth, String fileName) {
        data = new DataSet(fileName);
        rand = new Random();

        // Initialize operators
        Binop[] ops = { new Plus(), new Minus(), new Mult(), new Divide() };
        factory = new NodeFactory(ops, data.getNumIndepVars());

        trees = new GPTree[size];
        for (int i = 0; i < size; i++) {
            trees[i] = new GPTree(factory, maxDepth, rand);
        }

        topTen = new ArrayList<>();
    }

    /**
     * Evaluate all trees in the generation on the dataset.
     */
    public void evalAll() {
        for (GPTree t : trees) {
            t.evalFitness(data);
        }

        // Sort trees by fitness
        java.util.Arrays.sort(trees);

        // Best tree
        bestTree = trees[0];

        // Update top ten
        topTen.clear();
        for (int i = 0; i < Math.min(10, trees.length); i++) {
            topTen.add(trees[i].clone());
        }
    }

    /**
     * Prints the best tree.
     */
    public void printBestTree() {
        System.out.println("Best Tree: " + bestTree);
    }

    /**
     * Prints the best fitness value.
     */
    public void printBestFitness() {
        System.out.printf("Best Fitness: %.2f%n", bestTree.getFitness());
    }

    /**
     * Returns the top ten trees.
     */
    public ArrayList<GPTree> getTopTen() {
        return topTen;
    }

    /**
     * Print the top ten fitness values.
     */
    public void printTopTen() {
        System.out.print("Top Ten Fitness Values:\n");
        for (int i = 0; i < topTen.size(); i++) {
            System.out.printf("%.2f", topTen.get(i).getFitness());
            if (i != topTen.size() - 1) System.out.print(", ");
        }
        System.out.println();
    }
}
