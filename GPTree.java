import java.util.Random;

public class GPTree implements Comparable<GPTree>, Cloneable {
    Node root;
    private double fitness;

    // Constructor used by Generation
    public GPTree(NodeFactory nf, int maxDepth, Random rand) {
        root = nf.getOperator(rand);            // Start with an operator
        root.addRandomKids(nf, maxDepth - 1, rand); // Fill tree
    }

    // Evaluate tree for a single input row
    public double eval(double[] values) {
        return root.eval(values);
    }

    // Compute fitness (sum of squared errors)
    public void evalFitness(DataSet data) {
        double sum = 0;
        for (int i = 0; i < data.size(); i++) {
            DataRow row = data.getRow(i);
            double pred = eval(row.getXValues());
            double err = pred - row.getY();
            sum += err * err;
        }
        fitness = sum;
    }

    public double getFitness() {
        return fitness;
    }

    @Override
    public GPTree clone() {
        GPTree copy = null;
        try {
            copy = (GPTree) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        copy.root = (Node) this.root.clone();
        copy.fitness = this.fitness;
        return copy;
    }

    // Swap random subtrees between two trees
    public void crossover(GPTree other, Random rand) {
        Node myNode = getRandomNode(this.root, rand);
        Node otherNode = getRandomNode(other.root, rand);

        Node temp = (Node) myNode.clone();
        myNode.left = otherNode.left;
        myNode.right = otherNode.right;
        otherNode.left = temp.left;
        otherNode.right = temp.right;
    }

    // Pick a random node in the tree
    private Node getRandomNode(Node node, Random rand) {
        if (node == null) return null;
        if (node.isLeaf() || rand.nextBoolean()) return node;
        if (rand.nextBoolean()) return getRandomNode(node.left, rand);
        return getRandomNode(node.right, rand);
    }

    // Traverse tree with Collector
    public void traverse() {
        root.traverse(n -> {}); // empty collector
    }

    // Return semicolon-separated string of all binop nodes
    public String getCrossNodes() {
        StringBuilder sb = new StringBuilder();
        root.traverse(n -> {
            if (n.op instanceof Binop) {
                if (sb.length() > 0) sb.append(";");
                sb.append(n.toString());
            }
        });
        return sb.toString();
    }

    // Compare by fitness (lower is better)
    @Override
    public int compareTo(GPTree o) {
        return Double.compare(this.fitness, o.fitness);
    }

    @Override
    public String toString() {
        return root.toString();
    }
}
