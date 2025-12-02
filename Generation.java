import java.util.*;

public class Generation {

    private ArrayList<GPTree> population;
    private DataSet data;

    // Create a generation of N trees
    public Generation(int size, int maxDepth, String fileName) {
        data = new DataSet(fileName);

        Binop[] ops = { new Plus(), new Minus(), new Mult(), new Divide() };
        NodeFactory nf = new NodeFactory(ops, data.numIndepVars);

        population = new ArrayList<>();

        Random rand = new Random();

        for (int i = 0; i < size; i++) {
            GPTree t = new GPTree(nf, maxDepth, rand);
            population.add(t);
        }
    }

    // Evaluate all trees
    public void evalAll() {
        for (GPTree t : population)
            t.evalFitness(data);
    }

    // Return best tree
    public GPTree getBestTree() {
        return Collections.min(population);
    }

    // Print best tree
    public void printBestTree() {
        GPTree best = getBestTree();
        System.out.println(best.toString());
    }

    // Print best fitness
    public void printBestFitness() {
        System.out.println(String.format("%.2f", getBestTree().getFitness()));
    }

    // Return top 10 trees
    public ArrayList<GPTree> getTopTen() {
        ArrayList<GPTree> sorted = new ArrayList<>(population);
        Collections.sort(sorted);

        int n = Math.min(10, sorted.size());
        return new ArrayList<>(sorted.subList(0, n));
    }
}
