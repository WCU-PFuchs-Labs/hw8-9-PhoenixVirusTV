import java.util.*;

public class TestGP {
    public static void main(String[] args) {

        // DO NOT PROMPT — autograder breaks if you do
        Scanner in = new Scanner(System.in);
        String fileName = in.nextLine();

        Generation gen = new Generation(500, 6, fileName);

        for (int genNum = 1; genNum <= 50; genNum++) {

            gen.evalAll();

            gen.printBestTree();
            gen.printBestFitness();
            gen.printTopTen();   // required by autograder

            gen.evolve();
        }
    }
}
