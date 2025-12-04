import java.util.Scanner;
import java.util.ArrayList;

public class TestGeneration {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter data file name: ");
        String fileName = sc.nextLine();

        // Check if file exists
        java.io.File f = new java.io.File(fileName);
        if (!f.exists()) {
            System.out.println("You must provide a data file.");
            System.exit(1);
        }

        // Create generation
        Generation gen = new Generation(500, 6, fileName);

        // Evaluate all trees
        gen.evalAll();

        // Print best tree
        System.out.println("Best Tree: " + gen.getBestTree());

        // Print best fitness
        System.out.printf("Best Fitness: %.2f%n", gen.getBestFitness());

        // Print top 10 fitness values
        System.out.println("Top Ten Fitness Values:");
        ArrayList<GPTree> top10 = gen.getTopTen();
        for (int i = 0; i < top10.size(); i++) {
            System.out.printf("%.2f", top10.get(i).getFitness());
            if (i < top10.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }
}
