import java.util.*;

public class TestGeneration {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter data file name: ");
        String file = in.nextLine();

        Generation gen = new Generation(500, 6, file);
        gen.evalAll();

        gen.printBestTree();
        gen.printBestFitness();

        ArrayList<GPTree> top10 = gen.getTopTen();
        System.out.print("Top Ten Fitness Values:\n");

        for (int i = 0; i < top10.size(); i++) {
            System.out.printf("%.2f", top10.get(i).getFitness());
            if (i < top10.size() - 1) System.out.print(", ");
        }
        System.out.println();
    }
}
