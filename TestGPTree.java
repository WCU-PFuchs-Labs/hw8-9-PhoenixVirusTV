import java.util.*;
import java.io.*;

public class TestGP {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter data file name: ");
        String fileName = sc.nextLine();

        // Create GP manager
        GP gp = new GP(fileName);

        // Evaluate first generation
        gp.evalFirstGeneration();

        // Run 50 generations
        gp.runGenerations(50);
    }
}
