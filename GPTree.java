import java.util.ArrayList;
import java.util.Random;

/**
 * GPTree - genetic programming tree used for symbolic regression.
 *
 * This implementation matches the Node, NodeFactory, and Collector
 * classes in your project:
 *  - constructor: GPTree(NodeFactory n, int maxDepth, Random rand)
 *  - crossover(GPTree other, Random rand)
 *  - eval(double[] data)
 *  - evalFitness(DataSet data)
 *  - getFitness()
 *  - clone(), compareTo(), equals()
 *
 * It assumes:
 *  - Node has methods: eval(double[]), addRandomKids(NodeFactory,int,Random),
 *    clone(), traverse(Collector), swapLeft(Node), swapRight(Node), isLeaf(), toString()
 *  - NodeFactory has methods: getOperator(Random), getTerminal(Random),
 *    getNumOps(), getNumIndepVars()
 *  - DataSet has methods: size(), getRow(int)
 *  - DataRow has methods: getY(), getXValues()
 */

public class GPTree implements Comparable<GPTree>, Collector, Cloneable {
    private Node root;
    private ArrayList<Node> crossNodes;
    private double fitness;

    // Default empty constructor
    public GPTree() {
        root = null;
        crossNodes = null;
        fitness = Double.POSITIVE_INFINITY;
    }

    // Constructor matching other code/tests: build a random tree
    public GPTree(NodeFactory n, int maxDepth, Random rand) {
        root = n.getOperator(rand);
        root.addRandomKids(n, maxDepth, rand);
        crossNodes = null;
        fitness = Double.POSITIVE_INFINITY;
    }

    // Return string representation of tree
    @Override
    public String toString() {
        return root == null ? "" : root.toString();
    }

    // Evaluate this tree on one row (double[] of independent variables)
    public double eval(double[] data) {
        return root.eval(data);
    }

    // ---------------- Collector implementation ----------------
    // collect non-leaf nodes for crossover
    @Override
    public void collect(Node node) {
        if (!node.isLeaf()) {
            if (crossNodes == null) crossNodes = new ArrayList<>();
            crossNodes.add(node);
        }
    }

    // traverse tree and populate crossNodes
    public void traverse() {
        crossNodes = new ArrayList<>();
        if (root != null) root.traverse(this);
    }

    // String list of cross nodes separated by semicolons (used by tests)
    public String getCrossNodes() {
        if (crossNodes == null || crossNodes.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        int last = crossNodes.size() - 1;
        for (int i = 0; i < last; ++i) {
            sb.append(crossNodes.get(i).toString()).append(";");
        }
        sb.append(crossNodes.get(last).toString());
        return sb.toString();
    }

    // ---------------- Crossover ----------------
    // Swap a left or right child with another tree's chosen non-leaf node
    public void crossover(GPTree tree, Random rand) {
        if (this.root == null || tree == null || tree.root == null) return;

        this.traverse();
        tree.traverse();

        // If either has no cross nodes, nothing to do
        if (this.crossNodes == null || this.crossNodes.isEmpty()) return;
        if (tree.crossNodes == null || tree.crossNodes.isEmpty()) return;

        int thisPoint = rand.nextInt(this.crossNodes.size());
        int treePoint = rand.nextInt(tree.crossNodes.size());
        boolean left = rand.nextBoolean();

        Node thisTrunk = this.crossNodes.get(thisPoint);
        Node treeTrunk = tree.crossNodes.get(treePoint);

        if (left) thisTrunk.swapLeft(treeTrunk);
        else thisTrunk.swapRight(treeTrunk);
    }

    // ---------------- Fitness computation ----------------
    // Compute sum of squared errors over all rows in DataSet
    public void evalFitness(DataSet data) {
        double sumSq = 0.0;
        int rows = data.size();
        for (int i = 0; i < rows; ++i) {
            DataRow r = data.getRow(i);
            double y = r.getY();
            double[] x = r.getXValues();
            double pred = eval(x);
            double diff = pred - y;
            sumSq += diff * diff;
        }
        this.fitness = sumSq;
    }

    public double getFitness() {
        return this.fitness;
    }

    // ---------------- Comparable and equals ----------------
    @Override
    public int compareTo(GPTree t) {
        return Double.compare(this.fitness, t.fitness);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof GPTree)) return false;
        return this.compareTo((GPTree) o) == 0;
    }

    // ---------------- Clone ----------------
    @Override
    public GPTree clone() {
        try {
            GPTree c = (GPTree) super.clone();
            if (this.root != null) c.root = (Node) this.root.clone();
            if (this.crossNodes != null) c.crossNodes = new ArrayList<>(this.crossNodes);
            c.fitness = this.fitness;
            return c;
        } catch (CloneNotSupportedException e) {
            // Shouldn't happen because we implement Cloneable
            return null;
        }
    }
}
