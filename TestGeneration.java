import java.util.*;
import java.text.DecimalFormat;

public class TestGeneration {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter data file name: ");
        String fileName = scanner.nextLine();

        // Create a Generation of trees
        int populationSize = 50; // adjust as needed
        int maxDepth = 6;        // adjust as needed
        Generation generation = new Generation(populationSize, maxDepth, fileName);

        // Get best tree
        GPTree bestTree = generation.getBestTree();
        System.out.println("Best Tree: " + bestTree);

        // Get best fitness
        double bestFitness = bestTree.getFitness();
        System.out.println("Best Fitness: " + bestFitness);

        // Get top ten fitness values
        double[] topTen = generation.getTopTenFitness();
        DecimalFormat df = new DecimalFormat("0.00");
        System.out.print("Top Ten Fitness Values:\n");
        for (int i = 0; i < topTen.length; i++) {
            System.out.print(df.format(topTen[i]));
            if (i != topTen.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }
}
