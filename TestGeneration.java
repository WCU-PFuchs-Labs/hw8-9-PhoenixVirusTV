import java.io.IOException;
import java.util.ArrayList;

public class TestGeneration {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java TestGeneration <csvFile>");
            return;
        }

        String fileName = args[0];

        try {
            // Create a generation object with population size = 500, max depth = 6
            Generation g = new Generation(500, 6, fileName);

            System.out.println("Collect has all binops!");
            ArrayList<GPTree> top = g.getTopTrees(10);

            for (int i = 0; i < top.size(); i++) {
                GPTree t = top.get(i);
                System.out.println("Tree " + (i + 1) + ": " + t);
                System.out.println("  Fitness = " + t.getFitness());
            }

        } catch (IOException e) {
            System.out.println("Error loading dataset: " + e.getMessage());
        }
    }
}
