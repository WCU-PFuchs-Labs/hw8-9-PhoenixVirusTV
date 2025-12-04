/**
 * Class Name: GPTree
 * Description:
 *     Represents a genetic programming tree used for symbolic regression.
 *     A GPTree consists of a single root Node, which may be an operator node
 *     (with children) or a terminal node. The GPTree supports evaluation,
 *     deep copying, mutation, crossover, and pretty-printing.
 *
 * Author: Michael Williams
 * Date: Fall 2025
 */

import java.util.Random;

public class GPTree {

    /** Root node of the GP tree */
    private Node root;

    /** Random object used for mutation & crossover */
    private static final Random rand = new Random();

    /** Default constructor (creates an empty tree) */
    public GPTree() {
        this.root = null;
    }

    /** Constructor with an already-built root node */
    public GPTree(Node root) {
        this.root = root;
    }

    /** Returns the root node */
    public Node getRoot() {
        return root;
    }

    /** Replace the root node */
    public void setRoot(Node n) {
        this.root = n;
    }

    /**
     * Evaluates the GPTree for a specific row of a dataset.
     * @param data   DataSet containing variables
     * @param row    Which row to evaluate
     * @return       Numerical output of the tree
     */
    public double eval(DataSet data, int row) {
        return root.eval(data, row);
    }

    /**
     * Returns a deep copy of this GPTree (root and entire subtree).
     */
    public GPTree copy() {
        return new GPTree(root.copy());
    }

    /**
     * Produces a pretty infix representation of the tree.
     * Example: "((x + 3) * (y - 2))"
     */
    @Override
    public String toString() {
        return root.printInfix();
    }

    // =====================================================================
    //                         MUTATION OPERATION
    // =====================================================================

    /**
     * Mutates the GPTree by selecting one random node and replacing it
     * with a completely new random subtree grown by the NodeFactory.
     *
     * @param factory – a NodeFactory that can grow new subtrees
     * @param maxDepth – maximum depth of the replacement subtree
     */
    public void mutate(NodeFactory factory, int maxDepth) {
        // If the tree only has a root, just replace it
        if (root.getNodeCount() == 1) {
            root = factory.growTree(maxDepth);
            return;
        }

        // Pick a random index in the tree
        int targetIndex = rand.nextInt(root.getNodeCount());

        // Mutate the node at that position
        root = mutateHelper(root, factory, maxDepth, new int[]{targetIndex});
    }

    private Node mutateHelper(Node current, NodeFactory factory, int maxDepth, int[] counter) {
        if (counter[0] == 0) {
            // Replace this node entirely
            return factory.growTree(maxDepth);
        }

        counter[0]--; // move deeper in the traversal

        // Recurse into children
        if (current instanceof OpNode) {
            OpNode op = (OpNode) current;
            op.setLeft(mutateHelper(op.getLeft(), factory, maxDepth, counter));
            op.setRight(mutateHelper(op.getRight(), factory, maxDepth, counter));
        }

        return current;
    }

    // =====================================================================
    //                         CROSSOVER OPERATION
    // =====================================================================

    /**
     * Performs subtree crossover between two GPTrees.
     * Randomly selects one node from this tree and one from the donor tree,
     * and swaps them.
     *
     * @param donor – GPTree providing the subtree
     * @return new GPTree containing the crossed-over result
     */
    public GPTree crossover(GPTree donor) {
        GPTree offspring = this.copy();          // preserve original
        Node donorRootCopy = donor.root.copy();  // deep copy of donor

        int indexA = rand.nextInt(offspring.root.getNodeCount());
        int indexB = rand.nextInt(donorRootCopy.getNodeCount());

        offspring.root = crossoverHelper(
                offspring.root,
                donorRootCopy,
                new int[]{indexA},
                new int[]{indexB}
        );

        return offspring;
    }

    private Node crossoverHelper(Node hostNode, Node donorNode, int[] a, int[] b) {

        // When host index hits 0, replace with donor subtree
        if (a[0] == 0) {
            return getNodeByIndex(donorNode, b[0]).copy();
        }

        a[0]--;

        if (hostNode instanceof OpNode op) {
            op.setLeft(crossoverHelper(op.getLeft(), donorNode, a, b));
            op.setRight(crossoverHelper(op.getRight(), donorNode, a, b));
        }

        return hostNode;
    }

    /**
     * Retrieves the node at a specific index (preorder).
     */
    public Node getNodeByIndex(int index) {
        return getNodeByIndex(root, index);
    }

    private Node getNodeByIndex(Node current, int index) {
        if (index == 0)
            return current;

        index--;

        if (current instanceof OpNode op) {

            int leftCount = op.getLeft().getNodeCount();

            if (index < leftCount)
                return getNodeByIndex(op.getLeft(), index);

            index -= leftCount;

            return getNodeByIndex(op.getRight(), index);
        }

        throw new IllegalArgumentException("Index exceeds node count");
    }
}
