import java.util.Scanner;

public class TestGP {
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

        // Create initial generation
        Generation gen = new Generation(500, 6, fileName);

        // Evaluate initial generation
        gen.evalAll();

        // Run 50 generations
        for (int i = 1; i <= 50; i++) {
            System.out.println("Generation " + i + ":");
            System.out.println("Best Tree: " + gen.getBestTree());
            System.out.printf("Best Fitness: %.2f%n", gen.getBestFitness());

            // Evolve to next generation
            gen.evolve();

            // Evaluate new generation
            gen.evalAll();
        }
    }
}
