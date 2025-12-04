import java.util.*;

public class TestGeneration {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter data file name: ");
        String fileName = sc.nextLine();

        Generation generation = new Generation(500, 6, fileName);

        GPTree bestTree = generation.getBestTree();
        double[] topTen = generation.getTopTenFitness();

        System.out.println("Best Tree: " + bestTree);
        System.out.println("Best Fitness: " + bestTree.getFitness());
        System.out.print("Top Ten Fitness Values:\n");
        for (int i = 0; i < topTen.length; i++) {
            System.out.print(String.format("%.2f", topTen[i]));
            if (i != topTen.length - 1) System.out.print(", ");
        }
        System.out.println();
    }
}
