import java.util.Random;
import tabular.DataSet;
import tabular.DataRow;

/**
 * GPTree class for Genetic Programming
 * Implements Comparable and Cloneable
 */
public class GPTree implements Comparable<GPTree>, Cloneable {
    private Node root;      // root node of the tree
    private double fitness; // fitness value after evaluation
    private static Random rand = new Random();

    // Constructor with root node
    public GPTree(Node root) {
        this.root = root;
        this.fitness = Double.MAX_VALUE; // initialize as worst fitness
    }

    /**
     * Evaluate tree for a single input row
     */
    public double eval(double[] x) {
        return root.eval(x);
    }

    /**
     * Evaluate fitness across entire DataSet
     * Fitness = sum of squared deviations from y
     */
    public void evalFitness(DataSet dataSet) {
        double sum = 0.0;
        for (DataRow row : dataSet.getRows()) {
            double[] x = row.getX();
            double y = row.getY();
            double diff = eval(x) - y;
            sum += diff * diff;
        }
        this.fitness = sum;
    }

    /**
     * Returns fitness after evalFitness has been called
     */
    public double getFitness() {
        return fitness;
    }

    /**
     * Compare two GPTrees based on fitness
     */
    @Override
    public int compareTo(GPTree t) {
        return Double.compare(this.fitness, t.fitness);
    }

    /**
     * Equals based on fitness
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof GPTree)) return false;
        GPTree other = (GPTree) o;
        return Double.compare(this.fitness, other.fitness) == 0;
    }

    /**
     * Deep clone of GPTree
     */
    @Override
    public GPTree clone() {
        try {
            GPTree copy = (GPTree) super.clone();
            copy.root = this.root.clone(); // assumes Node is Cloneable
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Genetic crossover with another GPTree
     * Swap random subtrees
     */
    public void crossover(GPTree other) {
        Node mySubtree = this.root.getRandomNode();
        Node otherSubtree = other.root.getRandomNode();

        Node temp = mySubtree.clone();
        mySubtree.replaceWith(otherSubtree.clone());
        otherSubtree.replaceWith(temp);
    }

    /**
     * Return string representation of tree
     */
    @Override
    public String toString() {
        return root.toString();
    }
}
