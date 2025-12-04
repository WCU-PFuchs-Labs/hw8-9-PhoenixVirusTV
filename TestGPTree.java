import java.util.*;

public class GPTree implements Comparable<GPTree>, Cloneable {
    private Node root;       // The root of the tree
    private double fitness;  // Fitness value after evaluation
    private NodeFactory factory;
    private Random rand;

    // Constructor
    public GPTree(NodeFactory factory, int maxDepth, Random rand) {
        this.factory = factory;
        this.rand = rand;
        this.root = factory.makeRandomTree(maxDepth, rand);
        this.fitness = Double.MAX_VALUE; // initial fitness
    }

    // Evaluate fitness over all rows in the DataSet
    public void evalFitness(DataSet dataSet) {
        double sum = 0.0;
        for (DataRow row : dataSet.getRows()) {
            double[] x = row.getX();
            double y = row.getY();
            double diff = root.eval(x) - y;
            sum += diff * diff;
        }
        this.fitness = sum;
    }

    // Get the computed fitness
    public double getFitness() {
        return fitness;
    }

    // Comparable interface
    @Override
    public int compareTo(GPTree other) {
        return Double.compare(this.fitness, other.fitness);
    }

    // Equals method (based on fitness)
    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof GPTree)) return false;
        return this.compareTo((GPTree) o) == 0;
    }

    // Clone the GPTree
    @Override
    public GPTree clone() {
        try {
            GPTree copy = (GPTree) super.clone();
            copy.root = this.root.clone(); // Node must implement Cloneable
            return copy;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Crossover with another tree (simplified: swap subtrees)
    public void crossover(GPTree other) {
        Node thisSub = this.root.getRandomNode(rand);
        Node otherSub = other.root.getRandomNode(rand);
        Node temp = thisSub.clone();
        thisSub.replaceWith(otherSub.clone());
        otherSub.replaceWith(temp);
    }

    // String representation
    @Override
    public String toString() {
        return root.toString();
    }
}
