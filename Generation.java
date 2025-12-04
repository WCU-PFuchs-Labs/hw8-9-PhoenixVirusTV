import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Generation {
    private GPTree[] trees;
    private DataSet data;
    private NodeFactory factory;
    private Random rand;

    public Generation(int size, int maxDepth, String fileName) {
        rand = new Random();
        data = new DataSet(fileName);

        Binop[] ops = { new Plus(), new Minus(), new Mult(), new Divide() };
        factory = new NodeFactory(ops, data.getNumIndepVars());

        trees = new GPTree[size];
        for (int i = 0; i < size; i++) {
            trees[i] = new GPTree(factory, maxDepth, rand);
        }
    }

    // Evaluate all trees and sort by fitness
    public void evalAll() {
        for (GPTree t : trees) t.evalFitness(data);
        Arrays.sort(trees);
    }

    // Return top 10 trees
    public ArrayList<GPTree> getTopTen() {
        ArrayList<GPTree> topTen = new ArrayList<>();
        int n = Math.min(10, trees.length);
        for (int i = 0; i < n; i++) {
            topTen.add(trees[i].copy());
        }
        return topTen;
    }

    public void printBestFitness() {
        if (trees.length > 0) System.out.printf("%.2f%n", trees[0].getFitness());
    }

    public void printBestTree() {
        if (trees.length > 0) System.out.println(trees[0]);
    }
}
