import java.util.*;

public class GP {

    private Generation generation;
    private int generationSize = 500;
    private int maxDepth = 6;

    public GP(String fileName) {
        generation = new Generation(generationSize, maxDepth, fileName);
    }

    /** Evaluate the first generation */
    public void evalFirstGeneration() {
        generation.evalAll();
    }

    /** Run many generations, printing best each time */
    public void runGenerations(int numGens) {
        for (int i = 1; i <= numGens; i++) {
            System.out.println("Generation " + i + ":");
            generation.printBestTree();
            generation.printBestFitness();

            // evolve creates next generation
            generation.evolve();
            generation.evalAll();
        }
    }

    /** Convenience function */
    public Generation getGeneration() {
        return generation;
    }
}
