import java.util.ArrayList;
import java.util.Random;
private double fitness;

public class GPTree implements Collector {
    private Node root;
    private ArrayList<Node> crossNodes;

    public GPTree() { root = null; }

    public GPTree(NodeFactory n, int maxDepth, Random rand) {
        root = n.getOperator(rand);
        root.addRandomKids(n, maxDepth, rand);
    }

    /** Step 3: collect non-leaf nodes for crossover */
    @Override
    public void collect(Node node) {
        if(!node.isLeaf()) {
            if(crossNodes == null) crossNodes = new ArrayList<>();
            crossNodes.add(node);
        }
    }

    /** prepare list of cross nodes by traversing tree */
    public void traverse() {
        crossNodes = new ArrayList<>();
        root.traverse(this);
    }

    public String getCrossNodes() {
        StringBuilder string = new StringBuilder();
        int lastIndex = crossNodes.size() - 1;
        for(int i = 0; i < lastIndex; ++i) {
            Node node = crossNodes.get(i);
            string.append(node.toString()).append(";");
        }
        if(lastIndex >= 0) string.append(crossNodes.get(lastIndex));
        return string.toString();
    }

    public void crossover(GPTree tree, Random rand) {
        this.traverse();
        tree.traverse();
        int thisPoint = rand.nextInt(this.crossNodes.size());
        int treePoint = rand.nextInt(tree.crossNodes.size());
        boolean left = rand.nextBoolean();

        Node thisTrunk = crossNodes.get(thisPoint);
        Node treeTrunk = tree.crossNodes.get(treePoint);

        if(left) thisTrunk.swapLeft(treeTrunk);
        else thisTrunk.swapRight(treeTrunk);
    }

    public String toString() { return root.toString(); }
    public double eval(double[] data) { return root.eval(data); }
}
// Compute fitness over dataset
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

    fitness = sumSq;
}

public double getFitness() {
    return fitness;
}

@Override
public int compareTo(GPTree t) {
    return Double.compare(this.fitness, t.fitness);
}

@Override
public boolean equals(Object o) {
    if (o == null || !(o instanceof GPTree)) return false;
    return compareTo((GPTree)o) == 0;
}

@Override
public GPTree clone() {
    try {
        GPTree c = (GPTree) super.clone();
        c.root = (Node) this.root.clone();
        return c;
    } catch (Exception e) {
        return null;
    }
}
