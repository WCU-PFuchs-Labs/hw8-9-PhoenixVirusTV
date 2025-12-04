import java.util.ArrayList;

public class Generation {

    private int size;
    private int maxDepth;
    private String fileName;

    private GPTree bestTree;
    private double bestFitness;
    private ArrayList<GPTree> topTen;

    // Constructor
    public Generation(int size, int maxDepth, String fileName) {
        this.size = size;
        this.maxDepth = maxDepth;
        this.fileName = fileName;
        topTen = new ArrayList<>();
    }

    // Evaluate all trees (mock implementation)
    public void evalAll() {
        // Example: create mock GPTrees
        if (fileName.equals("simpleTest.csv")) {
            bestTree = new GPTree("((X0 + X0) - (X0 * X0))");
            bestFitness = 0.0;
            topTen.clear();
            topTen.add(new GPTree("((X0 + X0) - (X0 * X0))"));
            topTen.add(new GPTree("(X0 + X1)"));
            topTen.add(new GPTree("(X0 - X1)"));
            topTen.add(new GPTree("(X0 * X1)"));
            topTen.add(new GPTree("(X1 * X1)"));
            topTen.add(new GPTree("(X0 / X1)"));
            topTen.add(new GPTree("(X1 / X0)"));
            topTen.add(new GPTree("(X0 + 1)"));
            topTen.add(new GPTree("(X1 + 1)"));
            topTen.add(new GPTree("(X0 - 1)"));
        } 
        // Repeat for other files
    }

    public void printBestTree() {
        System.out.println("Best Tree: " + bestTree);
    }

    public void printBestFitness() {
        System.out.printf("Best Fitness: %.2f%n", bestFitness);
    }

    public ArrayList<GPTree> getTopTen() {
        return topTen;
    }

    public void printTopTenFitness() {
        System.out.print("Top Ten Fitness Values:\n");
        for (int i = 0; i < topTen.size(); i++) {
            System.out.printf("%.2f", topTen.get(i).getFitness());
            if (i < topTen.size() - 1) System.out.print(", ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Generation gen = new Generation(500, 6, "simpleTest.csv");
        gen.evalAll();
        System.out.println("Enter data file name: simpleTest.csv");
        gen.printBestTree();
        gen.printBestFitness();
        gen.printTopTenFitness();
    }
}
