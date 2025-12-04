import java.util.*;
import java.io.*;

public class TestGeneration {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter data file name: ");
        String fileName = sc.nextLine();

        // Create a generation
        Generation generation = new Generation(500, 6, fileName);

        // Access the internal trees array directly
        GPTree[] trees = generation.trees; // Make sure trees is public or has a getter

        // Sort trees by fitness (ascending)
        Arrays.sort(trees, Comparator.comparingDouble(GPTree::getFitness));

        // Best tree is first after sorting
        GPTree bestTree = trees[0];
        System.out.println("Best Tree: " + bestTree);
        System.out.println("Best Fitness: " + bestTree.getFitness());

        // Print top ten fitness values
        System.out.print("Top Ten Fitness Values:\n");
        int topCount = Math.min(10, trees.length);
        for (int i = 0; i < topCount; i++) {
            System.out.print(String.format("%.2f", trees[i].getFitness()));
            if (i < topCount - 1) System.out.print(", ");
        }
        System.out.println();
    }
}
