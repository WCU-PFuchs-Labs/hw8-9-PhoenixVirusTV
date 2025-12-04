import java.util.Random;
import java.util.ArrayList;

public class GPTree implements Comparable<GPTree> {

    private Node root;
    private NodeFactory nf;
    private double fitness;

    // -------- CONSTRUCTORS --------

    public GPTree(NodeFactory nf, int depth) {
        this.nf = nf;
        this.root = nf.randomTree(depth);
    }

    public GPTree(Node root, NodeFactory nf) {
        this.root = root;
        this.nf = nf;
    }

    // -------- EVALUATION OF A SINGLE ROW --------

    public double eval(double[] data) {
        return root.eval(data);
    }

    // -------- COLLECT FUNCTIONALITY --------

    public void collect(Collector c) {
        root.collect(c);
    }

    // -------- RANDOM SUBTREE SELECTION --------

    private Node randomNode() {
        ArrayList<Node> list = new ArrayList<>();
        collect(n -> list.add(n));
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }

    // -------- CROSSOVER --------

    public void crossover(GPTree other) {

        // get random nodes from each tree
        Node a = this.randomNode();
        Node b = other.randomNode();

        // clone b before swapping
        Node bClone = (Node) b.clone();

        // swap subtrees
        if (a.getParent() == null) {
            // a is root
            this.root = bClone;
            bClone.setParent(null);
        } else {
            a.getParent().replaceChild(a, bClone);
        }
    }


    // -------- FITNESS EVALUATION --------

    public void evalFitness(DataSet data) {
        double sumSq = 0;

        for (int i = 0; i < data.size(); i++) {
            DataRow r = data.getRow(i);
            double y = r.getY();
            double[] x = r.getXValues();

            double pred = eval(x);
            double diff = pred - y;
            sumSq += diff * diff;
        }

        fitness = sumSq;   // lower is better (MSE)
    }

    public double getFitness() {
        return fitness;
    }


    // -------- COMPARISON --------

    @Override
    public int compareTo(GPTree t) {
        return Double.compare(this.fitness, t.fitness);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof GPTree)) return false;
        return this.compareTo((GPTree) o) == 0;
    }

    // -------- CLONE --------

    @Override
    public GPTree clone() {
        try {
            GPTree c = new GPTree(this.nf, 1);  // dummy depth: will be overwritten
            c.root = (Node) this.root.clone();
            c.fitness = this.fitness;
            return c;
        } catch (Exception e) {
            return null;
        }
    }

    // -------- PRINTING --------

    public String toString() {
        return root.toString();
    }
}
