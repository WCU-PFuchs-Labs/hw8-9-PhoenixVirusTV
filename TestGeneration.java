import java.util.*;

public class TestGeneration {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter data file name: ");
        String fileName = scanner.nextLine();

        // Create a generation
        Generation generation = new Generation(500, 6, fileName);

        // Get the best tree
        GPTree bestTree = generation.getBestTree();
        System.out.println("Best Tree: " + bestTree);
        System.out.printf("Best Fitness: %.2f%n", bestTree.getFitness());

        // Get top ten fitness values
        double[] topTen = generation.getTopTenFitness();
        System.out.print("Top Ten Fitness Values:\n");
        for (int i = 0; i < topTen.length; i++) {
            System.out.printf("%.2f", topTen[i]);
            if (i < topTen.length - 1) System.out.print(", ");
        }
        System.out.println();
    }
}
