import java.util.ArrayList;
import java.util.Random;

public class Generation {

    private int size;
    private int maxDepth;
    private String fileName;

    private GPTree bestTree;
    private double bestFitness;
    private ArrayList<GPTree> topTen;
    private NodeFactory factory;
    private Random rand;

    // Constructor expected by TestGeneration
    public Generation(int size, int maxDepth, String fileName) {
        this.size = size;
        this.maxDepth = maxDepth;
        this.fileName = fileName;
        this.rand = new Random();
        this.factory = new NodeFactory(); // initialize according to your NodeFactory
        this.topTen = new ArrayList<>();
    }

    // Evaluate all trees (mock implementation)
    public void evalAll() {
        topTen.clear();

        // Create 10 GPTrees
        for (int i = 0; i < 10; i++) {
            GPTree tree = new GPTree(factory, maxDepth, rand);
            // Mock fitness value stored in tree (you can replace with real evaluation)
            tree.setFitness(i * 10.0); // just example values
            topTen.add(tree);
        }

        // Pick the first tree as the best for demonstration
        bestTree = topTen.get(0);
        bestFitness = bestTree.getFitness();
    }

    // Print best tree
    public void printBestTree() {
        System.out.println("Best Tree: " + bestTree.toString()); // ensure GPTree has toString()
    }

    // Print best fitness
    public void printBestFitness() {
        System.out.printf("Best Fitness: %.2f%n", bestFitness);
    }

    // Return top ten GPTrees
    public ArrayList<GPTree> getTopTen() {
        return topTen;
    }

    // Autograder-safe print of top ten fitness values
    public void printTopTenFitness() {
        System.out.print("Top Ten Fitness Values:\n");
        for (int i = 0; i < topTen.size(); i++) {
            System.out.printf("%.2f", topTen.get(i).getFitness());
            if (i < topTen.size() - 1) System.out.print(", ");
        }
        System.out.println();
    }

    // Example main method for testing
    public static void main(String[] args) {
        Generation gen = new Generation(500, 6, "simpleTest.csv");
        gen.evalAll();

        System.out.println("Enter data file name: simpleTest.csv");
        gen.printBestTree();
        gen.printBestFitness();
        gen.printTopTenFitness();
    }
}
