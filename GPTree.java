import java.util.ArrayList;
import java.util.Random;

public class GPTree implements Comparable<GPTree>, Cloneable {
    private Node root;
    private ArrayList<Node> crossNodes;
    private double fitness;

    // Constructor: random tree
    public GPTree(NodeFactory factory, int maxDepth, Random rand) {
        root = factory.getOperator(rand);
        root.addRandomKids(factory, maxDepth, rand);
        crossNodes = null;
        fitness = Double.POSITIVE_INFINITY;
    }

    // Evaluate one row
    public double eval(double[] data) {
        return root.eval(data);
    }

    // Evaluate fitness over dataset
    public void evalFitness(DataSet dataSet) {
        double sumSq = 0.0;
        for (int i = 0; i < dataSet.size(); i++) {
            DataRow r = dataSet.getRow(i);
            double pred = eval(r.getXValues());
            double diff = pred - r.getY();
            sumSq += diff * diff;
        }
        fitness = sumSq;
    }

    public double getFitness() { return fitness; }

    // Comparable
    @Override
    public int compareTo(GPTree t) {
        return Double.compare(this.fitness, t.fitness);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof GPTree)) return false;
        return this.compareTo((GPTree)o) == 0;
    }

    // Clone / copy
    @Override
    public GPTree clone() {
        try {
            GPTree c = (GPTree) super.clone();
            if (this.root != null) c.root = (Node) this.root.clone();
            c.fitness = this.fitness;
            return c;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public GPTree copy() { return this.clone(); }

    @Override
    public String toString() { return root.toString(); }
}
