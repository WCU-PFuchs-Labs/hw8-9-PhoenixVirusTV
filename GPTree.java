import java.util.Random;

// Skeleton GPTree class
public class GPTree {
    private Node root;  // assume Node represents expression tree nodes
    private double fitness;

    // Constructor
    public GPTree(NodeFactory factory, int maxDepth, Random rand) {
        this.root = factory.createRandomTree(maxDepth, rand);
        this.fitness = Double.MAX_VALUE; // initialize high
    }

    // Evaluate tree for a single input
    public double evaluate(double[] inputs) {
        return root.evaluate(inputs); // root must implement evaluate(double[])
    }

    // Compute fitness over dataset (sum of squared errors)
    public void evaluateFitness(DataSet data) {
        double error = 0.0;
        for (int i = 0; i < data.size(); i++) {
            double predicted = this.evaluate(data.getInput(i));
            double actual = data.getOutput(i);
            error += Math.pow(predicted - actual, 2);
        }
        this.fitness = error;
    }

    public double getFitness() {
        return fitness;
    }

    // Optional: nice string representation of tree
    @Override
    public String toString() {
        return root.toString();
    }
}
