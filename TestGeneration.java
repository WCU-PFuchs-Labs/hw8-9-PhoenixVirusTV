import java.util.ArrayList;

public class TestGeneration {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("You must provide a data file.");
            return;
        }

        String fileName = args[0];
        Generation gen = new Generation(500, 6, fileName);

        // Evaluate all trees
        gen.evalAll();

        // Print best tree
        System.out.println("Best Tree: " + gen.getBestTree());

        // Print best fitness
        System.out.printf("Best Fitness: %.2f%n", gen.getBestFitness());

        // Print top ten fitness values
        ArrayList<GPTree> top10 = gen.getTopTen();
        System.out.print("Top Ten Fitness Values:\n");
        for (int i = 0; i < top10.size(); i++) {
            System.out.printf("%.2f", top10.get(i).getFitness());
            if (i != top10.size() - 1) System.out.print(", ");
        }
        System.out.println();
    }
}
