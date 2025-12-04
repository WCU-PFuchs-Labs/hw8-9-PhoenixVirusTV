import java.util.Arrays;
import java.util.Scanner;

public class TestGeneration {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter data file name: ");
        String fileName = scanner.nextLine();

        Generation generation = new Generation(500, 6, fileName); // your existing constructor
        generation.evalAll(); // evaluate all trees

        GPTree bestTree = generation.getBestTree();
        double bestFitness = bestTree.getFitness();

        System.out.println("Best Tree: " + bestTree); // Make sure GPTree.toString() prints parentheses
        System.out.printf("Best Fitness: %.2f%n", bestFitness);

        double[] topTen = generation.getTopTenFitness(); // assume this returns a sorted array
        System.out.print("Top Ten Fitness Values:\n");
        for (int i = 0; i < topTen.length; i++) {
            System.out.printf("%.2f", topTen[i]);
            if (i < topTen.length - 1) System.out.print(", ");
        }
        System.out.println();
    }
}
