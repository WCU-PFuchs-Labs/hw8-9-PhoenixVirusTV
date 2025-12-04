import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Generation - manages a population of GPTrees and provides the methods
 * expected by the autograder/test harness:
 *  - Generation(int size, int maxDepth, String fileName)
 *  - void evalAll()
 *  - void printBestTree()
 *  - void printBestFitness()
 *  - ArrayList<GPTree> getTopTen()
 *
 * It uses your NodeFactory(NodeFactory(Binop[], int)) and DataSet.
 */
public class Generation {

    private int size;
    private int maxDepth;
    private String fileName;
    private DataSet data;
    private NodeFactory factory;
    private Random rand;

    private GPTree bestTree;
    private double bestFitness;
    private ArrayList<GPTree> topTen;

    // Constructor expected by TestGeneration
    public Generation(int size, int maxDepth, String fileName) {
        this.size = size;
        this.maxDepth = maxDepth;
        this.fileName = fileName;
        this.rand = new Random();
        this.data = new DataSet(fileName);

        // Build operator set (ensure Plus exists in your project)
        Binop[] ops = { new Plus(), new Minus(), new Mult(), new Divide() };
        this.factory = new NodeFactory(ops, data.getNumIndepVars());

        this.topTen = new ArrayList<>();
        this.bestTree = null;
        this.bestFitness = Double.POSITIVE_INFINITY;
    }

    // Create population, evaluate all, and fill topTen
    public void evalAll() {
        ArrayList<GPTree> population = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            GPTree t = new GPTree(factory, maxDepth, rand);
            t.evalFitness(data);
            population.add(t);
        }

        // Sort smallest fitness first (we're minimizing SSE)
        Collections.sort(population);

        // Best tree is first element
        if (!population.isEmpty()) {
            bestTree = population.get(0);
            bestFitness = bestTree.getFitness();
        }

        // Get top 10 (clone them so external code can modify safely)
        topTen.clear();
        for (int i = 0; i < Math.min(10, population.size()); i++) {
            topTen.add(population.get(i).clone());
        }
    }

    // Print the best tree
    public void printBestTree() {
        System.out.println("Best Tree: " + (bestTree == null ? "" : bestTree.toString()));
    }

    // Print best fitness with 2 decimal places
    public void printBestFitness() {
        System.out.printf("Best Fitness: %.2f%n", bestFitness);
    }

    // Return top ten GPTrees
    public ArrayList<GPTree> getTopTen() {
        return topTen;
    }

    // Print top ten fitness values in autograder format
    public void printTopTen() {
        System.out.print("Top Ten Fitness Values:\n");
        for (int i = 0; i < 10; i++) {
            if (i < topTen.size()) System.out.printf("%.2f", topTen.get(i).getFitness());
            else System.out.printf("%.2f", 0.00);
            if (i < 9) System.out.print(", ");
        }
        System.out.println();
    }

    // Example main for quick manual testing (not used by autograder)
    public static void main(String[] args) {
        String file = (args != null && args.length > 0) ? args[0] : "simpleTest.csv";
        Generation gen = new Generation(500, 6, file);
        gen.evalAll();
        System.out.println("Enter data file name: " + file);
        gen.printBestTree();
        gen.printBestFitness();
        gen.printTopTen();
    }
}
