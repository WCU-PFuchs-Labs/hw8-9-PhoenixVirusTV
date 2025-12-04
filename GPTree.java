import java.util.ArrayList;
import java.util.Random;

public class GPTree implements Comparable<GPTree>, Collector, Cloneable {
    private Node root;
    private ArrayList<Node> crossNodes;
    private double fitness;

    // --- Constructors ---
    public GPTree() {
        root = null;
    }

    // Matches how your tests construct trees: NodeFactory, maxDepth, Random
    public GPTree(NodeFactory n, int maxDepth, Random rand) {
        root = n.getOperator(rand);
        root.addRandomKids(n, maxDepth, rand);
    }

    // --- Collector implementation: collect non-leaf nodes for crossover ---
    @Override
    public void collect(Node node) {
        if (!node.isLeaf()) {
            if (crossNodes == null) crossNodes = new ArrayList<>();
            crossNodes.add(node);
        }
    }

    // Prepare list of cross nodes by traversing the tree
    public void traverse() {
        crossNodes = new ArrayList<>();
        root.traverse(this);
    }

    /** Return semicolon-separated string of cross nodes (useful for testing) */
    public String getCrossNodes() {
        if (crossNodes == null || crossNodes.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        int lastIndex = crossNodes.size() - 1;
        for (int i = 0; i < lastIndex; ++i) {
            sb.append(crossNodes.get(i).toString()).append(";");
        }
        sb.append(crossNodes.get(lastIndex).toString());
        return sb.toString();
    }

    // --- Crossover: swaps a left or right child with another tree's chosen node ---
    public void crossover(GPTree tree, Random rand) {
        // ensure both trees have crossNodes populated
        this.traverse();
        tree.traverse();

        // safety: if either has no cross nodes, do nothing
        if (this.crossNodes.isEmpty() || tree.crossNodes.isEmpty()) return;

        int thisPoint = rand.nextInt(this.crossNodes.size());
        int treePoint = rand.nextInt(tree.crossNodes.size());
        boolean left = rand.nextBoolean();

        Node thisTrunk = this.crossNodes.get(thisPoint);
        Node treeTrunk = tree.crossNodes.get(treePoint);

        if (left) thisTrunk.swapLeft(treeTrunk);
        else thisTrunk.swapRight(treeTrunk);
    }

    // --- Evaluate single data row ---
    public double eval(double[] data) {
        return root.eval(data);
    }

    @Override
    public String toString() {
        return root.toString();
    }

    // --- Fitness over a DataSet (sum of squared errors) ---
    public void evalFitness(DataSet data) {
        double sumSq = 0.0;
        int rows = data.size(); // assumes DataSet.size() exists
        for (int i = 0; i < rows; ++i) {
            DataRow r = data.getRow(i);     // assumes DataSet.getRow(i)
            double y = r.getY();            // assumes DataRow.getY()
            double[] x = r.getXValues();    // assumes DataRow.getXValues()
            double pred = eval(x);
            double diff = pred - y;
            sumSq += diff * diff;
        }
        this.fitness = sumSq;
    }

    public double getFitness() { return fitness; }

    // --- Comparable / equals ---
    @Override
    public int compareTo(GPTree t) {
        return Double.compare(this.fitness, t.fitness);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof GPTree)) return false;
        return this.compareTo((GPTree) o) == 0;
    }

    // --- Deep clone ---
    @Override
    public GPTree clone() {
        try {
            GPTree c = (GPTree) super.clone();
            if (this.root != null) c.root = (Node) this.root.clone();
            if (this.crossNodes != null) c.crossNodes = new ArrayList<>(this.crossNodes); // shallow copy of references is OK
            c.fitness = this.fitness;
            return c;
        } catch (CloneNotSupportedException e) {
            // Shouldn't happen because we implement Cloneable, but handle gracefully
            System.out.println("GPTree clone failed: " + e);
            return null;
        }
    }
}
