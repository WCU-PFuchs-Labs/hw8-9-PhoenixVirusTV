import java.util.*;
import java.io.*;

public class TestGeneration {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter data file name: ");
        String fileName = sc.nextLine();

        // Make a generation of 500 trees
        Generation g = new Generation(500, 6, fileName);

        // Evaluate all trees
        g.evalAll();

        // Print best tree
        System.out.println("Best Tree:");
        g.printBestTree();

        // Print best fitness
        System.out.println("Best Fitness:");
        g.printBestFitness();

        // Top Ten Fitness
        ArrayList<GPTree> top = g.getTopTen();

        System.out.println("Top Ten Fitness Values:");
        for(int i = 0; i < top.size(); i++) {
            double f = top.get(i).getFitness();
            System.out.print(String.format("%.2f", f));

            if(i < top.size() - 1) System.out.print(", ");
        }
        System.out.println();
    }
}
