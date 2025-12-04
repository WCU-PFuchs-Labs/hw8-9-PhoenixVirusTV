import java.util.ArrayList;
import java.util.Scanner;

public class TestGeneration {
    public static void main(String[] args) {
        String fileName;

        if (args.length > 0) fileName = args[0];
        else {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter data file name: ");
            fileName = sc.nextLine();
            if (fileName.isEmpty()) {
                System.out.println("You must provide a data file.");
                return;
            }
        }

        Generation gen = new Generation(500, 6, fileName);
        gen.evalAll();

        ArrayList<GPTree> top10 = gen.getTopTen();

        System.out.println("Best Tree: " + top10.get(0));
        System.out.printf("Best Fitness: %.2f%n", top10.get(0).getFitness());
        System.out.println("Top Ten Fitness Values:");

        for (int i = 0; i < top10.size(); i++) {
            System.out.printf("%.2f", top10.get(i).getFitness());
            if (i != top10.size() - 1) System.out.print(", ");
        }
        System.out.println();
    }
}
