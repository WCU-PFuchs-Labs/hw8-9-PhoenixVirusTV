import java.util.*;

public class GPTree implements Comparable<GPTree>, Collector {

    private Node root;
    private ArrayList<Node> crossNodes;
    private double fitness;

    public GPTree() {}

    public GPTree(NodeFactory nf, int maxDepth, Random rand) {
        root = nf.getOperator(rand);
        root.addRandomKids(nf, maxDepth, rand);
    }

    public double getFitness() {
        return fitness;
    }

    public void evalFitness(DataSet data) {
        fitness = 0;

        for (DataRow row : data.rows) {
            double predicted = root.eval(row.x);
            double error = predicted - row.y;
            fitness += error * error;  // squared error
        }
    }

    @Override
    public int compareTo(GPTree o) {
        return Double.compare(this.fitness, o.fitness);
    }

    // Required for crossover
    @Override
    public void collect(Node n) {
        if (!n.isLeaf()) {
            if (crossNodes == null) crossNodes = new ArrayList<>();
            crossNodes.add(n);
        }
    }

    public void traverse() {
        crossNodes = new ArrayList<>();
        root.traverse(this);
    }

    @Override
    public String toString() {
        return root.toString();
    }

    @Override
    public GPTree clone() {
        GPTree g = new GPTree();
        g.root = (Node) this.root.clone();
        g.fitness = this.fitness;
        return g;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof GPTree)) return false;
        GPTree t = (GPTree) o;
        return this.toString().equals(t.toString());
    }
}
